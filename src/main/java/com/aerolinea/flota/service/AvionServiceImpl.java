package com.aerolinea.flota.service;

import com.aerolinea.flota.exception.DuplicateResourceException;
import com.aerolinea.flota.exception.ResourceNotFoundException;
import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import com.aerolinea.flota.repository.AerolineaRepository;
import com.aerolinea.flota.repository.AvionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvionServiceImpl implements AvionService {

    private final AvionRepository avionRepository;
    private final AerolineaRepository aerolineaRepository;

    public AvionServiceImpl(AvionRepository avionRepository, AerolineaRepository aerolineaRepository) {
        this.avionRepository = avionRepository;
        this.aerolineaRepository = aerolineaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avion> listarTodos() {
        return avionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Avion buscarPorId(Long id) {
        return avionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un avión con id " + id));
    }

    @Override
    @Transactional
    public Avion crear(Avion avion) {
        if (avionRepository.existsByMatricula(avion.getMatricula())) {
            throw new DuplicateResourceException(
                    "Ya existe un avión con la matrícula " + avion.getMatricula());
        }
        avion.setAerolinea(resolverAerolinea(avion.getAerolinea()));
        avion.setId(null);
        return avionRepository.save(avion);
    }

    @Override
    @Transactional
    public Avion actualizar(Long id, Avion datos) {
        Avion existente = buscarPorId(id);

        boolean cambiaMatricula = !existente.getMatricula().equals(datos.getMatricula());
        if (cambiaMatricula && avionRepository.existsByMatricula(datos.getMatricula())) {
            throw new DuplicateResourceException(
                    "Ya existe un avión con la matrícula " + datos.getMatricula());
        }

        existente.setMatricula(datos.getMatricula());
        existente.setModelo(datos.getModelo());
        existente.setAerolinea(resolverAerolinea(datos.getAerolinea()));
        existente.setCapacidad(datos.getCapacidad());
        existente.setEnMantenimiento(datos.isEnMantenimiento());
        existente.setCombustibleListo(datos.isCombustibleListo());
        existente.setLimpiezaLista(datos.isLimpiezaLista());
        existente.setUbicacion(datos.getUbicacion());
        return avionRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Avion existente = buscarPorId(id);
        avionRepository.delete(existente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avion> listarPorUbicacion(UbicacionAvion ubicacion) {
        return avionRepository.findByUbicacion(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Avion> listarEnMantenimiento() {
        return avionRepository.findByEnMantenimientoTrue();
    }

    /** Valida que la aerolínea enviada exista y devuelve la entidad gestionada. */
    private Aerolinea resolverAerolinea(Aerolinea referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new IllegalArgumentException("Debe indicar la aerolínea con su id: \"aerolinea\": {\"id\": 1}");
        }
        return aerolineaRepository.findById(referencia.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe una aerolínea con id " + referencia.getId()));
    }
}
