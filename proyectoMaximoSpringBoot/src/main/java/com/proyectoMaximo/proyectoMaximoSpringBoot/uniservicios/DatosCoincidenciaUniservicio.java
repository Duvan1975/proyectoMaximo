package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

public record DatosCoincidenciaUniservicio(

        Long id,
        Integer nivelTension,
        Integer codigoServicio,
        String estructura,
        String observacion
) {

    public DatosCoincidenciaUniservicio(Uniservicios u) {
        this(
                u.getId(),
                u.getNivelTension(),
                u.getCodigoServicio(),
                u.getEstructura(),
                u.getObservacion()
        );
    }
}
