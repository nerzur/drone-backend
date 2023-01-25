package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.service.DroneService;
import com.musala.demo.drone.util.ExceptionsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "drone")
public class DroneController {

    @Autowired
    DroneService droneService;
    @GetMapping
    public ResponseEntity<List <Drone>> listAllDrones(){
        log.info("listing all Drones");
        List<Drone> droneList = droneService.listAllDrone();
        log.info("Detected "+droneList.size());
        return ResponseEntity.ok(droneList);
    }

    @PostMapping
    public ResponseEntity<Drone> addDrone(@RequestBody Drone drone, BindingResult result){
        if(result.hasErrors()){
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Drone droneDb = droneService.createDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneDb);
    }
}
