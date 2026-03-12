package com.uniquehire.rolemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponseDto {
    private Long trainingId;

    private String trainingName;

    private String batchNo;

    private String trainerName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer noOfTrainees;
}
