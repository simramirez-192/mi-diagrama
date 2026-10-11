package com.aerolinea.flota.repository;

import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepository extends JpaRepository<Avion, Long> {

    Optional<Avion> findByMatricula(String matricula);

    List<Avion> findByUbicacion(UbicacionAvion ubicacion);

    List<Avion> findByEnMantenimientoTrue();

    boolean existsByMatricula(String matricula);
}
