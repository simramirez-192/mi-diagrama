package com.aerolinea.flota.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aerolinea.flota.model.Aerolinea;
import com.aerolinea.flota.model.Avion;
import com.aerolinea.flota.model.UbicacionAvion;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AvionRepositoryTest {

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AerolineaRepository aerolineaRepository;

    private Avion guardarAvion(String matricula, UbicacionAvion ubicacion, boolean mantenimiento, Aerolinea a) {
        Avion avion = new Avion();
        avion.setMatricula(matricula);
        avion.setModelo("Boeing 787");
        avion.setCapacidad(300);
        avion.setEnMantenimiento(mantenimiento);
        avion.setUbicacion(ubicacion);
        avion.setAerolinea(a);
        return avionRepository.save(avion);
    }

    private Aerolinea guardarAerolinea() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("LATAM");
        aerolinea.setCodigoIata("LA");
        return aerolineaRepository.save(aerolinea);
    }

    @Test
    void findByUbicacionFiltraCorrectamente() {
        Aerolinea aerolinea = guardarAerolinea();
        guardarAvion("CC-AAA", UbicacionAvion.EN_TIERRA, false, aerolinea);
        guardarAvion("CC-AAB", UbicacionAvion.VOLANDO, false, aerolinea);

        List<Avion> enTierra = avionRepository.findByUbicacion(UbicacionAvion.EN_TIERRA);

        assertEquals(1, enTierra.size());
        assertEquals("CC-AAA", enTierra.get(0).getMatricula());
    }

    @Test
    void findByEnMantenimientoTrueDevuelveSoloLosEnMantenimiento() {
        Aerolinea aerolinea = guardarAerolinea();
        guardarAvion("CC-AAA", UbicacionAvion.EN_TIERRA, true, aerolinea);
        guardarAvion("CC-AAB", UbicacionAvion.EN_TIERRA, false, aerolinea);

        List<Avion> enMantenimiento = avionRepository.findByEnMantenimientoTrue();

        assertEquals(1, enMantenimiento.size());
        assertTrue(enMantenimiento.get(0).isEnMantenimiento());
    }

    @Test
    void existsByMatriculaDetectaDuplicados() {
        Aerolinea aerolinea = guardarAerolinea();
        guardarAvion("CC-AAA", UbicacionAvion.EN_TIERRA, false, aerolinea);

        assertTrue(avionRepository.existsByMatricula("CC-AAA"));
    }
}
