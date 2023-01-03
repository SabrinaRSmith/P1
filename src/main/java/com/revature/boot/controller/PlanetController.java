package com.revature.boot.controller;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.entities.Planet;
import com.revature.boot.exceptions.AuthenticationFailed;
import com.revature.boot.exceptions.EntityNotFound;
import com.revature.boot.service.PlanetService;

@RestController
public class PlanetController {

    private static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);
    
    @Autowired
    private PlanetService planetService;

    @ExceptionHandler(AuthenticationFailed.class)
     public ResponseEntity<String> authenticationFailed(AuthenticationFailed e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
     }
    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> sqlIssue(PSQLException e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> deleteFailed(EmptyResultDataAccessException e){
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>("could not delete planet", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/planets")
    public ResponseEntity<List<Planet>> getAllPlanets(){
        return new ResponseEntity<>(this.planetService.getAllPlanets(), HttpStatus.OK);
    }

    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> getPlanetByName(@PathVariable String name){
        return new ResponseEntity<>(this.planetService.getPlanetByName(name), HttpStatus.OK);
    }

    @GetMapping("/api/planet/id/{id}")
    public ResponseEntity<Planet> getPlanetById(@PathVariable int id){
        return new ResponseEntity<>(this.planetService.getPlanetById(id), HttpStatus.OK);
    }

    @PostMapping("/api/planet")
    public ResponseEntity<String> createPlanet(@RequestBody Planet planet){
        return new ResponseEntity<>(this.planetService.createPlanet(planet), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/planet/{id}")
    public ResponseEntity<String> deletePlanet(@PathVariable int id){
        return new ResponseEntity<>(this.planetService.deletePlanet(id), HttpStatus.OK);
    }

}
