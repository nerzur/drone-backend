package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface allows access to the basic functions for accessing the data of the Drone table.
 * @author Gabriel
 * @version 1.0
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    public Drone findBySerialNumber(String serialNumber);

    public List<Drone> findByState(String state);
}
