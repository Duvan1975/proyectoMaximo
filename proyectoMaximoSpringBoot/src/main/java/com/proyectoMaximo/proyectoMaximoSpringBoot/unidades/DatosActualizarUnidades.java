package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUnidades(

        @NotNull
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
}
