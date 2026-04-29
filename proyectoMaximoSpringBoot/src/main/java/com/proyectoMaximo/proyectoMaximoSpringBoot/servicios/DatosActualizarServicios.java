package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DatosActualizarServicios(

        @NotNull
        Long id,

        Long codigoServicio,
        Integer item,
        Integer agrupacion,
        String estructura,
        String detalle,
        String unidad,
        BigDecimal valorUnitario,
        Integer nivelTension,
        String descripcion
) {
}
