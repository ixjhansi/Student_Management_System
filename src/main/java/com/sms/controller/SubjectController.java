package com.sms.controller;

import com.sms.model.Subject;
import com.sms.request.SubjectRequest;
import com.sms.response.SubjectDetailResponse;
import com.sms.response.SubjectSummaryResponse;
import com.sms.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // ---------- POST ----------
    @PostMapping
    public SubjectDetailResponse createSubject(@RequestBody SubjectRequest request) {
        Subject subject = new Subject();
        subject.setName(request.getName());
        subject.setStatus(request.getStatus());
        Subject saved = subjectRepository.save(subject);
        return mapToDetailResponse(saved);
    }

    // ---------- GET (Paginated) ----------
    @GetMapping
    public Page<SubjectSummaryResponse> getAllSubjects(@PageableDefault(size = 5) Pageable pageable) {
        return subjectRepository.findAll(pageable).map(this::mapToSummaryResponse);
    }

    // ---------- GET by ID ----------
    @GetMapping("/{id}")
    public SubjectSummaryResponse getSubject(@PathVariable Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
        return mapToSummaryResponse(subject);
    }

    // ---------- PUT ----------
    @PutMapping("/{id}")
    public SubjectDetailResponse updateSubject(@PathVariable Long id, @RequestBody SubjectRequest request) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
        subject.setName(request.getName());
        subject.setStatus(request.getStatus());
        Subject updated = subjectRepository.save(subject);
        return mapToDetailResponse(updated);
    }
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));

        // Soft delete
        subject.setStatus("inactive");
        subjectRepository.save(subject);

        return "Subject marked as inactive successfully";
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