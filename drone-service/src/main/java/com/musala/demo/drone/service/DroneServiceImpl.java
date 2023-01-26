package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.State;
import com.musala.demo.drone.repository.DroneRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import com.musala.demo.drone.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final BindingResult result = new BindException(new Exception(), "");

    private final String droneClassName = "Drone";

    @Override
    public List<Drone> listAllDrone() {
        return droneRepository.findAll();
    }

    @Override
    public Drone getDroneById(Long id) {
        return droneRepository.findById(id).orElse(null);
    }

    @Override
    public Drone getDroneBySerialNumber(String serialNumber) {
        return droneRepository.findBySerialNumber(serialNumber);
    }

    @Override
    public Drone createDrone(Drone drone) {
        if (null != getDroneBySerialNumber(drone.getSerialNumber()))
            ExceptionsBuilder.launchException(result,droneClassName, "This drone is already exist.");
        if (isDroneOverWeight(drone)) {
            ExceptionsBuilder.launchException(result,droneClassName, "The drone is over Weight.");
        }
        drone.setState(State.IDLE.toString());
        return droneRepository.save(drone);
    }

    @Override
    public Drone deleteDrone(String serialNumber) {
        Drone droneDb = getDroneBySerialNumber(serialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
        droneRepository.delete(droneDb);
        if (null != getDroneById(droneDb.getId()))
            ExceptionsBuilder.launchException(result,droneClassName, "This drone can´t be deleted.");
        return droneDb;
    }

    @Override
    public Drone updateDrone(Drone drone) {
        Drone droneDb = getDroneById(drone.getId());
        if (null == droneDb) {
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
            return null;
        }
        droneDb.setState(drone.getState());
        droneDb.setModel(drone.getModel());
        droneDb.setBatteryCapacity(drone.getBatteryCapacity());
        droneDb.setWeight(drone.getWeight());
        if (isDroneOverWeight(drone))
            ExceptionsBuilder.launchException(result,droneClassName, "The drone is over Weight.");
        return droneRepository.save(droneDb);
    }

    @Override
    public Drone updateBattery(String serialNumber, String newPercent) {
        Integer percent = 0;
        try {
            percent = Integer.parseInt(newPercent);
            if (percent < 0 || percent > 100)
                throw new Exception();
        } catch (Exception ex) {
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated value for new percent is invalid.");
        }
        Drone droneDb = getDroneBySerialNumber(serialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
        droneDb.setBatteryCapacity(percent);
        return updateDrone(droneDb);
    }

    //TODO Verify the available states for the drone
    @Override
    public Drone updateDroneState(String serialNumber, String newState) {
        Drone droneDb = droneRepository.findBySerialNumber(serialNumber);
        if(null == droneDb)
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
        droneDb.setState(newState);
        return droneRepository.save(droneDb);
    }

    //TODO Load the Medications by drone and verify the OverWeight
    private boolean isDroneOverWeight(Drone drone) {
        double weightCount = 0;
//        for (Medication medication : drone.getMedicationList()) {
//            weightCount+=medication.getWeight();
//        }
        return drone.getWeight() < weightCount;
    }
}
