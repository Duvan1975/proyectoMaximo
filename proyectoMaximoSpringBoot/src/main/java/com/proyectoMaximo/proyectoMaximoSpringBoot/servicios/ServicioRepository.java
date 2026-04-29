package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    boolean existsByCodigoServicio(
            @NotNull(message = "Código de servicio DUPLICADO")Long codigo);
}
