package com.example.webapp.controller;

import com.example.webapp.dto.TeacherDTO;
import com.example.webapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public String getTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers";
    }

    @GetMapping("/add")
    public String addTeacher(Model model) {
        model.addAttribute("teacher", new TeacherDTO());
        return "teacher-form";
    }

    @PostMapping("/store")
    public String storeTeacher(@ModelAttribute("teacher") TeacherDTO teacherDTO) {
        teacherService.saveTeacher(teacherDTO);
        return "redirect:/teachers";
    }

    @GetMapping("/edit/{id}")
    public String editTeacher(@PathVariable Long id, Model model) {
        TeacherDTO teacher = teacherService.getTeacherById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        model.addAttribute("teacher", teacher);
        return "teacher-form";
    }

    @PostMapping("/update/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute("teacher") TeacherDTO teacherDTO) {
        teacherService.updateTeacher(id, teacherDTO);
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
