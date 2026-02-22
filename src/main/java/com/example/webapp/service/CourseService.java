package com.example.webapp.service;

import com.example.webapp.dto.CourseDTO;
import com.example.webapp.entity.Course;
import com.example.webapp.entity.Department;
import com.example.webapp.repository.CourseRepository;
import com.example.webapp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<CourseDTO> getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        course.setCredits(courseDTO.getCredits());
        course.setDescription(courseDTO.getDescription());
        
        if (courseDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(courseDTO.getDepartmentId())
                    .orElse(null);
            course.setDepartment(department);
        }
        
        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .code(course.getCode())
                .credits(course.getCredits())
                .description(course.getDescription())
                .departmentId(course.getDepartment() != null ? course.getDepartment().getId() : null)
                .departmentName(course.getDepartment() != null ? course.getDepartment().getName() : null)
                .build();
    }

    private Course convertToEntity(CourseDTO dto) {
        Course course = Course.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .credits(dto.getCredits())
                .description(dto.getDescription())
                .build();
        
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElse(null);
            course.setDepartment(department);
        }
        
        return course;
    }
}
