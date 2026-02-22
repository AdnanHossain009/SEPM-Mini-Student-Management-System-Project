package com.example.webapp.service;

import com.example.webapp.dto.DepartmentDTO;
import com.example.webapp.entity.Department;
import com.example.webapp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Transactional
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        department.setName(departmentDTO.getName());
        department.setCode(departmentDTO.getCode());
        department.setDescription(departmentDTO.getDescription());
        
        Department updatedDepartment = departmentRepository.save(department);
        return convertToDTO(updatedDepartment);
    }

    private DepartmentDTO convertToDTO(Department department) {
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .code(department.getCode())
                .description(department.getDescription())
                .build();
    }

    private Department convertToEntity(DepartmentDTO dto) {
        return Department.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .description(dto.getDescription())
                .build();
    }
}
