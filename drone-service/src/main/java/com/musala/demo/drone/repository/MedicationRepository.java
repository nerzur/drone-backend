package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    public Medication findByCode(String code);
}
