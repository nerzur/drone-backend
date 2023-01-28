package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.Drone;
import com.musala.demo.drone.entity.Model;
import com.musala.demo.drone.entity.State;
import com.musala.demo.drone.repository.DroneMedicationRepository;
import com.musala.demo.drone.repository.DroneRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@SpringBootTest
class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationRepository medicationRepository;
    @Mock
    private DroneMedicationRepository droneMedicationRepository;

    private DroneService droneService;

    private Drone drone;

    private Drone newDrone;

    private List<Drone> droneList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        droneService = new DroneServiceImpl(droneRepository, medicationRepository, droneMedicationRepository);
        drone = Drone.builder()
                .id(1L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("1SE")
                .state(State.IDLE.toString())
                .weight(400)
                .build();
        newDrone = Drone.builder()
                .id(6L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("6SE")
                .state(State.IDLE.toString())
                .weight(400)
                .build();
        droneList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            droneList.add(Drone.builder()
                    .id((long) i + 1)
                    .batteryCapacity(80)
                    .model(Model.Middleweight.toString())
                    .serialNumber((i + 1) + "SE")
                    .state(State.IDLE.toString())
                    .weight(400)
                    .build());
        }
        Mockito.when(droneRepository.findAll()).thenReturn(droneList);
        Mockito.when(droneRepository.findById(1L)).thenReturn(Optional.of(droneList.get(0)));
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(droneList.get(0));
        Mockito.when(droneRepository.save(newDrone)).thenReturn(newDrone);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listAllDrone() {
        Assertions.assertThat(droneService.listAllDrone()).isNotEmpty();
    }

    @Test
    void getDroneById() {
        Assertions.assertThat(droneService.getDroneById(1L)).isNotNull();
    }

    @Test
    void getDroneBySerialNumber() {
        Assertions.assertThat(droneService.getDroneBySerialNumber("1SE")).isNotNull();

    }

    @Test
    void createDrone() {
        Assertions.assertThat(droneService.createDrone(newDrone)).isNotNull();
    }

    @Test
    void deleteDrone() {
        Assertions.assertThat(droneService.deleteDrone("1SE")).isNotNull();
    }

    @Test
    void updateDrone() {
        Drone drone1 = Drone.builder()
                .id(1L)
                .batteryCapacity(40)
                .model(Model.HEAVYWEIGHT.toString())
                .serialNumber("1SE")
                .state(State.IDLE.toString())
                .weight(200)
                .build();
        Mockito.when(droneRepository.save(drone1)).thenReturn(drone1);
        Assertions.assertThat(droneService.updateDrone(drone1)).isNotNull();
    }

    @Test
    void updateBattery() {
        Drone drone1 = Drone.builder().id(1L).batteryCapacity(80).model(Model.Middleweight.toString()).serialNumber("1SE").state(State.IDLE.toString()).weight(400).build();
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone1);
        Drone drone2 = Drone.builder().id(1L).batteryCapacity(65).model(Model.Middleweight.toString()).serialNumber("1SE").state(State.IDLE.toString()).weight(400).build();
        Mockito.when(droneRepository.save(drone1)).thenReturn(drone2);
        Assertions.assertThat(droneService.updateBattery("1SE", "65")).isNotNull();
        try {
            droneService.updateBattery("1SE", "85");
            assert false;
        } catch (Exception ignored) {}
    }

    @Test
    void getAvailableDrones() {
        Mockito.when(droneRepository.findByState(State.IDLE.toString())).thenReturn(droneList);
        Assertions.assertThat(droneService.getAvailableDrones()).isNotEmpty();
    }

    @Test
    void getBatteryCapacityByDrone() {
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone);
        Assertions.assertThat(droneService.getBatteryCapacityByDrone("1SE")).isNotNull();

    }
}