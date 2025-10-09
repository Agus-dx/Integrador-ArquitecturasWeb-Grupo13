package micro.example.enums;

    public enum CampoOrdenamientoEstudiante {
        NOMBRE("nombre"),
        APELLIDO("apellido"),
        DNI("dni"),
        EDAD("edad"),
        GENERO("genero"),
        CIUDAD("ciudadOrigen"); // Ojo: este es el nombre real del campo en la entidad

        private final String campoEntidad;

        CampoOrdenamientoEstudiante(String campoEntidad) {
            this.campoEntidad = campoEntidad;
        }

        public String getCampoEntidad() {
            return campoEntidad;
        }

        public static CampoOrdenamientoEstudiante from(String valor) {
            for (CampoOrdenamientoEstudiante c : CampoOrdenamientoEstudiante.values()) {
                if (c.name().equalsIgnoreCase(valor) || c.campoEntidad.equalsIgnoreCase(valor)) {
                    return c;
                }
            }
            throw new IllegalArgumentException("Campo de ordenamiento inválido: " + valor);
        }
    }
