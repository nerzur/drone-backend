package com.musala.demo.drone.ui;

import com.musala.demo.drone.entity.Medication;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MedicationQuantity {
    Medication medication;
    @Positive
    Long quantity;
}
