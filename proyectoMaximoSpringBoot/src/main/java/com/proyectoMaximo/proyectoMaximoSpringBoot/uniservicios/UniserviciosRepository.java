package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniserviciosRepository
        extends JpaRepository<Uniservicios, Long> {

    List<Uniservicios> findByCodigoServicioIn(List<Integer> codigos);
}