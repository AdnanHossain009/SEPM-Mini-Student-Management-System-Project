package com.example.webapp.service;

import com.example.webapp.dto.TeacherDTO;
import com.example.webapp.entity.Teacher;
import com.example.webapp.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Transactional(readOnly = true)
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<TeacherDTO> getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = convertToEntity(teacherDTO);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return convertToDTO(savedTeacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Transactional
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        teacher.setName(teacherDTO.getName());
        teacher.setEmployeeId(teacherDTO.getEmployeeId());
        teacher.setDesignation(teacherDTO.getDesignation());
        teacher.setPhone(teacherDTO.getPhone());
        
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return convertToDTO(updatedTeacher);
    }

    private TeacherDTO convertToDTO(Teacher teacher) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .employeeId(teacher.getEmployeeId())
                .designation(teacher.getDesignation())
                .phone(teacher.getPhone())
                .build();
    }

    private Teacher convertToEntity(TeacherDTO dto) {
        return Teacher.builder()
                .name(dto.getName())
                .employeeId(dto.getEmployeeId())
                .designation(dto.getDesignation())
                .phone(dto.getPhone())
                .build();
    }
}
