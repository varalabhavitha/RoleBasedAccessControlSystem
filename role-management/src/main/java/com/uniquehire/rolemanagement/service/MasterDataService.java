package com.uniquehire.rolemanagement.service;
import com.uniquehire.rolemanagement.dto.response.ApiResponse;
import com.uniquehire.rolemanagement.dto.response.MasterDataResponseDto;

public interface MasterDataService {
    ApiResponse<MasterDataResponseDto> getMasterData();
}