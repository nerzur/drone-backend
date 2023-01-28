package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Medication;
import com.musala.demo.drone.repository.DroneMedicationRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import com.musala.demo.drone.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final DroneMedicationRepository droneMedicationRepository;
    private final BindingResult result = new BindException(new Exception(), "");

    private final String medicationClassName = "Medication";

    @Override
    public List<Medication> listAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    @Override
    public Medication getMedicationByCode(String code) {
        return medicationRepository.findByCode(code);
    }

    @Override
    public Medication createMedication(Medication medication) {
        Medication medicationdb = medicationRepository.findByCode(medication.getCode());
        if (null != medicationdb)
            ExceptionsBuilder.launchException(result,medicationClassName, "This Medication is already exist.");
        return medicationRepository.save(medication);
    }

    @Override
    public Medication deleteMedication(String code) {
        Medication medicationDb = medicationRepository.findByCode(code);
        if (null == medicationDb)
            ExceptionsBuilder.launchException(result,medicationClassName, "The indicated medication isn´t exists.");
        if(isMedicationSendingToDeliver(medicationDb))
            ExceptionsBuilder.launchException(result,medicationClassName, "The indicated medication cannot be eliminated as there is at least one product in the delivery process of this type");
        medicationRepository.delete(medicationDb);
        return medicationRepository.findByCode(code) == null ? null : medicationDb;
    }

    @Override
    public Medication updateMedication(Medication medication) {
        Medication medicationDb = medicationRepository.findByCode(medication.getCode());
        if (null == medicationDb)
            ExceptionsBuilder.launchException(result,medicationClassName, "The indicated medication isn´t exists.");
        if(isMedicationSendingToDeliver(medicationDb))
            ExceptionsBuilder.launchException(result,medicationClassName, "The indicated medication cannot be edited as there is at least one product in the delivery process of this type");
        medicationDb.setWeight(medication.getWeight());
        medicationDb.setName(medication.getName());
        medicationDb.setImage(medication.getImage());
        return medicationRepository.save(medicationDb);
    }

    private boolean isMedicationSendingToDeliver(Medication medication){
        return !droneMedicationRepository.findByMedication(medication).isEmpty();
    }
}
