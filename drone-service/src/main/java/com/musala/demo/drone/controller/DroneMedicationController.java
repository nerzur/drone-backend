package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.service.DroneMedicationService;
import com.musala.demo.drone.service.DroneService;
import com.musala.demo.drone.ui.MedicationQuantity;
import com.musala.demo.drone.ui.MedicationQuantityMinimal;
import com.musala.demo.drone.ui.MedicationsListByDroneResponse;
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
@RequestMapping(value = "droneMedication")
public class DroneMedicationController {

    @Autowired
    DroneMedicationService droneMedicationService;

    @Autowired
    DroneService droneService;

    @GetMapping(path = "findMedicationsByDrone/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> listAllMedications(@PathVariable(required = true) String droneSerialNumber) {
        log.info("Finding the medications by Drone");
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.findMedicationsByDrone(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @PostMapping(path = "sendDroneToDelivery/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> sendDroneToDelivery(@PathVariable(required = true) String droneSerialNumber, @RequestBody List<MedicationQuantityMinimal> medicationQuantityMinimalList, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.sendDroneToDelivery(droneSerialNumber, medicationQuantityMinimalList);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @PostMapping(path = "deliverMedicines/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> deliverMedicines(@PathVariable(required = true) String droneSerialNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.deliverMedicines(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @PostMapping(path = "returnDrone/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> returnDrone(@PathVariable(required = true) String droneSerialNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.returnDrone(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @PostMapping(path = "reuseDrone/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> reuseDrone(@PathVariable(required = true) String droneSerialNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.reuseDrone(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

}
