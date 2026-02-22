package com.example.webapp.config;

import com.example.webapp.entity.*;
import com.example.webapp.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataInitializer {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Check if data already exists
            if (userRepository.count() > 0) {
                log.info("Data already initialized. Skipping...");
                return;
            }

            log.info("Initializing demo data...");

            // Create Departments
            Department cse = Department.builder()
                    .name("Computer Science & Engineering")
                    .code("CSE")
                    .description("Department of Computer Science and Engineering")
                    .build();
            departmentRepository.save(cse);

            Department eee = Department.builder()
                    .name("Electrical & Electronics Engineering")
                    .code("EEE")
                    .description("Department of Electrical and Electronics Engineering")
                    .build();
            departmentRepository.save(eee);

            Department me = Department.builder()
                    .name("Mechanical Engineering")
                    .code("ME")
                    .description("Department of Mechanical Engineering")
                    .build();
            departmentRepository.save(me);

            // Create Courses
            Course course1 = Course.builder()
                    .name("Data Structures and Algorithms")
                    .code("CSE201")
                    .credits(3)
                    .description("Introduction to data structures and algorithms")
                    .department(cse)
                    .build();
            courseRepository.save(course1);

            Course course2 = Course.builder()
                    .name("Database Management Systems")
                    .code("CSE301")
                    .credits(3)
                    .description("Fundamentals of database systems")
                    .department(cse)
                    .build();
            courseRepository.save(course2);

            Course course3 = Course.builder()
                    .name("Software Engineering")
                    .code("CSE401")
                    .credits(3)
                    .description("Software development principles and practices")
                    .department(cse)
                    .build();
            courseRepository.save(course3);

            Course course4 = Course.builder()
                    .name("Circuit Theory")
                    .code("EEE101")
                    .credits(4)
                    .description("Basic electrical circuit analysis")
                    .department(eee)
                    .build();
            courseRepository.save(course4);

            // Create Teacher User and Teacher
            Set<Role> teacherRoles = new HashSet<>();
            teacherRoles.add(Role.TEACHER);
            
            User teacherUser = User.builder()
                    .username("teacher")
                    .password(passwordEncoder.encode("teacher123"))
                    .email("teacher@university.edu")
                    .enabled(true)
                    .roles(teacherRoles)
                    .build();
            userRepository.save(teacherUser);

            Teacher teacher = Teacher.builder()
                    .name("Dr. John Smith")
                    .employeeId("EMP001")
                    .designation("Professor")
                    .phone("+1234567890")
                    .user(teacherUser)
                    .build();
            teacherRepository.save(teacher);

            // Link teacher back to user
            teacherUser.setTeacher(teacher);
            userRepository.save(teacherUser);

            // Create Student User and Student
            Set<Role> studentRoles = new HashSet<>();
            studentRoles.add(Role.STUDENT);
            
            User studentUser = User.builder()
                    .username("student")
                    .password(passwordEncoder.encode("student123"))
                    .email("student@university.edu")
                    .enabled(true)
                    .roles(studentRoles)
                    .build();
            userRepository.save(studentUser);

            Student student = Student.builder()
                    .name("Alice Johnson")
                    .roll("2021001")
                    .email("alice@university.edu")
                    .phone("+9876543210")
                    .department(cse)
                    .user(studentUser)
                    .build();
            student.getCourses().add(course1);
            student.getCourses().add(course2);
            studentRepository.save(student);

            // Link student back to user
            studentUser.setStudent(student);
            userRepository.save(studentUser);

            // Create more students without users
            Student student2 = Student.builder()
                    .name("Bob Williams")
                    .roll("2021002")
                    .email("bob@university.edu")
                    .phone("+1111111111")
                    .department(cse)
                    .build();
            student2.getCourses().add(course1);
            student2.getCourses().add(course3);
            studentRepository.save(student2);

            Student student3 = Student.builder()
                    .name("Carol Davis")
                    .roll("2021003")
                    .email("carol@university.edu")
                    .phone("+2222222222")
                    .department(eee)
                    .build();
            student3.getCourses().add(course4);
            studentRepository.save(student3);

            Student student4 = Student.builder()
                    .name("David Brown")
                    .roll("2021004")
                    .email("david@university.edu")
                    .phone("+3333333333")
                    .department(me)
                    .build();
            studentRepository.save(student4);

            log.info("Demo data initialized successfully!");
            log.info("Login credentials:");
            log.info("Teacher - Username: teacher, Password: teacher123");
            log.info("Student - Username: student, Password: student123");
        };
    }
}
