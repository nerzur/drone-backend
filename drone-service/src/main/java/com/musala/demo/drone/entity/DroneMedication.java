package com.musala.demo.drone.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;

@Entity (name = "tbl_drone_medication")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoConfiguration
public class DroneMedication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    DroneMedicationPK droneMedicationPK;

    @Positive
    Long quantity;

    @JoinColumn(name = "droneId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Drone drone;

    @JoinColumn(name = "medicationId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Medication medication;

}
