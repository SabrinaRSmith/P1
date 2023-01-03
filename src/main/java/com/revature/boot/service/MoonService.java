package com.revature.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.entities.Moon;
import com.revature.boot.exceptions.EntityNotFound;
import com.revature.boot.repository.MoonDao;

@Service
public class MoonService {

    @Autowired
    private MoonDao moonDao;

    public List<Moon> getAllMoons(){
        List<Moon> moons = this.moonDao.findAll();
        if(moons.size() != 0){
            return moons;
        }else{
            throw new EntityNotFound("No moons were found");
        }
    }

    public Moon getMoonByName(String name){
        Optional<Moon> possibleMoon = this.moonDao.findByName(name);
        if(possibleMoon.isPresent()){
            return possibleMoon.get();
        }else{
            throw new EntityNotFound("Moon not found");
        }
    }

    public Moon getMoonById(int id){
        Optional<Moon> possibleMoon = this.moonDao.findById(id);
        if(possibleMoon.isPresent()){
            return possibleMoon.get();
        }else{
            throw new EntityNotFound("Moon not found");
        }
    }

    public String createMoon(Moon moon){
        this.moonDao.createMoon(moon.getName(), moon.getMyPlanetId());
        return "Moon created";
    }

    public String deleteMoon(int id){
        this.moonDao.deleteById(id);
        return "Deleted moon with id of " + id;
    }

    public List<Moon> getMoonsFromPlanet(int planetId){
        List<Moon> moons = this.moonDao.findAllByMyPlanetId(planetId);
        if(moons.size() != 0){
            return moons;
        }else{
            throw new EntityNotFound("No moons were found");
        }
    }
    
}
