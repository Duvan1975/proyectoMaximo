package com.proyectoMaximo.proyectoMaximoSpringBoot.uniservicios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniserviciosRepository
        extends JpaRepository<Uniservicios, Long> {

    List<Uniservicios> findByCodigoServicioIn(List<Integer> codigos);

    Page<Uniservicios> findByCodigoServicio(String codigoServicio, Pageable paginacion);
}