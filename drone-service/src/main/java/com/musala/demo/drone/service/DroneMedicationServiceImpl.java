package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.*;
import com.musala.demo.drone.repository.DroneMedicationRepository;
import com.musala.demo.drone.repository.DroneRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import com.musala.demo.drone.ui.MedicationQuantity;
import com.musala.demo.drone.ui.MedicationQuantityMinimal;
import com.musala.demo.drone.ui.MedicationsListByDroneResponse;
import com.musala.demo.drone.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Gabriel
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class DroneMedicationServiceImpl implements DroneMedicationService {

    private final DroneMedicationRepository droneMedicationRepository;
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final BindingResult result = new BindException(new Exception(), "");

    private final String className = "DroneMedication";

    @Override
    public MedicationsListByDroneResponse findMedicationsByDrone(String droneSerialNumber) {
        Drone droneDb = droneRepository.findBySerialNumber(droneSerialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result, className, "The indicated drone isn´t exists.");
        List<DroneMedication> dronemedicarionQuantityList = droneMedicationRepository.findByDrone(droneDb);
        List<MedicationQuantity> medicarionQuantityList = new ArrayList<>();
        dronemedicarionQuantityList.forEach(droneMedication -> {
            MedicationQuantity medicationQuantity = MedicationQuantity.builder()
                    .medication(medicationRepository.findById(droneMedication.getMedication().getId()).orElse(null))
                    .quantity(droneMedication.getQuantity())
                    .build();
            medicarionQuantityList.add(medicationQuantity);
        });
        return MedicationsListByDroneResponse.builder()
                .drone(droneDb)
                .medicationQuantityList(medicarionQuantityList)
                .build();
    }

    @Override
    public MedicationsListByDroneResponse sendDroneToDelivery(String droneSerialNumber, List<MedicationQuantityMinimal> medicationQuantityMinimalList) {
        List<MedicationQuantity> medicationQuantityListResponse = new ArrayList<>();
        Drone droneDb = droneRepository.findBySerialNumber(droneSerialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result, className, "The indicated drone isn´t exists.");
        if(!droneDb.getState().equals(State.IDLE.toString()))
            ExceptionsBuilder.launchException(result, className, "The drone must be in the IDLE state to proceed with the shipment of products.");
        List<MedicationQuantity> medicationQuantityList = new ArrayList<>();
        AtomicReference<Double> availableWeight = new AtomicReference<>(droneDb.getWeightLimit());
        medicationQuantityMinimalList.forEach(medicationQuantityMinimal -> {
            Medication medication = medicationRepository.findByCode(medicationQuantityMinimal.getMedicationCode());
            if (null != medication) {
                MedicationQuantity medicationQuantity = (MedicationQuantity.builder()
                        .medication(medication)
                        .quantity(medicationQuantityMinimal.getQuantity())
                        .build());
                medicationQuantityList.add(medicationQuantity);
                availableWeight.updateAndGet(v -> v - medicationQuantity.getMedication().getWeight() * medicationQuantity.getQuantity());
            }
        });
        if (availableWeight.get() < 0)
            ExceptionsBuilder.launchException(result, className, "The indicated medications exceed the availability of the drone.");
        for (MedicationQuantity medicationQuantity : medicationQuantityList) {
            DroneMedicationPK droneMedicationPK = DroneMedicationPK.builder()
                    .droneId(droneDb.getId())
                    .medicationId(medicationQuantity.getMedication().getId())
                    .build();
            DroneMedication droneMedication = DroneMedication.builder()
                    .droneMedicationPK(droneMedicationPK)
                    .medication(medicationQuantity.getMedication())
                    .drone(droneDb)
                    .quantity(medicationQuantity.getQuantity())
                    .build();
            droneMedication = droneMedicationRepository.save(droneMedication);
            if(null!=droneMedication)
                medicationQuantityListResponse.add(medicationQuantity);
        }
        if(medicationQuantityListResponse.isEmpty())
            ExceptionsBuilder.launchException(result, className, "It has not been possible to add the indicated Medications to the drone.");
        droneDb.setState(State.DELIVERING.toString());
        droneDb = droneRepository.save(droneDb);
        return MedicationsListByDroneResponse.builder()
                .medicationQuantityList(medicationQuantityListResponse)
                .drone(droneDb)
                .build();
    }

    @Override
    public MedicationsListByDroneResponse deliverMedications(String droneSerialNumber) {
        Drone droneDb = droneRepository.findBySerialNumber(droneSerialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result, className, "The indicated drone isn´t exists.");
        if(!droneDb.getState().equals(State.DELIVERING.toString()))
            ExceptionsBuilder.launchException(result, className, "The drone must be in the DELIVERING state to proceed with the shipment of products.");
        droneDb.setState(State.DELIVERED.toString());
        droneDb = droneRepository.save(droneDb);
        List<DroneMedication> dronemedicarionQuantityList = droneMedicationRepository.findByDrone(droneDb);
        List<MedicationQuantity> medicarionQuantityList = new ArrayList<>();
        dronemedicarionQuantityList.forEach(droneMedication -> {
            MedicationQuantity medicationQuantity = MedicationQuantity.builder()
                    .medication(medicationRepository.findById(droneMedication.getMedication().getId()).orElse(null))
                    .quantity(droneMedication.getQuantity())
                    .build();
            medicarionQuantityList.add(medicationQuantity);
        });
        return MedicationsListByDroneResponse.builder()
                .drone(droneDb)
                .medicationQuantityList(medicarionQuantityList)
                .build();
    }

    @Override
    public MedicationsListByDroneResponse returnDrone(String droneSerialNumber) {
        Drone droneDb = droneRepository.findBySerialNumber(droneSerialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result, className, "The indicated drone isn´t exists.");
        if(!droneDb.getState().equals(State.DELIVERED.toString()))
            ExceptionsBuilder.launchException(result, className, "The drone must be in the DELIVERED state to proceed with the shipment of products.");
        droneDb.setState(State.RETURNING.toString());
        droneDb = droneRepository.save(droneDb);
        List<DroneMedication> droneMedicationList = droneMedicationRepository.findByDrone(droneDb);
        droneMedicationRepository.deleteAll(droneMedicationList);
        return MedicationsListByDroneResponse.builder()
                .drone(droneDb)
                .medicationQuantityList(new ArrayList<>())
                .build();
    }

    @Override
    public MedicationsListByDroneResponse reuseDrone(String droneSerialNumber) {
        Drone droneDb = droneRepository.findBySerialNumber(droneSerialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result, className, "The indicated drone isn´t exists.");
        if(!droneDb.getState().equals(State.RETURNING.toString()) && !droneDb.getState().equals(State.LOADED.toString()))
            ExceptionsBuilder.launchException(result, className, "The drone must be in the RETURNING or LOADED state to proceed with the shipment of products.");
        droneDb.setState(State.IDLE.toString());
        droneDb = droneRepository.save(droneDb);
        return MedicationsListByDroneResponse.builder()
                .drone(droneDb)
                .medicationQuantityList(new ArrayList<>())
                .build();
    }
}
