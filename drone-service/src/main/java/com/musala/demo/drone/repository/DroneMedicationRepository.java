package com.musala.demo.drone.repository;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.DroneMedication;
import com.musala.demo.drone.entity.DroneMedicationPK;
import com.musala.demo.drone.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  * This interface allows access to the basic functions for accessing the data of the DroneMedication table.
 * @author Gabriel
 * @version 1.0
 */
public interface DroneMedicationRepository extends JpaRepository<DroneMedication, DroneMedicationPK> {

    public List<DroneMedication> findByDrone(Drone drone);

    public List<DroneMedication> findByMedication(Medication medication);
}
