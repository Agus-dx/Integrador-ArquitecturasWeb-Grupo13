/**
 * 📝 DTO de Entrada para Actualizaciones Parciales (Patch).
 *
 * Esta clase se utiliza exclusivamente para recibir los datos de una
 * **actualización parcial (PATCH)** de una Parada.
 * Su estructura como 'record' inmutable es ideal para transferir datos
 * de manera segura desde la solicitud HTTP al servicio.
 * Al incluir todos los campos opcionales, el servicio puede verificar qué campos
 * están presentes en el cuerpo de la petición y aplicarlos a la entidad existente.
 */
package com.grupo13.microservicioparada.dtos;

public record ParadaPatchDTO(
        String nombre,
        String ciudad,
        String direccion,
        Double latitud,
        Double longitud
) {
}
