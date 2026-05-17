package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

public record DatosListadoUniservicios(
        Long id,
        Integer nivelTension,
        Integer codigoServicio,
        String estructura,
        String observacion
) {
    public DatosListadoUniservicios(Uniservicios uniservicios) {
        this(
                uniservicios.getId(),
                uniservicios.getNivelTension(),
                uniservicios.getCodigoServicio(),
                uniservicios.getEstructura(),
                uniservicios.getObservacion()
        );
    }
}
