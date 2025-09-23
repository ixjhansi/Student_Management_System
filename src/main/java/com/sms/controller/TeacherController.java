package com.sms.controller;

import com.sms.model.Teacher;
import com.sms.request.TeacherRequest;
import com.sms.response.TeacherResponse;
import com.sms.repository.TeacherRepository;
import com.sms.repository.SubjectRepository;
import com.sms.repository.ClassRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classRepository;

    // Create Teacher
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Object createTeacher(@RequestBody TeacherRequest request) {
        try {
            validateRequest(request);

            Teacher teacher = new Teacher();
            teacher.setName(request.getName().trim());
            teacher.setPhone(request.getPhone().trim());
            teacher.setEmail(request.getEmail().trim());
            teacher.setQualification(request.getQualification().trim());
            teacher.setStatus(request.getStatus().trim());

            Teacher savedTeacher = teacherRepository.save(teacher);
            return mapToResponse(savedTeacher);
        } catch (Exception e) {
            return "Error creating Teacher: " + e.getMessage();
        }
    }

    // Get All Teachers with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Object getAllTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Teacher> teacherPage = teacherRepository.findAll(pageable);

            return teacherPage.getContent().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return "Error fetching teachers: " + e.getMessage();
        }
    }

    // Get Teacher by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public Object getTeacher(@PathVariable Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
            return mapToResponse(teacher);
        } catch (Exception e) {
            return "Error fetching teacher: " + e.getMessage();
        }
    }
    
 // ---------- FILTER ----------
    @GetMapping("/filter")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Object filterTeachers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Teacher> teacherPage = teacherRepository.filterTeachers(id, name, pageable);

            return teacherPage.map(this::mapToResponse);
        } catch (Exception e) {
            return "Error filtering teachers: " + e.getMessage();
        }
    }


    // Update Teacher
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Object updateTeacher(@PathVariable Long id, @RequestBody TeacherRequest request) {
        try {
            validateRequest(request);

            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

            teacher.setName(request.getName().trim());
            teacher.setPhone(request.getPhone().trim());
            teacher.setEmail(request.getEmail().trim());
            teacher.setQualification(request.getQualification().trim());
            teacher.setStatus(request.getStatus().trim());

            Teacher updated = teacherRepository.save(teacher);
            return mapToResponse(updated);
        } catch (Exception e) {
            return "Error updating teacher: " + e.getMessage();
        }
    }

    // Delete Teacher (Soft Delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Object deleteTeacher(@PathVariable Long id) {
        try {
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

            // Soft delete: mark teacher as inactive
            teacher.setStatus("inactive");
            teacherRepository.save(teacher);

            return "Teacher marked as inactive successfully";
        } catch (Exception e) {
            return "Error deleting teacher: " + e.getMessage();
        }
    }

    // Mapper
    private TeacherResponse mapToResponse(Teacher teacher) {
        TeacherResponse response = new TeacherResponse();
        response.setId(teacher.getId());
        response.setName(teacher.getName());
        response.setPhone(teacher.getPhone());
        response.setEmail(teacher.getEmail());
        response.setQualification(teacher.getQualification());
        response.setStatus(teacher.getStatus());
        return response;
    }

    // Validation method to block empty strings
    private void validateRequest(TeacherRequest request) {
        if (isEmpty(request.getName())) throw new RuntimeException("Name cannot be empty");
        if (isEmpty(request.getPhone())) throw new RuntimeException("Phone cannot be empty");
        if (isEmpty(request.getEmail())) throw new RuntimeException("Email cannot be empty");
        if (isEmpty(request.getQualification())) throw new RuntimeException("Qualification cannot be empty");
        if (isEmpty(request.getStatus())) throw new RuntimeException("Status cannot be empty");
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
