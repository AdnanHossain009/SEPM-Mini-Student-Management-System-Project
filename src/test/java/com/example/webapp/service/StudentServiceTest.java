package com.example.webapp.service;

import com.example.webapp.dto.StudentDTO;
import com.example.webapp.entity.Student;
import com.example.webapp.repository.CourseRepository;
import com.example.webapp.repository.DepartmentRepository;
import com.example.webapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void getAllStudents_shouldReturnStudents() {
        Student s1 = new Student();
        s1.setId(1L);
        s1.setName("Rahim");
        s1.setRoll("CSE-01");

        when(studentRepository.findAll()).thenReturn(List.of(s1));

        List<StudentDTO> students = studentService.getAllStudents();

        assertEquals(1, students.size());
        assertEquals("Rahim", students.get(0).getName());
        verify(studentRepository).findAll();
    }

    @Test
    void getStudentById_shouldReturnStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Test Student");
        student.setRoll("CSE-01");

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        Optional<StudentDTO> result = studentService.getStudentById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Test Student", result.get().getName());
    }

    @Test
    void saveStudent_shouldMapAndSave() {
        StudentDTO dto = StudentDTO.builder()
                .name("Karim")
                .roll("CSE-01")
                .build();

        Student savedStudent = Student.builder()
                .id(1L)
                .name("Karim")
                .roll("CSE-01")
                .build();
        // Courses list is initialized in the builder

        when(studentRepository.save(any(Student.class)))
                .thenReturn(savedStudent);

        StudentDTO saved = studentService.saveStudent(dto);

        assertEquals("Karim", saved.getName());
        verify(studentRepository).save(any(Student.class));
    }
}
