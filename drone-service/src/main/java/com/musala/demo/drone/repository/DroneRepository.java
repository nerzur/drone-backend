package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    public Drone findBySerialNumber(String serialNumber);

    public List<Drone> findByState(String state);
}
