package com.proyectoMaximo.proyectoMaximoSpringBoot.sercivios;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DatosRegistroServicios(

        @NotNull(message = "El código de servicio no debe estar vacío")
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
