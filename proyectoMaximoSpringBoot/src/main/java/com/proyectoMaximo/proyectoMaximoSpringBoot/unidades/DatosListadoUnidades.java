package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

public record DatosListadoUnidades(
        Long id,
        String uc,
        String descripcion,
        String unidadMedida,
        Integer version,
        String regulacionVersion,
        Integer nivelUc,
        String equivale,
        String planta,
        String estado
) {
    public DatosListadoUnidades(Unidades unidades) {
        this(
                unidades.getId(),
                unidades.getUc(),
                unidades.getDescripcion(),
                unidades.getUnidadMedida(),
                unidades.getVersion(),
                unidades.getRegulacionVersion(),
                unidades.getNivelUc(),
                unidades.getEquivale(),
                unidades.getPlanta(),
                unidades.getEstado()
        );
    }
}
