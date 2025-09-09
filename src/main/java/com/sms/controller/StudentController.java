package com.sms.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.sms.request.StudentRequest;
import com.sms.response.StudentDetailResponse;
import com.sms.response.StudentSummaryResponse;
import com.sms.model.ClassEntity;
import com.sms.model.Student;

import com.sms.repository.ClassRepository;
import com.sms.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRepository classRepository;

    // ---------- POST ----------
    @PostMapping
    public StudentDetailResponse createStudent(@RequestBody StudentRequest request) {
        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setStatus(request.getStatus());
        student.setClassEntity(classEntity);

        Student saved = studentRepository.save(student);
        return mapToDetailResponse(saved);
    }

    // ---------- GET (Paginated) ----------
    @GetMapping
    public Page<StudentSummaryResponse> getAllStudents(@PageableDefault(size = 5) Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(this::mapToSummaryResponse);
    }

    // ---------- GET by ID ----------
    @GetMapping("/{id}")
    public StudentSummaryResponse getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return mapToSummaryResponse(student);
    }

    // ---------- GET by Subject ----------
    @GetMapping("/subject/{subjectId}")
    public List<StudentSummaryResponse> getStudentsBySubject(@PathVariable Long subjectId) {
        return studentRepository.findStudentsBySubjectId(subjectId)
                .stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());
    }

    // ---------- PUT ----------
    @PutMapping("/{id}")
    public StudentDetailResponse updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        ClassEntity classEntity = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setStatus(request.getStatus());
        student.setClassEntity(classEntity);

        Student updated = studentRepository.save(student);
        return mapToDetailResponse(updated);
    }
    //---------delete-----------
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        // Soft delete
        student.setStatus("inactive");
        studentRepository.save(student);

        return "Student marked as inactive successfully";
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