/**
 * 🚀 Clase Principal y Punto de Arranque del Microservicio de IA.
 *
 * Esta clase inicia la aplicación Spring Boot, la cual proporciona la
 * funcionalidad Text-to-SQL a través de la integración con la API de Groq.
 * * * Arquitectura:
 * Este servicio conecta la interfaz de usuario con la base de datos de datos
 * mediante un LLM (Groq), actuando como un traductor de lenguaje natural a SQL.
 */
package org.example.ia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IaApplication.class, args);
    }

}
