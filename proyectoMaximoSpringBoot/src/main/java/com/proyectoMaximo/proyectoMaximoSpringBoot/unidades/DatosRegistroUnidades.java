package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

public record DatosRegistroUnidades(

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
}
