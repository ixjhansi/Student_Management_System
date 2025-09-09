package com.sms.controller;

import com.sms.model.SubjectMarks;
import com.sms.model.Student;
import com.sms.model.Subject;

import com.sms.request.SubjectMarksRequest;
import com.sms.response.SubjectMarksGetResponse;
import com.sms.response.SubjectMarksResponse;

import com.sms.repository.SubjectMarksRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/subject-marks")
public class SubjectMarksController {

    @Autowired
    private SubjectMarksRepository subjectMarksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    // Create
    @PostMapping
    public SubjectMarksResponse createSubjectMarks(@RequestBody SubjectMarksRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        SubjectMarks subjectMarks = new SubjectMarks();
        subjectMarks.setStudent(student);
        subjectMarks.setSubject(subject);
        subjectMarks.setMarks(request.getMarks());

        SubjectMarks saved = subjectMarksRepository.save(subjectMarks);
        return mapToDetailedResponse(saved);
    }

    // Get All with Pagination
    @GetMapping
    public List<SubjectMarksGetResponse> getAllSubjectMarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SubjectMarks> subjectMarksPage = subjectMarksRepository.findAll(pageable);
        return subjectMarksPage.stream()
                               .map(this::mapToSimpleResponse)
                               .collect(Collectors.toList());
    }

    // Get by ID
    @GetMapping("/{id}")
    public SubjectMarksGetResponse getSubjectMarksById(@PathVariable Long id) {
        SubjectMarks subjectMarks = subjectMarksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubjectMarks not found"));
        return mapToSimpleResponse(subjectMarks);
    }

    // Update
    @PutMapping("/{id}")
    public SubjectMarksResponse updateSubjectMarks(@PathVariable Long id, @RequestBody SubjectMarksRequest request) {
        SubjectMarks subjectMarks = subjectMarksRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubjectMarks not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        subjectMarks.setStudent(student);
        subjectMarks.setSubject(subject);
        subjectMarks.setMarks(request.getMarks());

        SubjectMarks updated = subjectMarksRepository.save(subjectMarks);
        return mapToDetailedResponse(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteSubjectMarks(@PathVariable Long id) {
        subjectMarksRepository.deleteById(id);
        return "SubjectMarks deleted successfully";
    }

    // Mappers
    private SubjectMarksResponse mapToDetailedResponse(SubjectMarks subjectMarks) {
        SubjectMarksResponse response = new SubjectMarksResponse();
        response.setId(subjectMarks.getId());
        response.setStudentId(subjectMarks.getStudent().getId());
        response.setSubjectId(subjectMarks.getSubject().getId());
        response.setMarks(subjectMarks.getMarks());
        return response;
    }

    private SubjectMarksGetResponse mapToSimpleResponse(SubjectMarks subjectMarks) {
        SubjectMarksGetResponse response = new SubjectMarksGetResponse();
        response.setId(subjectMarks.getId());
        response.setStudentId(subjectMarks.getStudent().getId());
        response.setStudentName(subjectMarks.getStudent().getName());
        response.setSubjectName(subjectMarks.getSubject().getName());
        response.setMarks(subjectMarks.getMarks());
        return response;
    }
}