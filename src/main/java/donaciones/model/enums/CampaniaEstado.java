package donaciones.model.enums;

public enum CampaniaEstado {
    BORRADOR("Borrador", "Campaña en fase de preparación"),
    ACTIVA("Activa", "Campaña recibiendo donaciones"),
    PAUSADA("Pausada", "Campaña temporalmente suspendida"),
    COMPLETADA("Completada", "Campaña que alcanzó sus metas"),
    CANCELADA("Cancelada", "Campaña anulada"),
    ARCHIVADA("Archivada", "Campaña finalizada y archivada");

    private final String nombre;
    private final String descripcion;

    CampaniaEstado(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static CampaniaEstado fromString(String text) {
        for (CampaniaEstado estado : CampaniaEstado.values()) {
            if (estado.name().equalsIgnoreCase(text) || 
                estado.getNombre().equalsIgnoreCase(text)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("No se encontró estado con valor: " + text);
    }

    public boolean isEditable() {
        return this == BORRADOR || this == PAUSADA;
    }

    public boolean isActive() {
        return this == ACTIVA;
    }

    public boolean isFinalState() {
        return this == COMPLETADA || this == CANCELADA || this == ARCHIVADA;
    }
}