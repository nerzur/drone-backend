package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.State;
import com.musala.demo.drone.repository.DroneMedicationRepository;
import com.musala.demo.drone.repository.DroneRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import com.musala.demo.drone.ui.BatteryCapacityResponse;
import com.musala.demo.drone.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneMedicationRepository droneMedicationRepository;
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
        drone.setState(State.IDLE.toString());
        return droneRepository.save(drone);
    }

    @Override
    public Drone deleteDrone(String serialNumber) {
        Drone droneDb = getDroneBySerialNumber(serialNumber);
        if (null == droneDb)
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
        if(!droneDb.getState().equals(State.IDLE.toString()))
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone cannot be eliminated, as it is in some delivery process.");
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
        if(!droneDb.getState().equals(State.IDLE.toString()))
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone cannot be edited, as it is in some delivery process.");
        droneDb.setModel(drone.getModel());
        droneDb.setBatteryCapacity(drone.getBatteryCapacity());
        droneDb.setWeight(drone.getWeight());
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
        if(droneDb.getState().equals(State.LOADING.toString())){
            if(percent < droneDb.getBatteryCapacity())
                ExceptionsBuilder.launchException(result,droneClassName, "The drone is in Loading state, the indicated value for the new battery percentage is lower than the previous value.");
        }
        else if(!droneDb.getState().equals(State.LOADED.toString())){
            if(percent > droneDb.getBatteryCapacity())
                ExceptionsBuilder.launchException(result,droneClassName, "The drone is not in Loading state, the indicated value for the new battery percentage is higher than the previous value.");
        }
        else{
            if(percent != droneDb.getBatteryCapacity() && percent != 100)
                ExceptionsBuilder.launchException(result,droneClassName, "The drone is in Loaded state, the new battery value should be 100 percent.");

        }
        if(percent < 25 && !droneDb.getState().equals(State.LOADING.toString())){
            droneDb.setState(State.LOADING.toString());
            droneMedicationRepository.deleteAll(droneMedicationRepository.findByDrone(droneDb));
        }
        else if(droneDb.getState().equals(State.LOADING.toString()) && percent == 100){
            droneDb.setState(State.LOADED.toString());
        }
        droneDb.setBatteryCapacity(percent);
        return updateDrone(droneDb);
    }

//    @Override
//    public Drone updateDroneState(String serialNumber, String newState) {
//        Drone droneDb = droneRepository.findBySerialNumber(serialNumber);
//        if(null == droneDb)
//            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
//        droneDb.setState(newState);
//        return droneRepository.save(droneDb);
//    }


    @Override
    public List<Drone> getAvailableDrones() {
        List<Drone> droneList = droneRepository.findByState(State.IDLE.toString());
        return null != droneList? droneList : new ArrayList<>();
    }

    @Override
    public BatteryCapacityResponse getBatteryCapacityByDrone(String serialNumber) {
        Drone droneDb = droneRepository.findBySerialNumber(serialNumber);
        if(null == droneDb)
            ExceptionsBuilder.launchException(result,droneClassName, "The indicated drone isn´t exists.");
        return BatteryCapacityResponse.builder()
                .batteryCapacity(droneDb.getBatteryCapacity())
                .build();
    }
}
