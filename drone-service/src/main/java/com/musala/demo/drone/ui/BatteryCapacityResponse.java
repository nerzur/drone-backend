package com.musala.demo.drone.ui;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BatteryCapacityResponse {

    @Positive
    @Max(value = 100)
    Integer batteryCapacity;
}
