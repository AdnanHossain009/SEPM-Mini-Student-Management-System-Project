package com.example.webapp.controller;

import com.example.webapp.dto.DepartmentDTO;
import com.example.webapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public String getDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments";
    }

    @GetMapping("/add")
    public String addDepartment(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        return "department-form";
    }

    @PostMapping("/store")
    public String storeDepartment(@ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.saveDepartment(departmentDTO);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable Long id, Model model) {
        DepartmentDTO department = departmentService.getDepartmentById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        model.addAttribute("department", department);
        return "department-form";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable Long id, @ModelAttribute("department") DepartmentDTO departmentDTO) {
        departmentService.updateDepartment(id, departmentDTO);
        return "redirect:/departments";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }
}
