package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import java.util.List;

public record ResultadoBusquedaServicio(

        Integer codigoServicio,
        Integer cantidadRegistros,
        List<DatosCoincidenciaUniservicio> coincidencias
) {
}
