package com.sms.controller;

import com.sms.model.TeacherSubjects;
import com.sms.model.Teacher;
import com.sms.model.Subject;
import com.sms.model.ClassEntity;
import com.sms.request.TeacherSubjectsRequest;
import com.sms.response.TeacherSubjectsGetResponse;
import com.sms.response.TeacherSubjectsResponse;
import com.sms.service.TeacherSubjectsService;
import com.sms.repository.TeacherSubjectsRepository;
import com.sms.repository.TeacherRepository;
import com.sms.repository.SubjectRepository;
import com.sms.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private TeacherSubjectsService teacherSubjectsService;

    // Create
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> createTeacherSubjects(@Valid @RequestBody TeacherSubjectsRequest request) {
        try {
            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + request.getTeacherId()));
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + request.getSubjectId()));
            ClassEntity classEntity = classEntityRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found with ID: " + request.getClassId()));

            TeacherSubjects teacherSubjects = new TeacherSubjects();
            teacherSubjects.setTeacher(teacher);
            teacherSubjects.setSubject(subject);
            teacherSubjects.setClassEntity(classEntity);
            teacherSubjects.setRole(request.getRole());

            TeacherSubjects saved = teacherSubjectsRepository.save(teacherSubjects);
            return ResponseEntity.ok(mapToResponse(saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get All with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllTeacherSubjects(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<TeacherSubjects> teacherSubjectsPage = teacherSubjectsRepository.findAll(pageable);

            List<TeacherSubjectsGetResponse> result = teacherSubjectsPage.getContent()
                    .stream().map(this::mapToGetResponse).collect(Collectors.toList());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getTeacherSubjectsById(@PathVariable Long id) {
        try {
            Optional<TeacherSubjects> teacherSubjects = teacherSubjectsRepository.findById(id);
            if (teacherSubjects.isEmpty()) {
                return ResponseEntity.status(404).body("TeacherSubjects not found with ID: " + id);
            }
            return ResponseEntity.ok(mapToGetResponse(teacherSubjects.get()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Get all classes taught by a teacher
    @GetMapping("/teacher/{teacherId}/classes")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public ResponseEntity<?> getClassesByTeacher(@PathVariable Long teacherId) {
        try {
            if (!teacherRepository.existsById(teacherId)) {
                return ResponseEntity.status(404).body("Teacher not found with ID: " + teacherId);
            }
            List<String> classes = teacherSubjectsService.getClassesByTeacherId(teacherId);
            return ResponseEntity.ok(classes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Get all subjects taught by a teacher
    @GetMapping("/teacher/{teacherId}/subjects")
    @PreAuthorize("hasAnyAuthority('admin','teacher')")
    public ResponseEntity<?> getSubjectsByTeacher(@PathVariable Long teacherId) {
        try {
            if (!teacherRepository.existsById(teacherId)) {
                return ResponseEntity.status(404).body("Teacher not found with ID: " + teacherId);
            }
            List<String> subjects = teacherSubjectsService.getSubjectsByTeacherId(teacherId);
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> updateTeacherSubjects(@PathVariable Long id,
                                                   @Valid @RequestBody TeacherSubjectsRequest request) {
        try {
            TeacherSubjects teacherSubjects = teacherSubjectsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("TeacherSubjects not found with ID: " + id));

            Teacher teacher = teacherRepository.findById(request.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + request.getTeacherId()));
            Subject subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + request.getSubjectId()));
            ClassEntity classEntity = classEntityRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found with ID: " + request.getClassId()));

            teacherSubjects.setTeacher(teacher);
            teacherSubjects.setSubject(subject);
            teacherSubjects.setClassEntity(classEntity);
            teacherSubjects.setRole(request.getRole());

            TeacherSubjects updated = teacherSubjectsRepository.save(teacherSubjects);
            return ResponseEntity.ok(mapToResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteTeacherSubjects(@PathVariable Long id) {
        try {
            if (!teacherSubjectsRepository.existsById(id)) {
                return ResponseEntity.status(404).body("TeacherSubjects not found with ID: " + id);
            }
            teacherSubjectsRepository.deleteById(id);
            return ResponseEntity.ok("TeacherSubjects deleted successfully with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
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
