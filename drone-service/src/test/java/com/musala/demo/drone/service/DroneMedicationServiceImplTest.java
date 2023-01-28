package com.musala.demo.drone.service;

import com.musala.demo.drone.entity.*;
import com.musala.demo.drone.repository.DroneMedicationRepository;
import com.musala.demo.drone.repository.DroneRepository;
import com.musala.demo.drone.repository.MedicationRepository;
import com.musala.demo.drone.ui.MedicationQuantityMinimal;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DroneMedicationServiceImplTest {

    @Mock
    private DroneMedicationRepository droneMedicationRepository;
    @Mock
    private DroneRepository droneRepository;
    @Mock
    private MedicationRepository medicationRepository;
    private DroneMedicationService droneMedicationService;

    private Drone drone;

    private Medication medication;
    private DroneMedication droneMedication;
    private List<DroneMedication> droneMedicationList;
    private List<MedicationQuantityMinimal> medicationQuantityMinimalList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        droneMedicationService = new DroneMedicationServiceImpl(droneMedicationRepository, droneRepository, medicationRepository);
        drone = Drone.builder()
                .id(1L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("1SE")
                .state(State.IDLE.toString())
                .weight(400)
                .build();
        medication = Medication.builder()
                .id(1L)
                .code("1SE")
                .name("Dipirona")
                .image("iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=")
                .weight(20L)
                .build();
        droneMedication = DroneMedication.builder()
                .droneMedicationPK(DroneMedicationPK.builder()
                        .medicationId(1L)
                        .droneId(1L)
                        .build())
                .drone(drone)
                .medication(medication)
                .build();
        droneMedicationList = new ArrayList<>();
        droneMedicationList.add(droneMedication);
        medicationQuantityMinimalList = new ArrayList<>();
        medicationQuantityMinimalList.add(MedicationQuantityMinimal.builder()
                .medicationCode("1SE")
                .quantity(10L)
                .build());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findMedicationsByDrone() {
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone);
        Mockito.when(droneMedicationRepository.findByDrone(drone)).thenReturn(droneMedicationList);
        Assertions.assertThat(droneMedicationService.findMedicationsByDrone("1SE")).isNotNull();
    }

    @Test
    void sendDroneToDelivery() {
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone);
        Mockito.when(medicationRepository.findByCode("1SE")).thenReturn(medication);
        Mockito.when(droneMedicationRepository.save(Mockito.any(DroneMedication.class))).thenReturn(droneMedication);
        Assertions.assertThat(droneMedicationService.sendDroneToDelivery("1SE", medicationQuantityMinimalList)).isNotNull();
    }

    @Test
    void deliverMedications() {
        Drone drone1 = Drone.builder()
                .id(1L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("1SE")
                .state(State.DELIVERING.toString())
                .weight(400)
                .build();
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone1);
        Mockito.when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone1);
        Assertions.assertThat(droneMedicationService.deliverMedications("1SE")).isNotNull();
    }

    @Test
    void returnDrone() {
        Drone drone1 = Drone.builder()
                .id(1L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("1SE")
                .state(State.DELIVERED.toString())
                .weight(400)
                .build();
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone1);
        Mockito.when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone1);
        Assertions.assertThat(droneMedicationService.returnDrone("1SE")).isNotNull();
    }

    @Test
    void reuseDrone() {
        Drone drone1 = Drone.builder()
                .id(1L)
                .batteryCapacity(80)
                .model(Model.Middleweight.toString())
                .serialNumber("1SE")
                .state(State.RETURNING.toString())
                .weight(400)
                .build();
        Mockito.when(droneRepository.findBySerialNumber("1SE")).thenReturn(drone1);
        Mockito.when(droneRepository.save(Mockito.any(Drone.class))).thenReturn(drone1);
        Assertions.assertThat(droneMedicationService.reuseDrone("1SE")).isNotNull();
    }
}