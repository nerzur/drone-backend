package com.musala.demo.drone.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MedicationsListByDroneResponse {
    private Drone drone;
    private List<MedicationQuantity> medicationQuantityList;
}
