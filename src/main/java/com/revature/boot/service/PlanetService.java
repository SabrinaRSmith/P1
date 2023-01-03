package com.revature.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.entities.Planet;
import com.revature.boot.exceptions.EntityNotFound;
import com.revature.boot.repository.PlanetDao;

@Service
public class PlanetService {

    @Autowired
    private PlanetDao planetDao;

    public List<Planet> getAllPlanets(){
        List<Planet> planets = this.planetDao.findAll();
        if(planets.size() != 0){
            return planets;
        }else{
            throw new EntityNotFound("No planets were found");
        }
    }

    public Planet getPlanetByName(String name){
        Optional<Planet> possiblePlanet = this.planetDao.findByName(name);
        if(possiblePlanet.isPresent()){
            return possiblePlanet.get();
        }else{
            throw new EntityNotFound("Planet not found");
        }
    }

    public Planet getPlanetById(int id){
        Optional<Planet> possiblePlanet = this.planetDao.findById(id);
        if(possiblePlanet.isPresent()){
            return possiblePlanet.get();
        }else{
            throw new EntityNotFound("Planet not found");
        }
    }

    public String createPlanet(Planet planet){
        this.planetDao.createPlanet(planet.getName(), planet.getOwnerId());
        return "Planet created";
    }

    public String deletePlanet(int id){
        this.planetDao.deleteById(id);
        return "Deleted planet with id of " + id;
    }
    
}
