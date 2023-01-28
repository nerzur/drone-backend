package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.DroneMedication;
import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.ui.MedicationQuantity;
import com.musala.demo.drone.ui.MedicationQuantityMinimal;
import com.musala.demo.drone.ui.MedicationsListByDroneResponse;

import java.util.List;

public interface DroneMedicationService {

    MedicationsListByDroneResponse findMedicationsByDrone(String droneSerialNumber);

    MedicationsListByDroneResponse sendDroneToDelivery(String droneSerialNumber, List<MedicationQuantityMinimal> medicationQuantityMinimalList);

    MedicationsListByDroneResponse deliverMedications(String droneSerialNumber);

    MedicationsListByDroneResponse returnDrone(String droneSerialNumber);

    MedicationsListByDroneResponse reuseDrone(String droneSerialNumber);
}
