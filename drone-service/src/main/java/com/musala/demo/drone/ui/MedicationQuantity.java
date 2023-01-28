package com.musala.demo.drone.ui;

import com.musala.demo.drone.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Builder
public class MedicationQuantity {
    Medication medication;
    @Positive
    Long quantity;
}
