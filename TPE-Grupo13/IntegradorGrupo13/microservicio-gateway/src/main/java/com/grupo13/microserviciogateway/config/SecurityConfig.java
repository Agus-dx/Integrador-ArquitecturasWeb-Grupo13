/**
 * 🔒 Configuración Central de Seguridad (SecurityConfig).
 *
 * Implementa la configuración de Spring Security para el API Gateway. Es el
 * componente más crítico de seguridad en la arquitectura de microservicios.
 * * Características Clave:
 * 1. Stateless (JWT): Configura la gestión de sesiones como `STATELESS`
 * (`SessionCreationPolicy.STATELESS`), indicando que no se almacenan estados
 * de usuario en el servidor, lo que es esencial para el uso de JWT.
 * 2. JWT Filter: Agrega el `JwtFilter` **antes** del filtro estándar de
 * autenticación de Spring Security (`UsernamePasswordAuthenticationFilter`),
 * asegurando que el token se valide en cada solicitud.
 * 3. `PasswordEncoder`: Define el algoritmo de hash (`BCryptPasswordEncoder`)
 * para codificar contraseñas antes de enviarlas al Microservicio de Usuarios/Cuentas.
 * 4. Control de Autorización (CRÍTICO): Define las reglas de acceso a los
 * microservicios (`requestMatchers`) mapeando rutas a roles (`AuthotityConstant._ADMIN`,
 * `_USUARIO`, etc.). Esto centraliza la **autorización** en el Gateway.
 */
package com.grupo13.microserviciogateway.config;

import com.grupo13.microserviciogateway.security.AuthotityConstant;
import com.grupo13.microserviciogateway.security.jwt.JwtFilter;
import com.grupo13.microserviciogateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {
        http
                .csrf( AbstractHttpConfigurer::disable );
        http
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );

        http
                .securityMatcher("/api/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**" ) // Incluir rutas de Swagger en el matcher
                .authorizeHttpRequests( authz -> authz
                        // =======================================================
                        // RUTAS PÚBLICAS Y DE DOCUMENTACIÓN 🔓
                        // =======================================================
                        .requestMatchers(HttpMethod.POST, "/api/token").permitAll()     // Login/Generar Token
                        .requestMatchers(HttpMethod.POST, "/api/registrar").permitAll()  // Registro de usuarios
                        // Acceso a la documentación Swagger (crucial para la consigna 8)
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()

                        // =======================================================
                        // MS-MONOPATINES 🛴
                        // a. ADMIN: Reporte de uso/kilómetros para mantenimiento (POST/GET)
                        // c. ADMIN: Consultar monopatines con más de X viajes (GET Reporte)
                        // g. USUARIO: Listado de monopatines cercanos a mi zona (GET Búsqueda)
                        // =======================================================
                        .requestMatchers("/api/monopatines/reportes-mantenimiento/**").hasAuthority( AuthotityConstant._ADMIN ) // a
                        .requestMatchers(HttpMethod.GET, "/api/monopatines/reportes-viajes/**").hasAuthority( AuthotityConstant._ADMIN ) // c

                        // g: Búsquedas. Permite al usuario y al admin buscar/consultar.
                        .requestMatchers(HttpMethod.GET, "/api/monopatines/**").hasAnyAuthority( AuthotityConstant._USUARIO, AuthotityConstant._ADMIN )
                        .requestMatchers("/api/monopatines/**").hasAuthority( AuthotityConstant._ADMIN ) // CRUD completo

                        // =======================================================
                        // MS-FACTURAS Y TARIFA 🧾💲
                        // d. ADMIN: Total facturado en un rango de meses (GET Reporte)
                        // f. ADMIN: Ajuste de precios a partir de cierta fecha (POST/PUT)
                        // =======================================================
                        // Las reglas genéricas ya cubren (d) y (f), asumiendo que /api/facturas y /api/tarifas son solo ADMIN.
                        .requestMatchers("/api/facturas/**").hasAuthority( AuthotityConstant._ADMIN ) // d
                        .requestMatchers("/api/tarifas/**").hasAuthority( AuthotityConstant._ADMIN ) // f

                        // =======================================================
                        // MS-VIAJES 🗺️
                        // e. ADMIN: Usuarios que más utilizan (GET Reporte)
                        // h. USUARIO: Saber cuánto ha usado monopatines (GET Reporte personal)
                        // =======================================================
                        .requestMatchers("/api/viajes/reportes/**").hasAuthority( AuthotityConstant._ADMIN ) // e (Reportes avanzados de uso)

                        // h: El usuario solo puede ver su propio uso.
                        .requestMatchers(HttpMethod.GET, "/api/viajes/mi-uso/**").hasAuthority( AuthotityConstant._USUARIO )
                        .requestMatchers("/api/viajes/**").hasAnyAuthority( AuthotityConstant._USUARIO, AuthotityConstant._ADMIN ) // Iniciar/Finalizar Viaje

                        // =======================================================
                        // MS-USUARIO Y CUENTA 👤
                        // b. ADMIN: Anular cuentas de usuarios (PUT/DELETE en cuentas)
                        // =======================================================
                        // b: Anular cuentas está cubierto por la regla general de ADMIN para /api/cuentas
                        .requestMatchers("/api/usuarios").hasAuthority( AuthotityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/{idUsuario}/asignar-cuenta/{idCuenta}").hasAuthority( AuthotityConstant._USUARIO) // Mantengo el original
                        .requestMatchers("/api/cuentas/**").hasAuthority( AuthotityConstant._ADMIN) // b (Anular cuentas)

                        // =======================================================
                        // MS-IA 🧠
                        // Asumimos que los endpoints de IA son reportes o optimizaciones ADMIN.
                        // =======================================================
                        .requestMatchers("/api/ia/**").hasAuthority( AuthotityConstant._ADMIN )

                        // Cualquier otra ruta que no coincida con las anteriores requiere autenticación.
                        .anyRequest().authenticated()
                )
                .httpBasic( Customizer.withDefaults() )
                .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }

}
