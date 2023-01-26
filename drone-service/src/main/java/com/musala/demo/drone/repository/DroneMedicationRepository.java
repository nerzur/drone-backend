package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.DroneMedication;
import com.musala.demo.drone.entity.DroneMedicationPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneMedicationRepository extends JpaRepository<DroneMedication, DroneMedicationPK> {


}
