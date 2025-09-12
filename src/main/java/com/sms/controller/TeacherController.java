package com.sms.controller;

import com.sms.model.Teacher;
import com.sms.model.TeacherSubjects;
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
    public TeacherResponse createTeacher(@RequestBody TeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setQualification(request.getQualification());
        teacher.setStatus(request.getStatus());

        Teacher savedTeacher = teacherRepository.save(teacher);
        return mapToResponse(savedTeacher);
    }

    // Get All Teachers with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public List<TeacherResponse> getAllTeachers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
    	size=5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);

        return teacherPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get Teacher by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public TeacherResponse getTeacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
        return mapToResponse(teacher);
    }

    // Update Teacher
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public TeacherResponse updateTeacher(@PathVariable Long id, @RequestBody TeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        teacher.setName(request.getName());
        teacher.setPhone(request.getPhone());
        teacher.setEmail(request.getEmail());
        teacher.setQualification(request.getQualification());
        teacher.setStatus(request.getStatus());

        Teacher updated = teacherRepository.save(teacher);
        return mapToResponse(updated);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteTeacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));

        // Soft delete: mark teacher as inactive
        teacher.setStatus("inactive");
        teacherRepository.save(teacher);

        return "Teacher marked as inactive successfully";
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
}