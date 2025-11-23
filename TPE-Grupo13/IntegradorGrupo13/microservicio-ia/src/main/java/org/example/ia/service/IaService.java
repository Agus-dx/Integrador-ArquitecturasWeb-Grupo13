package org.example.ia.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.ia.client.GroqClient;
import org.example.ia.dto.RespuestaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 👉 Servicio que:
 * - Construye el prompt con el esquema SQL
 * - Llama a Groq para generar SQL
 * - Valida y extrae una ÚNICA sentencia SQL (SELECT/INSERT/UPDATE/DELETE)
 * - Ejecuta de forma segura (bloquea DDL peligrosos)
 */
@Service
public class IaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GroqClient groqChatClient;

    private final String CONTEXTO_SQL;

    private static final Logger log = LoggerFactory.getLogger(IaService.class);

    // ========================================================================
    // [MOD - NUEVO] Reglas de extracción/seguridad para la sentencia SQL
    // ------------------------------------------------------------------------
    // Aceptamos EXACTAMENTE una sentencia que empiece por SELECT|INSERT|UPDATE|DELETE
    // y que termine en ';'. El DOTALL permite capturar saltos de línea.
    private static final Pattern SQL_ALLOWED =
            Pattern.compile("(?is)\\b(SELECT|INSERT|UPDATE|DELETE)\\b[\\s\\S]*?;");

    // Bloqueamos DDL u otras operaciones peligrosas por si el modelo "derrapa".
    private static final Pattern SQL_FORBIDDEN =
            Pattern.compile("(?i)\\b(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b");
    // ========================================================================

    public IaService() {
        this.CONTEXTO_SQL = cargarEsquemaSQL("esquema_completo.sql");
    }

    private String cargarEsquemaSQL(String nombreArchivo) {
        try (InputStream inputStream = new ClassPathResource(nombreArchivo).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo SQL desde resources: " + e.getMessage(), e);
        }
    }

    /**
     * Genera el prompt, obtiene la SQL de Groq, la valida y ejecuta.
     */
    // ========================================================================
    // [MOD - NUEVO] Agregamos @Transactional para soportar INSERT/UPDATE/DELETE
    // ========================================================================
    @Transactional
    public ResponseEntity<?> procesarPrompt(String promptUsuario) {
        try {
            String promptFinal = """
                    Este es el esquema de mi base de datos MySQL:
                    %s

                    Basándote exclusivamente en este esquema, devolveme ÚNICAMENTE una sentencia SQL
                    MySQL completa y VÁLIDA (sin texto adicional, sin markdown, sin comentarios) que
                    termine con punto y coma. La sentencia puede ser SELECT/INSERT/UPDATE/DELETE.
                    %s
                    """.formatted(CONTEXTO_SQL, promptUsuario);

            log.info("==== PROMPT ENVIADO A LA IA ====\n{}", promptFinal);

            String respuestaIa = groqChatClient.preguntar(promptFinal);
            log.info("==== RESPUESTA IA ====\n{}", respuestaIa);

            // ========================================================================
            // [MOD - CAMBIO] Usamos la nueva extracción segura (acepta DML y bloquea DDL)
            // ========================================================================
            String sql = extraerConsultaSQL(respuestaIa);
            if (sql == null || sql.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new RespuestaApi<>(false,
                                "No se encontró una sentencia SQL válida en la respuesta de la IA.", null));
            }

            log.info("==== SQL EXTRAÍDA ====\n{}", sql);

            // Para JDBC/JPA normalmente NO va el ';' final
            String sqlToExecute = sql.endsWith(";") ? sql.substring(0, sql.length() - 1) : sql;

            try {
                Object data;
                // ====================================================================
                // [MOD - NUEVO] Ejecutamos SELECT con getResultList y DML con executeUpdate
                // ====================================================================
                if (sql.trim().regionMatches(true, 0, "SELECT", 0, 6)) {
                    @SuppressWarnings("unchecked")
                    List<Object[]> resultados = entityManager.createNativeQuery(sqlToExecute).getResultList();
                    data = resultados;
                    return ResponseEntity.ok(new RespuestaApi<>(true, "Consulta SELECT ejecutada con éxito", data));
                } else {
                    int rows = entityManager.createNativeQuery(sqlToExecute).executeUpdate();
                    data = rows; // cantidad de filas afectadas
                    return ResponseEntity.ok(new RespuestaApi<>(true, "Sentencia DML ejecutada con éxito", data));
                }
            } catch (Exception e) {
                log.warn("Error al ejecutar SQL: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new RespuestaApi<>(false, "Error al ejecutar la sentencia: " + e.getMessage(), null));
            }

        } catch (Exception e) {
            log.error("Fallo al procesar prompt", e);
            return new ResponseEntity<>(
                    new RespuestaApi<>(false, "Error al procesar el prompt: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    // ========================================================================
    // [MOD - REEMPLAZO] Método de extracción robusto y documentado
    //   - Acepta SOLO una sentencia que empiece con SELECT/INSERT/UPDATE/DELETE
    //   - Exige punto y coma final
    //   - Recorta todo lo que venga después del primer ';'
    //   - Bloquea DDL peligrosos (DROP/TRUNCATE/ALTER/CREATE/GRANT/REVOKE)
    // ========================================================================
    private String extraerConsultaSQL(String respuesta) {
        if (respuesta == null) return null;

        Matcher m = SQL_ALLOWED.matcher(respuesta);
        if (!m.find()) return null;

        String sql = m.group().trim();

        // Asegurar UNA sola sentencia (hasta el primer ';')
        int first = sql.indexOf(';');
        if (first > -1) {
            sql = sql.substring(0, first + 1);
        }

        // Bloquear DDL
        if (SQL_FORBIDDEN.matcher(sql).find()) {
            log.warn("Sentencia bloqueada por contener DDL prohibido: {}", sql);
            return null;
        }

        return sql;
    }

    // =======================
    // [MOD - HISTÓRICO]
    // Antes estaba este extraerConsultaSQL que solo acepta consultas SELECT:
    //
    // private String extraerConsultaSQL(String respuesta) {
    //     Pattern pattern = Pattern.compile("(?i)(SELECT\\s+.*?;)", Pattern.DOTALL);
    //     Matcher matcher = pattern.matcher(respuesta);
    //     if (matcher.find()) {
    //         return matcher.group(1).trim();
    //     }
    //     return null;
    // }
    //
    // Lo reemplazamos por la versión superior que permite DML y agrega salvaguardas.
    // =======================
}