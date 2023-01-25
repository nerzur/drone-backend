package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    public Drone findBySerialNumber(String serialNumber);
}
