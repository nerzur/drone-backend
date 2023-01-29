package com.musala.demo.drone.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tbl_medication")
@AutoConfiguration
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9-_]+", message = "The name is invalid. The name must meet the following pattern \"[a-zA-Z0-9-_]+\".")
    @Size(min = 1, max = 255, message = "The name must be between 1 and 255 characters")
    private String name;
    @NotNull
    @Positive
    private Long weight;
    @NotNull
    @Pattern(regexp = "[A-Z0-9_]+", message = "The name is invalid. The name must meet the following pattern \"[A-Z0-9_]+\".")
    @Size(min = 1, max = 100, message = "The code must be between 1 and 100 characters")
    private String code;
    @NotNull
    @Size(max = 1500000)
    @Column(length = 1500000)
    private String image;

}
