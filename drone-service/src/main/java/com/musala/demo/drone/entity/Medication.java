package com.musala.demo.drone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import javax.validation.constraints.*;

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
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9-_]+", message = "The name is invalid. The name must meet the following pattern \"[a-zA-Z0-9-_]+\".")
    @Size (min = 1, max = 255, message = "The name must be between 1 and 255 characters")
    private String name;
    @NotNull
    @Positive
    private Long weight;
    @NotNull
    @Pattern(regexp = "[A-Z0-9_]+", message = "The name is invalid. The name must meet the following pattern \"[A-Z0-9_]+\".")
    @Size (min = 1, max = 100, message = "The code must be between 1 and 100 characters")
    private String code;
    @NotNull
    @Size (max = 2048)
    @Column(length = 2048)
    private String image;

}
