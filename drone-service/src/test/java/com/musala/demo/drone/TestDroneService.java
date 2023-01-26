package com.musala.demo.drone;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Model;
import com.musala.demo.drone.entity.State;
import com.musala.demo.drone.service.DroneService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestDroneService {

    @Autowired
    DroneService droneService;
    @BeforeAll
    public void setup(){
        Drone drone = Drone.builder()
                .serialNumber("1QAZxsw2")
                .batteryCapacity(80)
                .model(Model.CRUISERWEIGHT.toString())
                .state(State.DELIVERED.toString())
                .weight(400)
                .build();
        Assertions.assertThat(droneService.createDrone(drone)).isNotNull();
    }

    @Test
    public void testFindDrone(){
        Drone drone = droneService.getDroneBySerialNumber("1QAZxsw2");
        Assertions.assertThat(drone).isNotNull();
        Assertions.assertThat(droneService.getDroneById(drone.getId())).isNotNull();
    }

    @Test
    public void testEditDrone(){
        Drone drone = droneService.getDroneBySerialNumber("1QAZxsw2");
        drone.setState(State.DELIVERING.toString());
        drone.setWeight(100);
        drone.setBatteryCapacity(40);
        drone.setModel(Model.HEAVYWEIGHT.toString());
        Assertions.assertThat(droneService.updateDrone(drone)).isNotNull();
    }

    @Test
    public void testUpdateBattery(){
        Drone drone = droneService.updateBattery("1QAZxsw2", "45");
        Assertions.assertThat(droneService.updateDrone(drone)).isNotNull();
    }

    @Test
    public void testUpdateState(){
        Drone drone = droneService.updateDroneState("1QAZxsw2", State.LOADED.toString());
        Assertions.assertThat(droneService.updateDrone(drone)).isNotNull();
    }

    @AfterAll
    public void finish(){
        Assertions.assertThat(droneService.deleteDrone("1QAZxsw2")).isNotNull();
    }
}
