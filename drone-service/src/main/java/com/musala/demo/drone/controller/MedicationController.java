package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.service.MedicationService;
import com.musala.demo.drone.util.ErrorMessage;
import com.musala.demo.drone.util.ExceptionsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all medications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the medications list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Medication.class))) })
    })
    @GetMapping
    public ResponseEntity<List<Medication>> listAllMedications() {
        log.info("listing all Medications");
        List<Medication> medicationList = medicationService.listAllMedications();
        log.info("Detected " + medicationList.size());
        return ResponseEntity.ok(medicationList);
    }

    @Operation(summary = "Create a medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medication is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medication.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This Medication is already exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Medication> addMedication(@RequestBody Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Medication medicationDb = medicationService.createMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationDb);
    }

    @Operation(summary = "Edit a medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medication is edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medication.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated medication isn´t exists, The indicated medication cannot be edited as there is at least one product in the delivery process of this type)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/edit")
    public ResponseEntity<Medication> editMedication(@RequestBody Medication medication, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Medication medicationDb = medicationService.updateMedication(medication);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationDb);
    }

    @Operation(summary = "Delete a medication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medication is deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medication.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated medication isn´t exists, The indicated medication cannot be eliminated as there is at least one product in the delivery process of this type)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
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
