package com.example.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private Long id;
    private String name;
    private String roll;
    private String email;
    private String phone;
    private Long departmentId;
    private String departmentName;
    private List<Long> courseIds = new ArrayList<>();
    private List<String> courseNames = new ArrayList<>();
}
