package com.musala.demo.drone.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * This class contains the foreign key information for the DroneMedication relation.
 * @author Gabriel
 * @version 1.0
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoConfiguration
public class DroneMedicationPK  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long droneId;

    private Long medicationId;

//    private Timestamp timestamp;
}
