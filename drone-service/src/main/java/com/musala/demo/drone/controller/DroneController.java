package com.musala.demo.drone.controller;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.service.DroneService;
import com.musala.demo.drone.ui.BatteryCapacityResponse;
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

/**
 * @author Gabriel
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "drone")
public class DroneController {

    @Autowired
    DroneService droneService;

    @Operation(summary = "Get all drones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the drones list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Drone.class))) })
    })
    @GetMapping
    public ResponseEntity<List<Drone>> listAllDrones() {
        log.info("listing all Drones");
        List<Drone> droneList = droneService.listAllDrone();
        log.info("Detected " + droneList.size());
        return ResponseEntity.ok(droneList);
    }

    @Operation(summary = "Create a dron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drone.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This drone is already exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Drone> addDrone(@RequestBody Drone drone, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Drone droneDb = droneService.createDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneDb);
    }

    @Operation(summary = "Edit a dron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone is edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drone.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The indicated drone cannot be edited, as it is in some delivery process)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/edit")
    public ResponseEntity<Drone> editDrone(@RequestBody Drone drone, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Drone droneDb = droneService.updateDrone(drone);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneDb);
    }

    @Operation(summary = "Delete a dron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The drone is deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drone.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists, The indicated drone cannot be eliminated, as it is in some delivery process, This drone can´t be deleted)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/delete")
    public ResponseEntity<Drone> deleteDrone(@RequestBody Drone drone, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Drone droneDb = droneService.deleteDrone(drone.getSerialNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(droneDb);
    }

    @Operation(summary = "Update the battery percent for a custom dron")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The battery percent of the drone is updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Drone.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated value for new percent is invalid, The indicated drone isn´t exists, The drone is in Loading state, the indicated value for the new battery percentage is lower than the previous value, The drone is not in Loading state, the indicated value for the new battery percentage is higher than the previous value, The drone is in Loaded state, the new battery value should be 100 percent)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/updateDroneBattery/{serialNumber}/{newPercent}")
    public ResponseEntity<Drone> updateDroneBatteryPercent(BindingResult result, @PathVariable(required = true) String newPercent, @PathVariable(required = true) String serialNumber) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Drone droneDb = droneService.updateBattery(serialNumber, newPercent);
        return ResponseEntity.status(HttpStatus.CREATED).body(droneDb);
    }

    @Operation(summary = "Get all available drones (drones whose status is IDLE)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found available drones",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Drone.class))) })
    })
    @GetMapping(path = "getAvailableDrones")
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        log.info("listing available Drones");
        List<Drone> droneList = droneService.getAvailableDrones();
        log.info("Detected " + droneList.size());
        return ResponseEntity.ok(droneList);
    }

    @Operation(summary = "Get the battery capacity by dron serial number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The battery capacity for the drone is obtained successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BatteryCapacityResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated drone isn´t exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @GetMapping(path = "getBatteryCapacityByDrone/{serialNumber}")
    public ResponseEntity<BatteryCapacityResponse> getBatteryCapacityByDrone(@PathVariable(required = true) String serialNumber) {
        log.info("getting the battery capacity for the drone " + serialNumber);
        BatteryCapacityResponse batteryCapacityResponse = droneService.getBatteryCapacityByDrone(serialNumber);
        return ResponseEntity.ok(batteryCapacityResponse);
    }
}
