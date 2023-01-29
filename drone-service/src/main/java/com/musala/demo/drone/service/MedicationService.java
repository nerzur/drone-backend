package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Medication;

import java.util.List;

/**
 * @author Gabriel
 * @version 1.0
 */
public interface MedicationService {

    public List<Medication> listAllMedications();
    public Medication getMedicationById(Long id);
    public Medication getMedicationByCode(String code);
    public Medication createMedication(Medication medication);
    public Medication deleteMedication(String code);
    public Medication updateMedication(Medication medication);
}
