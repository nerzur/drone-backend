package com.musala.demo.drone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import javax.validation.constraints.Pattern;

@Entity
@Table(name = "tbl_medication")
@AutoConfiguration
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "[a-zA-Z0-9-_]+", message = "The name is invalid. The name must meet the following pattern \"[a-zA-Z0-9-_]+\".")
    private String name;
    private long weight;
    @Pattern(regexp = "[A-Z0-9_]+", message = "The name is invalid. The name must meet the following pattern \"[A-Z0-9_]+\".")
    private String code;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY ,optional = false)
    @JoinColumn(name = "drone_id", nullable = false)
    Drone drone;

}
