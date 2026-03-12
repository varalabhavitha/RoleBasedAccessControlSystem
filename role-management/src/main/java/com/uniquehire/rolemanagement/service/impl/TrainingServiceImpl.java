package com.uniquehire.rolemanagement.service.impl;


import com.uniquehire.rolemanagement.dto.request.TrainingRequestDto;
import com.uniquehire.rolemanagement.dto.response.TrainingResponseDto;
import com.uniquehire.rolemanagement.entity.Training;
import com.uniquehire.rolemanagement.exception.ResourceNotFoundException;
import com.uniquehire.rolemanagement.repository.TrainingRepository;
import com.uniquehire.rolemanagement.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger =
            LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingRepository repository;

    @Override
    public TrainingResponseDto createTraining(TrainingRequestDto dto){

        logger.info("Creating new training");

        Training training=new Training();

        training.setTrainingName(dto.getTrainingName());
        training.setBatchNo(dto.getBatchNo());
        training.setTrainerName(dto.getTrainerName());
        training.setStartDate(dto.getStartDate());
        training.setEndDate(dto.getEndDate());
        training.setNoOfTrainees(dto.getNoOfTrainees());

        Training saved=repository.save(training);

        logger.info("Training created with ID {}",saved.getTrainingId());

        return mapToResponse(saved);
    }

    @Override
    public Page<TrainingResponseDto> getAllTrainings(int page,int size){

        logger.info("Fetching trainings page {}",page);

        Page<Training> trainings=repository.findAll(PageRequest.of(page,size));

        List<TrainingResponseDto> list=new ArrayList<>();

        for(Training t:trainings.getContent()){

            list.add(mapToResponse(t));

        }

        return new PageImpl<>(list,trainings.getPageable(),trainings.getTotalElements());

    }

    @Override
    public TrainingResponseDto getTrainingById(Long id){

        logger.info("Fetching training {}",id);

        Training training=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Training not found"));

        return mapToResponse(training);
    }

    @Override
    public TrainingResponseDto updateTraining(Long id,TrainingRequestDto dto){

        Training training=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Training not found"));

        training.setTrainingName(dto.getTrainingName());
        training.setBatchNo(dto.getBatchNo());
        training.setTrainerName(dto.getTrainerName());
        training.setStartDate(dto.getStartDate());
        training.setEndDate(dto.getEndDate());
        training.setNoOfTrainees(dto.getNoOfTrainees());

        Training updated=repository.save(training);

        logger.info("Training updated {}",id);

        return mapToResponse(updated);
    }

    @Override
    public void deleteTraining(Long id){

        Training training=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Training not found"));

        repository.delete(training);

        logger.info("Training deleted {}",id);

    }

    private TrainingResponseDto mapToResponse(Training training){

        TrainingResponseDto dto=new TrainingResponseDto();

        dto.setTrainingId(training.getTrainingId());
        dto.setTrainingName(training.getTrainingName());
        dto.setBatchNo(training.getBatchNo());
        dto.setTrainerName(training.getTrainerName());
        dto.setStartDate(training.getStartDate());
        dto.setEndDate(training.getEndDate());
        dto.setNoOfTrainees(training.getNoOfTrainees());

        return dto;

    }
}
