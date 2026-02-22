package com.example.webapp.repository;

import com.example.webapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testFindById(){
        Student student = Student.builder()
                .name("ABC")
                .roll("1")
                .build();

        this.studentRepository.save(student);

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertTrue(studentOptional.isPresent());
        assertEquals(student.getId(), studentOptional.get().getId());
    }

    @Test
    void testFindAll() {
        Student student1 = Student.builder()
                .name("ABC")
                .roll("1")
                .build();
        this.studentRepository.save(student1);

        Student student2 = Student.builder()
                .name("DEF")
                .roll("2")
                .build();
        this.studentRepository.save(student2);

        List<Student> students = this.studentRepository.findAll();
        assertFalse(students.isEmpty());
    }

}