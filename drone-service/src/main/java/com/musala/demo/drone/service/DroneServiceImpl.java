package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

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
        if(null != getDroneBySerialNumber(drone.getSerialNumber()))
            return null;
        return droneRepository.save(drone);
    }

    @Override
    public Drone deleteDrone(Long id) {
        Drone droneDb = getDroneById(id);
        if(null == droneDb)
            return null;
        droneRepository.delete(droneDb); //TODO Verify if any Medication need a drone
        return droneDb;
    }

    @Override
    public Drone updateDrone(Drone drone) {
        Drone droneDb = getDroneById(drone.getId());
        double weightCount = 0;
        if(null == droneDb)
            return null;
        droneDb.setState(drone.getState());
        droneDb.setModel(drone.getModel());
        droneDb.setBatteryCapacity(drone.getBatteryCapacity());
        droneDb.setWeight(drone.getWeight());
        for (Medication medication : drone.getMedicationList()) {
            weightCount+=medication.getWeight();
        }
        if(droneDb.getWeight()<weightCount)
            return null;
        droneDb.setMedicationList(drone.getMedicationList());
        return null;
    }

    @Override
    public Drone updateBattery(String serialNumber, Integer newPercent) {
        Drone droneDb = getDroneBySerialNumber(serialNumber);
        if(null == droneDb)
            return null;
        droneDb.setBatteryCapacity(newPercent);
        return updateDrone(droneDb);
    }
}
