/**
 * 🏷️ Enumeración (Enum) de Estados Operacionales del Monopatín.
 *
 * Define la lista finita de estados en los que puede encontrarse un monopatín:
 * - ACTIVO: Monopatín en uso por un cliente (dentro de un viaje).
 * - MANTENIMIENTO: Monopatín retirado del servicio (ej. por alto kilometraje).
 * - LIBRE: Monopatín disponible para ser alquilado (ya sea en una parada o no).
 *
 * El método 'perteneceAlEnum' es una utilidad clave utilizada en la capa de Servicio
 * para validar si la cadena de estado enviada por el cliente o por otro microservicio
 * es válida antes de ser persistida.
 */
package com.grupo13.microserviciomonopatin.model;

public enum Estado {
        ACTIVO,
        MANTENIMIENTO,
        LIBRE;

    public static Estado perteneceAlEnum(String estado) {
            try {
                return Estado.valueOf(estado.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                return null;
            }
    }
}
