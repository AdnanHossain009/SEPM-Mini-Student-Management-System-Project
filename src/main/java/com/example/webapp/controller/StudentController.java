package com.example.webapp.controller;

import com.example.webapp.dto.StudentDTO;
import com.example.webapp.service.CourseService;
import com.example.webapp.service.DepartmentService;
import com.example.webapp.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final CourseService courseService;

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('TEACHER')")
    public String addStudent(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "student-form";
    }

    @PostMapping("/store")
    @PreAuthorize("hasRole('TEACHER')")
    public String storeStudent(@ModelAttribute("student") StudentDTO studentDTO) {
        studentService.saveStudent(studentDTO);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String editStudent(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "student-form";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") StudentDTO studentDTO) {
        studentService.updateStudent(id, studentDTO);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/{id}/courses")
    public String viewStudentCourses(@PathVariable Long id, Model model) {
        StudentDTO student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("allCourses", courseService.getAllCourses());
        return "student-courses";
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public String enrollInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.enrollStudentInCourse(studentId, courseId);
        return "redirect:/students/" + studentId + "/courses";
    }

    @GetMapping("/{studentId}/remove/{courseId}")
    @PreAuthorize("hasRole('TEACHER')")
    public String removeFromCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.removeStudentFromCourse(studentId, courseId);
        return "redirect:/students/" + studentId + "/courses";
    }
}
