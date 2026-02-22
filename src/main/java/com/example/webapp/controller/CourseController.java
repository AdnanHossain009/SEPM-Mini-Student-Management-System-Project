package com.example.webapp.controller;

import com.example.webapp.dto.CourseDTO;
import com.example.webapp.service.CourseService;
import com.example.webapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class CourseController {

    private final CourseService courseService;
    private final DepartmentService departmentService;

    @GetMapping
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/add")
    public String addCourse(Model model) {
        model.addAttribute("course", new CourseDTO());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "course-form";
    }

    @PostMapping("/store")
    public String storeCourse(@ModelAttribute("course") CourseDTO courseDTO) {
        courseService.saveCourse(courseDTO);
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseDTO course = courseService.getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        model.addAttribute("course", course);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "course-form";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute("course") CourseDTO courseDTO) {
        courseService.updateCourse(id, courseDTO);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
