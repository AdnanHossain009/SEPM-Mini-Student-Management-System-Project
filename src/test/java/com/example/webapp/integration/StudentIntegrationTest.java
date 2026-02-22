package com.example.webapp.integration;

import com.example.webapp.dto.StudentDTO;
import com.example.webapp.entity.Student;
import com.example.webapp.repository.StudentRepository;
import com.example.webapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StudentIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void testSaveAndRetrieveStudentFlow() {
        // 1. Create DTO
        StudentDTO dto = StudentDTO.builder()
                .name("Integration Test Student")
                .roll("INT-001")
                .build();

        // 2. Act: Save through Service
        StudentDTO savedStudent = studentService.saveStudent(dto);

        // 3. Assert: Verify saved object
        assertNotNull(savedStudent.getId());
        assertEquals("Integration Test Student", savedStudent.getName());

        // 4. Verify: Retrieve from DB using Service
        Optional<StudentDTO> fetched = studentService.getStudentById(savedStudent.getId());

        assertTrue(fetched.isPresent());
        assertEquals("INT-001", fetched.get().getRoll());
    }

    @Test
    void testGetAllStudentsFlow() {
        // Arrange: Directly save entities
        Student s1 = Student.builder()
                .name("User 1")
                .roll("R1")
                .build();
        studentRepository.save(s1);

        Student s2 = Student.builder()
                .name("User 2")
                .roll("R2")
                .build();
        studentRepository.save(s2);

        // Act: Use Service to fetch all
        List<StudentDTO> allStudents = studentService.getAllStudents();

        // Assert
        assertEquals(2, allStudents.size());
    }
}