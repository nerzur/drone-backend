package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.service.MedicationService;
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
@RequestMapping(value = "medication")
public class MedicationController {

    @Autowired
    MedicationService medicationService;

    @GetMapping
    public ResponseEntity<List<Medication>> listAllMedications() {
        log.info("listing all Medications");
        List<Medication> medicationList = medicationService.listAllMedications();
        log.info("Detected " + medicationList.size());
        return ResponseEntity.ok(medicationList);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Medication medicationDb = medicationService.createMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationDb);
    }

    @PostMapping(path = "/edit")
    public ResponseEntity<Medication> editMedication(@RequestBody Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Medication medicationDb = medicationService.updateMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationDb);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<Medication> deleteDrone(@RequestBody Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Medication medicationDb = medicationService.deleteMedication(medication.getCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationDb);
    }
}
