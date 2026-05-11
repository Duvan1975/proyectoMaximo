package com.proyectoMaximo.proyectoMaximoSpringBoot.unidades;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnidadesRepository extends JpaRepository<Unidades, Long> {

    List<Unidades> findByDescripcionContainingIgnoreCase(String descripcion);
}
