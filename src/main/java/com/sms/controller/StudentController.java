package com.sms.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sms.request.StudentRequest;
import com.sms.response.StudentDetailResponse;
import com.sms.response.StudentSummaryResponse;
import com.sms.model.ClassEntity;
import com.sms.model.Student;
import com.sms.repository.ClassRepository;
import com.sms.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    // ---------- POST ----------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequest request) {
        try {
            ClassEntity classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found with id " + request.getClassId()));

            Student student = new Student();
            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student.setStatus(request.getStatus());
            student.setClassEntity(classEntity);

            Student saved = studentRepository.save(student);
            return ResponseEntity.ok(mapToDetailResponse(saved));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating student: " + e.getMessage());
        }
    }

    // ---------- GET (Paginated) ----------
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllStudents(@PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<StudentSummaryResponse> page = studentRepository.findAll(pageable).map(this::mapToSummaryResponse);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching students: " + e.getMessage());
        }
    }

    // ---------- GET by ID ----------
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','student')")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
            return ResponseEntity.ok(mapToSummaryResponse(student));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------- GET by Subject ----------
    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getStudentsBySubject(@PathVariable Long subjectId) {
        try {
            List<StudentSummaryResponse> students = studentRepository.findStudentsBySubjectId(subjectId)
                    .stream()
                    .map(this::mapToSummaryResponse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching students: " + e.getMessage());
        }
    }

    // ---------- PUT ----------
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

            ClassEntity classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new RuntimeException("Class not found with id " + request.getClassId()));

            student.setName(request.getName());
            student.setEmail(request.getEmail());
            student.setStatus(request.getStatus());
            student.setClassEntity(classEntity);

            Student updated = studentRepository.save(student);
            return ResponseEntity.ok(mapToDetailResponse(updated));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // --------- DELETE (Soft delete) -----------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

            student.setStatus("inactive"); // Soft delete
            studentRepository.save(student);

            return ResponseEntity.ok("Student marked as inactive successfully");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------- Mappers ----------
    private StudentSummaryResponse mapToSummaryResponse(Student student) {
        StudentSummaryResponse response = new StudentSummaryResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setStatus(student.getStatus());
        if (student.getClassEntity() != null) {
            response.setClassName(student.getClassEntity().getName());
        }
        return response;
    }

    private StudentDetailResponse mapToDetailResponse(Student student) {
        StudentDetailResponse response = new StudentDetailResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setStatus(student.getStatus());

        if (student.getClassEntity() != null) {
            response.setClassId(student.getClassEntity().getId());
            response.setClassName(student.getClassEntity().getName());
        }

        if (student.getLaptopHistories() != null) {
            response.setLaptopHistories(student.getLaptopHistories().stream().map(lh -> {
                StudentDetailResponse.LaptopHistorySummary summary = new StudentDetailResponse.LaptopHistorySummary();
                summary.setId(lh.getId());
                if (lh.getLaptop() != null) {
                    summary.setLaptopModel(lh.getLaptop().getModel());
                    summary.setLaptopSerial(lh.getLaptop().getSerialNumber());
                }
                summary.setDateIssued(lh.getDateIssued() != null ? lh.getDateIssued().toString() : null);
                summary.setDateReturned(lh.getDateReturned() != null ? lh.getDateReturned().toString() : null);
                return summary;
            }).collect(Collectors.toList()));
        }

        return response;
    }
}
