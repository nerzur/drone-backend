package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Drone;

import java.util.List;

public interface DroneService {

    public List<Drone> listAllDrone();
    public Drone getDroneById(Long id);
    public Drone getDroneBySerialNumber(String serialNumber);
    public Drone createDrone(Drone drone);
    public Drone deleteDrone(String serialNumber);
    public Drone updateDrone(Drone drone);
    public Drone updateBattery(String serialNumber, String newPercent);
    public Drone updateDroneState(String serialNumber, String newState);
}
