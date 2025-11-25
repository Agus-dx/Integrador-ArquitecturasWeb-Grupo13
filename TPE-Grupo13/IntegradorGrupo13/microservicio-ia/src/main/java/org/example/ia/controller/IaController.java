/**
 * 💻 Controlador REST (IaController) para el Microservicio de IA.
 *
 * Este controlador expone el endpoint principal para interactuar con la IA
 * (Groq/Ollama).
 * * Endpoint: POST /api/ia/prompt
 * Recibe un prompt (pregunta o instrucción en lenguaje natural) del cliente.
 * * Responsabilidad:
 * 1. Recepción: Recibe la instrucción del usuario en el cuerpo de la solicitud (`@RequestBody String prompt`).
 * 2. Delegación: Delega inmediatamente la lógica compleja (interacción con el LLM,
 * procesamiento de la respuesta, potencial ejecución de SQL) al `IaService`.
 * 3. Manejo de Errores: Captura cualquier excepción en el proceso de IA/Base de Datos
 * y devuelve un código 500 (`INTERNAL_SERVER_ERROR`).
 *
 * NOTA: El comentario interno describe un flujo complejo donde la IA genera SQL,
 * el service la ejecuta y devuelve los resultados. Esto implica que el IaService
 * tendrá que manejar la conexión a la base de datos y la ejecución dinámica de consultas.
 */
package org.example.ia.controller;

import org.example.ia.service.IaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/api/ia")
    public class IaController {    // IaController exponene el endpoint REST que recibe prompts y delega a IaService.


        /**🔑 que va a hacer mi app en conjunto
         *  IaController recibe prompt →
         *  IaService añade esquema + manda a Ollama →
         *  OllamaClient se conecta a la API →
         *  Respuesta: IA devuelve consulta SQL →
         *  IaService la ejecuta →
         *  Respuesta JSON con resultados.
         */

        @Autowired
        private IaService iaService;

        @PostMapping(value = "/prompt", produces = "application/json") // 👉 Define endpoint POST /api/ia/prompt que recibe un prompt como cuerpo JSON.
        public ResponseEntity<?> procesarPrompt(@RequestBody String prompt) {
            try {
                return iaService.procesarPrompt(prompt);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el prompt: " + e.getMessage());
            }
        }
    }

