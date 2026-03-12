package com.uniquehire.rolemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRequestDto {
    @NotBlank (message="Training name required")
    private String trainingName;

    @NotBlank(message="Batch number required")
    private String batchNo;

    @NotBlank(message="Trainer name required")
    private String trainerName;

    @NotNull(message="Start date required")
    private LocalDate startDate;

    @NotNull(message="End date required")
    private LocalDate endDate;

    private Integer noOfTrainees;
}
