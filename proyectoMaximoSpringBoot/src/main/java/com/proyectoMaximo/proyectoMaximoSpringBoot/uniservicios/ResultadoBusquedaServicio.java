package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import java.util.List;

public record ResultadoBusquedaServicio(

        Integer codigoServicio,
        List<DatosCoincidenciaUniservicio> coincidencias
) {
}
