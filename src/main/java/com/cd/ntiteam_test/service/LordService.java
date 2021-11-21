package com.cd.ntiteam_test.service;

import com.cd.ntiteam_test.entity.Lord;
import com.cd.ntiteam_test.entity.Planet;
import com.cd.ntiteam_test.exception.LordException;
import com.cd.ntiteam_test.repo.LordRepo;
import com.cd.ntiteam_test.repo.PlanetRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LordService {

    private final LordRepo lordRepo;
    private final PlanetRepo planetRepo;

    public LordService(LordRepo lordRepo, PlanetRepo planetRepo) {
        this.lordRepo = lordRepo;
        this.planetRepo = planetRepo;
    }

    public Lord addLord(Lord request) throws LordException {
        Lord lord = new Lord();
        Lord check = null;
        try {
            if (request.getName() != null && request.getAge() != null)
                check = lordRepo.isExists(request.getName(), request.getAge());
            if (check != null)
                throw new LordException("An author with such data already exists.");
            lord.setName(request.getName());
            lord.setAge(request.getAge());
            lord.setPlanets(new ArrayList<>());
            lordRepo.save(lord);
        } catch (DataIntegrityViolationException | NullPointerException e) {
            throw new LordException("Bad request, please check required fields for input.");
        }

        return lord;
    }

    public Lord ruleThePlanet(Long id, Long[] planets) throws LordException {
        Lord lord;
        if (planets == null)
            throw new LordException("The planets parameter is empty");
        if (planets.length == 0)
            throw new LordException("The planets parameter is empty");
        if (id != null)
            lord = lordRepo.findByIdCustom(id);
        else
            throw new LordException("Id is null");
        if (lord == null) {
            throw new LordException("Lord with id: " + id + " does not exist");
        }
        List<Planet> localPlanets;
        try {
            localPlanets = lordRepo.getPlanets(lord.getId());
        }catch (Exception e){
            localPlanets = new ArrayList<>();
        }

        for (Long planetId : planets) {
            Planet planet = planetRepo.findByIdCustom(planetId);
            if (planet != null && planet.getLord() == null) {
                planet.setLord(lord);
                planetRepo.save(planet);
                localPlanets.add(planet);
            } else if (planet == null)
                throw new LordException("Planet with id: " + planetId + " does not exist");
            else if (planet.getLord() != null)
                throw new LordException("The planet with id: " + planetId + " already has an appointed leader");
        }

        lord.setPlanets(localPlanets);
        lordRepo.save(lord);
        return lord;
    }

    public List<Lord> getAllLoafers() {
        return lordRepo.findAllWherePlanetsNull();
    }

    public List<Lord> topYoungLord() {
        return lordRepo.topYoungLord();
    }
}
