package com.musala.demo.drone.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table (name = "tbl_drone")
@AutoConfiguration
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Drone {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The drone need a serialNumber")
    @Size (min = 1, max = 100, message = "The serialNumber must be between 1 and 100 characters")
    private String serialNumber;
    private Model model;
    @Max(value = 500, message = "The weight of drone must be 1 to 500gr")
    @Positive(message = "The weight of drone must be positive")
    private double weight;
    @Max(value = 100, message = "The maximum value for de battery 100%")
    @Positive(message = "The maximum value for de battery 100%")
    private int batteryCapacity;
    private State state;

    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medication_id")
    List<Medication> medicationList;
}
