package com.sms.controller;

import com.sms.model.Subject;
import com.sms.request.SubjectRequest;
import com.sms.response.SubjectDetailResponse;
import com.sms.response.SubjectSummaryResponse;
import com.sms.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/subjects")
@Validated
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // ---------- POST ----------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectRequest request) {
        try {
            Subject subject = new Subject();
            subject.setName(request.getName());
            subject.setStatus(request.getStatus());

            Subject saved = subjectRepository.save(subject);
            return ResponseEntity.ok(mapToDetailResponse(saved));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating subject: " + e.getMessage());
        }
    }

    // ---------- GET (Paginated) ----------
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllSubjects(@PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<SubjectSummaryResponse> page = subjectRepository.findAll(pageable)
                    .map(this::mapToSummaryResponse);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching subjects: " + e.getMessage());
        }
    }

    // ---------- GET by ID ----------
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getSubject(@PathVariable Long id) {
        try {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
            return ResponseEntity.ok(mapToSummaryResponse(subject));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------- PUT ----------
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        try {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));

            subject.setName(request.getName());
            subject.setStatus(request.getStatus());

            Subject updated = subjectRepository.save(subject);
            return ResponseEntity.ok(mapToDetailResponse(updated));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------- DELETE (Soft Delete) ----------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            Subject subject = subjectRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));

            subject.setStatus("inactive"); // Soft delete
            subjectRepository.save(subject);

            return ResponseEntity.ok("Subject marked as inactive successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ---------- Mappers ----------
    private SubjectSummaryResponse mapToSummaryResponse(Subject subject) {
        SubjectSummaryResponse response = new SubjectSummaryResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setStatus(subject.getStatus());
        return response;
    }

    private SubjectDetailResponse mapToDetailResponse(Subject subject) {
        SubjectDetailResponse response = new SubjectDetailResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setStatus(subject.getStatus());

        if (subject.getTeacherSubjects() != null) {
            response.setTeachers(subject.getTeacherSubjects().stream()
                    .map(ts -> ts.getTeacher().getName())
                    .collect(Collectors.toList()));
        }

        if (subject.getClassSubjects() != null) {
            response.setClasses(subject.getClassSubjects().stream()
                    .map(cs -> cs.getClassEntity().getName())
                    .collect(Collectors.toList()));
        }

        if (subject.getSubjectMarks() != null) {
            response.setStudents(subject.getSubjectMarks().stream()
                    .map(sm -> sm.getStudent().getName())
                    .collect(Collectors.toList()));
        }

        return response;
    }
}
