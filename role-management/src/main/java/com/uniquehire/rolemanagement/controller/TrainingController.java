package com.uniquehire.rolemanagement.controller;


import com.uniquehire.rolemanagement.dto.request.TrainingRequestDto;
import com.uniquehire.rolemanagement.dto.response.TrainingResponseDto;
import com.uniquehire.rolemanagement.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    @Autowired
    private TrainingService service;

    @PostMapping
    public TrainingResponseDto createTraining(@Valid @RequestBody TrainingRequestDto dto){

        return service.createTraining(dto);
    }

    @GetMapping
    public Page<TrainingResponseDto> getAllTrainings(

            @RequestParam int page,
            @RequestParam int size){

        return service.getAllTrainings(page,size);
    }

    @GetMapping("/{id}")
    public TrainingResponseDto getTrainingById(@PathVariable Long id){

        return service.getTrainingById(id);
    }

    @PutMapping("/{id}")
    public TrainingResponseDto updateTraining(@PathVariable Long id,
                                              @Valid @RequestBody TrainingRequestDto dto){

        return service.updateTraining(id,dto);
    }

    @DeleteMapping("/{id}")
    public String deleteTraining(@PathVariable Long id){

        service.deleteTraining(id);

        return "Training deleted successfully";
    }
}
