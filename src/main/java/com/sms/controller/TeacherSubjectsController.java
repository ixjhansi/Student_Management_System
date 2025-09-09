package com.sms.controller;

import com.sms.model.TeacherSubjects;
import com.sms.model.Teacher;
import com.sms.model.Subject;
import com.sms.model.ClassEntity;

import com.sms.request.TeacherSubjectsRequest;
import com.sms.response.TeacherSubjectsGetResponse;
import com.sms.response.TeacherSubjectsResponse;

import com.sms.repository.TeacherSubjectsRepository;
import com.sms.repository.TeacherRepository;
import com.sms.repository.SubjectRepository;
import com.sms.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teacher-subjects")
public class TeacherSubjectsController {

    @Autowired
    private TeacherSubjectsRepository teacherSubjectsRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassRepository classEntityRepository;

    // Create
    @PostMapping
    public TeacherSubjectsResponse createTeacherSubjects(@RequestBody TeacherSubjectsRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        ClassEntity classEntity = classEntityRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        TeacherSubjects teacherSubjects = new TeacherSubjects();
        teacherSubjects.setTeacher(teacher);
        teacherSubjects.setSubject(subject);
        teacherSubjects.setClassEntity(classEntity);
        teacherSubjects.setRole(request.getRole());

        TeacherSubjects saved = teacherSubjectsRepository.save(teacherSubjects);
        return mapToResponse(saved);
    }
 // Get All with Pagination
    @GetMapping
    public List<TeacherSubjectsGetResponse> getAllTeacherSubjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TeacherSubjects> teacherSubjectsPage = teacherSubjectsRepository.findAll(pageable);

        return teacherSubjectsPage.getContent().stream()
                .map(this::mapToGetResponse)
                .collect(Collectors.toList());
    }

    // Get by ID
    @GetMapping("/{id}")
    public TeacherSubjectsGetResponse getTeacherSubjectsById(@PathVariable Long id) {
        TeacherSubjects teacherSubjects = teacherSubjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeacherSubjects not found"));
        return mapToGetResponse(teacherSubjects);
    }

    
    // Update
    @PutMapping("/{id}")
    public TeacherSubjectsResponse updateTeacherSubjects(@PathVariable Long id,
                                                         @RequestBody TeacherSubjectsRequest request) {
        TeacherSubjects teacherSubjects = teacherSubjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TeacherSubjects not found"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        ClassEntity classEntity = classEntityRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        teacherSubjects.setTeacher(teacher);
        teacherSubjects.setSubject(subject);
        teacherSubjects.setClassEntity(classEntity);
        teacherSubjects.setRole(request.getRole());

        TeacherSubjects updated = teacherSubjectsRepository.save(teacherSubjects);
        return mapToResponse(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteTeacherSubjects(@PathVariable Long id) {
        teacherSubjectsRepository.deleteById(id);
        return "TeacherSubjects deleted successfully";
    }

    // Mapper
    private TeacherSubjectsResponse mapToResponse(TeacherSubjects teacherSubjects) {
        TeacherSubjectsResponse response = new TeacherSubjectsResponse();
        response.setId(teacherSubjects.getId());
        response.setTeacherId(teacherSubjects.getTeacher().getId());
        response.setSubjectId(teacherSubjects.getSubject().getId());
        response.setClassId(teacherSubjects.getClassEntity().getId());
        response.setRole(teacherSubjects.getRole());
        return response;
    }
 // Mapper for GET APIs
    private TeacherSubjectsGetResponse mapToGetResponse(TeacherSubjects teacherSubjects) {
        TeacherSubjectsGetResponse response = new TeacherSubjectsGetResponse();
        response.setId(teacherSubjects.getId());
        response.setTeacherName(teacherSubjects.getTeacher().getName());
        response.setSubjectName(teacherSubjects.getSubject().getName());
        response.setClassName(teacherSubjects.getClassEntity().getName());
        response.setRole(teacherSubjects.getRole());
        return response;
    }
}