package com.uniquehire.rolemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="trainings")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;

    private String trainingName;

    private String batchNo;

    private String trainerName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer noOfTrainees;
}
