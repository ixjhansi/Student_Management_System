package com.sms.controller;

import com.sms.model.LaptopHistory;
import com.sms.model.Student;
import com.sms.model.Laptop;
import com.sms.request.LaptopHistoryRequest;
import com.sms.response.LaptopHistoryResponse;
import com.sms.repository.LaptopHistoryRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.LaptopRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/laptop-history")
public class LaptopHistoryController {

    @Autowired
    private LaptopHistoryRepository laptopHistoryRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LaptopRepository laptopRepository;

    // Create Laptop History
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> createHistory(@Valid @RequestBody LaptopHistoryRequest request) {
        try {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + request.getStudentId()));

            Laptop laptop = laptopRepository.findById(request.getLaptopId())
                    .orElseThrow(() -> new RuntimeException("Laptop not found with id " + request.getLaptopId()));

            LaptopHistory history = new LaptopHistory();
            history.setDateIssued(request.getDateIssued());
            history.setDateReturned(request.getDateReturned());
            history.setStudent(student);
            history.setLaptop(laptop);

            LaptopHistory saved = laptopHistoryRepository.save(history);
            return ResponseEntity.ok(mapToResponse(saved));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get All Laptop Histories with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllHistories(Pageable pageable) {
        try {
            Page<LaptopHistoryResponse> page = laptopHistoryRepository.findAll(pageable).map(this::mapToResponse);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get History by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getHistoryById(@PathVariable Long id) {
        try {
            LaptopHistory history = laptopHistoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Laptop history not found with id " + id));
            return ResponseEntity.ok(mapToResponse(history));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all laptop histories by studentId
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyAuthority('admin','student')")
    public ResponseEntity<?> getHistoriesByStudentId(@PathVariable Long studentId) {
        try {
            List<LaptopHistoryResponse> list = laptopHistoryRepository.findByStudent_Id(studentId)
                    .stream().map(this::mapToResponse).toList();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update Laptop History
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> updateHistory(@PathVariable Long id, @Valid @RequestBody LaptopHistoryRequest request) {
        try {
            LaptopHistory history = laptopHistoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Laptop history not found with id " + id));

            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with id " + request.getStudentId()));

            Laptop laptop = laptopRepository.findById(request.getLaptopId())
                    .orElseThrow(() -> new RuntimeException("Laptop not found with id " + request.getLaptopId()));

            history.setDateIssued(request.getDateIssued());
            history.setDateReturned(request.getDateReturned());
            history.setStudent(student);
            history.setLaptop(laptop);

            LaptopHistory updated = laptopHistoryRepository.save(history);
            return ResponseEntity.ok(mapToResponse(updated));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete Laptop History
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteHistory(@PathVariable Long id) {
        try {
            if (!laptopHistoryRepository.existsById(id)) {
                throw new RuntimeException("Laptop history not found with id " + id);
            }
            laptopHistoryRepository.deleteById(id);
            return ResponseEntity.ok("Laptop history deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Mapper: Entity -> Response DTO
    private LaptopHistoryResponse mapToResponse(LaptopHistory history) {
        LaptopHistoryResponse response = new LaptopHistoryResponse();
        response.setId(history.getId());
        response.setDateIssued(history.getDateIssued());
        response.setDateReturned(history.getDateReturned());
        response.setStudentId(history.getStudent().getId());
        response.setStudentName(history.getStudent().getName());
        response.setLaptopId(history.getLaptop().getId());
        response.setLaptopSerialNumber(history.getLaptop().getSerialNumber());
        return response;
    }
}
