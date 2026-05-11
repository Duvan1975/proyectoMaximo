package com.proyectoMaximo.proyectoMaximoSpringBoot.configuracion;

public record ConfiguracionActa(

        String nombreHoja,
        Integer columnaEstructura,
        Integer columnaDetalle,
        Integer columnaCant,
        Integer columnaNivel
) {
}
