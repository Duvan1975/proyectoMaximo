package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUniservicios(

        @NotNull
        Long id,
        Integer nivelTension,
        Integer codigoServicio,
        String estructura,
        String observacion
) {
}
