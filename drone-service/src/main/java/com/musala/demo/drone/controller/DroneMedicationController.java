package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.service.DroneMedicationService;
import com.musala.demo.drone.service.DroneService;
import com.musala.demo.drone.ui.MedicationQuantityMinimal;
import com.musala.demo.drone.ui.MedicationsListByDroneResponse;
import com.musala.demo.drone.util.ErrorMessage;
import com.musala.demo.drone.util.ExceptionsBuilder;
import io.swagger.v3.oas.annotations.Operation;
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

/**
 * @author Gabriel
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "droneMedication")
public class DroneMedicationController {

    @Autowired
    DroneMedicationService droneMedicationService;

    @Autowired
    DroneService droneService;

    @Operation(summary = "Find medications by drone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Obtained the information from the drone and the medications it contains",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicationsListByDroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @GetMapping(path = "findMedicationsByDrone/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> listAllMedications(@PathVariable(required = true) String droneSerialNumber) {
        log.info("Finding the medications by Drone");
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.findMedicationsByDrone(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @Operation(summary = "Send drone to delivery")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The medications that the drone must deliver have been indicated and it is ready to deliver",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicationsListByDroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The drone must be in the IDLE state to proceed with the shipment of products, The indicated medications exceed the availability of the drone, It has not been possible to add the indicated Medications to the drone)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "sendDroneToDelivery/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> sendDroneToDelivery(@PathVariable(required = true) String droneSerialNumber, @RequestBody List<MedicationQuantityMinimal> medicationQuantityMinimalList, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.sendDroneToDelivery(droneSerialNumber, medicationQuantityMinimalList);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @Operation(summary = "Deliver Medications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone is at the delivery location and is ready to deliver the Medications",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicationsListByDroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The drone must be in the DELIVERING state to proceed with the shipment of products)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "deliverMedicines/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> deliverMedications(@PathVariable(required = true) String droneSerialNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.deliverMedications(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @Operation(summary = "Return Drone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone has made the delivery and is ready to return",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicationsListByDroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The drone must be in the DELIVERED state to proceed with the shipment of products)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "returnDrone/{droneSerialNumber}")
    public ResponseEntity<MedicationsListByDroneResponse> returnDrone(@PathVariable(required = true) String droneSerialNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        MedicationsListByDroneResponse medicationsListByDroneResponse = droneMedicationService.returnDrone(droneSerialNumber);
        return ResponseEntity.ok(medicationsListByDroneResponse);
    }

    @Operation(summary = "Reuse drone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone is ready to be used again",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MedicationsListByDroneResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The drone must be in the RETURNING or LOADED state to proceed with the shipment of products)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
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
