package com.example.webapp.service;

import com.example.webapp.dto.StudentDTO;
import com.example.webapp.entity.Course;
import com.example.webapp.entity.Department;
import com.example.webapp.entity.Student;
import com.example.webapp.repository.CourseRepository;
import com.example.webapp.repository.DepartmentRepository;
import com.example.webapp.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Transactional
    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        student.setName(studentDTO.getName());
        student.setRoll(studentDTO.getRoll());
        student.setEmail(studentDTO.getEmail());
        student.setPhone(studentDTO.getPhone());
        
        if (studentDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(studentDTO.getDepartmentId())
                    .orElse(null);
            student.setDepartment(department);
        }
        
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    @Transactional
    public void enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            studentRepository.save(student);
        }
    }

    @Transactional
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    private StudentDTO convertToDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .roll(student.getRoll())
                .email(student.getEmail())
                .phone(student.getPhone())
                .departmentId(student.getDepartment() != null ? student.getDepartment().getId() : null)
                .departmentName(student.getDepartment() != null ? student.getDepartment().getName() : null)
                .courseIds(student.getCourses().stream()
                        .map(Course::getId)
                        .collect(Collectors.toList()))
                .courseNames(student.getCourses().stream()
                        .map(Course::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = Student.builder()
                .name(dto.getName())
                .roll(dto.getRoll())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
        
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElse(null);
            student.setDepartment(department);
        }
        
        return student;
    }
}
