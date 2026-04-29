package com.proyectoMaximo.proyectoMaximoSpringBoot.servicios;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired ServicioRepository servicioRepository;

    public void registrarServicio(DatosRegistroServicios datos) {

        if (servicioRepository.existsByCodigoServicio(datos.codigoServicio())) {
            throw new RuntimeException("Código de servicio duplicado");
        }
        servicioRepository.save(new Servicio(datos));
    }
    public Page<DatosListadoServicios> listarServicios(Pageable paginacion) {
        return servicioRepository
                .findAll(paginacion)
                .map(DatosListadoServicios::new);
    }

    @Transactional
    public void actualizarServicio(DatosActualizarServicios datos) {
        Servicio servicio = servicioRepository.getReferenceById(datos.id());

        if (datos.codigoServicio() != null) servicio.setCodigoServicio(datos.codigoServicio());
        if (datos.item() != null) servicio.setItem(datos.item());
        if (datos.agrupacion() != null) servicio.setAgrupacion(datos.agrupacion());
        if (datos.estructura() != null) servicio.setEstructura(datos.estructura());
        if (datos.detalle() != null) servicio.setDetalle(datos.detalle());
        if (datos.unidad() != null) servicio.setUnidad(datos.unidad());
        if (datos.valorUnitario() != null) servicio.setValorUnitario(datos.valorUnitario());
        if (datos.nivelTension() != null) servicio.setNivelTension(datos.nivelTension());
        if (datos.descripcion() != null) servicio.setDescripcion(datos.descripcion());
    }

    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }
}
