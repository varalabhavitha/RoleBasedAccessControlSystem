package com.uniquehire.rolemanagement.controller;

import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.dto.response.MasterDataResponseDto;
import com.uniquehire.rolemanagement.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-data")
@RequiredArgsConstructor
public class MasterDataController {

    private final MasterDataService masterDataService;

    @GetMapping
    public ApiResponse<MasterDataResponseDto> getMasterData() {
        return masterDataService.getMasterData();
    }
}