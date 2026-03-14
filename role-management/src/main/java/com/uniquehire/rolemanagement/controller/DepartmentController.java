package com.uniquehire.rolemanagement.controller;
import com.uniquehire.rolemanagement.dto.request.DepartmentRequest;
import com.uniquehire.rolemanagement.dto.response.DepartmentResponseDto;
import com.uniquehire.rolemanagement.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * CREATE DEPARTMENT
     */
    @PostMapping
    public DepartmentResponseDto createDepartment(
            @Valid @RequestBody DepartmentRequest dto) {

        return departmentService.createDepartment(dto);
    }

    /**
     * GET DEPARTMENT BY ID
     */
    @GetMapping("/{id}")
    public DepartmentResponseDto getDepartmentById(
            @PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    /**
     * GET ALL DEPARTMENTS
     */
    @GetMapping
    public List<DepartmentResponseDto> getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    /**
     * UPDATE DEPARTMENT
     */
    @PutMapping("/{id}")
    public DepartmentResponseDto updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest dto) {

        return departmentService.updateDepartment(id, dto);
    }

    /**
     * DELETE DEPARTMENT
     */
    @DeleteMapping("/{id}")
    public String deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);
        return "Department deleted successfully";
    }
}