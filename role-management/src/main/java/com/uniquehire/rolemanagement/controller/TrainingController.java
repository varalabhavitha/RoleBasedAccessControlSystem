package com.uniquehire.rolemanagement.controller;
import com.uniquehire.rolemanagement.dto.request.TrainingRequestDto;
import com.uniquehire.rolemanagement.dto.response.TrainingResponseDto;
import com.uniquehire.rolemanagement.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public TrainingResponseDto createTraining(@Valid @RequestBody TrainingRequestDto dto) {
        return trainingService.createTraining(dto);
    }

    @GetMapping("/{id}")
    public TrainingResponseDto getTrainingById(@PathVariable Long id) {
        return trainingService.getTrainingById(id);
    }

    @GetMapping
    public List<TrainingResponseDto> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @PutMapping("/{id}")
    public TrainingResponseDto updateTraining(@PathVariable Long id,
                                              @Valid @RequestBody TrainingRequestDto dto) {
        return trainingService.updateTraining(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteTraining(@PathVariable Long id) {
        trainingService.deleteTraining(id);
        return "Training deleted successfully";
    }
}