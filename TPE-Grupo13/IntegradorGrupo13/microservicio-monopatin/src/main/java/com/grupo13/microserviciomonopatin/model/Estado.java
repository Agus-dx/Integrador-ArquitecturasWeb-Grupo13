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
