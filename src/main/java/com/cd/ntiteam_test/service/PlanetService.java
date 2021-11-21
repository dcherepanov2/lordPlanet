package com.cd.ntiteam_test.service;

import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.exception.PlanetException;
import com.cd.ntiteam_test.repo.PlanetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;


@Service
public class PlanetService {

    private final PlanetRepo planetRepo;

    @Autowired
    public PlanetService(PlanetRepo planetRepo) {
        this.planetRepo = planetRepo;
    }

    public Planet addPlanet(Planet request) throws PlanetException {
        Planet planet = new Planet();
        planet.setName(request.getName());
        try {
            planetRepo.save(planet);
        } catch (DataIntegrityViolationException | InvalidDataAccessApiUsageException | TransactionSystemException e) {
            throw new PlanetException("Parameter name was not passed");
        }
        return planet;
    }

    public Planet deletePlanet(Long id) throws PlanetException {
        Planet planet = planetRepo.findByIdCustom(id);
        if (planet != null) {
            planetRepo.setLordNull(id);
            planetRepo.setLordNullFromLordPlanet(id);
            planetRepo.deletePlanet(id);
        } else
            throw new PlanetException("The planet with the specified id does not exist");
        return planet;
    }
}
