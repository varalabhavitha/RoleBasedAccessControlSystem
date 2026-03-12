package com.uniquehire.rolemanagement.service;


import com.uniquehire.rolemanagement.dto.request.TrainingRequestDto;
import com.uniquehire.rolemanagement.dto.response.TrainingResponseDto;
import org.springframework.data.domain.Page;

public interface TrainingService {
    TrainingResponseDto createTraining(TrainingRequestDto dto);

    Page<TrainingResponseDto> getAllTrainings(int page, int size);

    TrainingResponseDto getTrainingById(Long id);

    TrainingResponseDto updateTraining(Long id,TrainingRequestDto dto);

    void deleteTraining(Long id);
}
