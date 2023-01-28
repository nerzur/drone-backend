package com.musala.demo.drone;

import com.musala.demo.drone.entity.DroneMedication;
import com.musala.demo.drone.service.DroneMedicationService;
import com.musala.demo.drone.ui.MedicationsListByDroneResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDroneMedicationService {
    @Autowired
    DroneMedicationService droneMedicationService;


}
