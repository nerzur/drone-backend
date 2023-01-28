package com.musala.demo.drone.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Builder
public class MedicationQuantityMinimal {

    @NotNull
    @Pattern(regexp = "[A-Z0-9_]+", message = "The name is invalid. The name must meet the following pattern \"[A-Z0-9_]+\".")
    @Size(min = 1, max = 100, message = "The code must be between 1 and 100 characters")
    String medicationCode;
    @Positive
    Long quantity;
}
