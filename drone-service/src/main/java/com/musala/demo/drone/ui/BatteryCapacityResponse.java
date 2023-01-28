package com.musala.demo.drone.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BatteryCapacityResponse {

    Integer batteryCapacity;
}
