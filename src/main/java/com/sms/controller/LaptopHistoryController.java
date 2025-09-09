package com.sms.controller;

import com.sms.model.LaptopHistory;
import com.sms.model.Student;
import com.sms.model.Laptop;

import com.sms.request.LaptopHistoryRequest;
import com.sms.response.LaptopHistoryResponse;

import com.sms.repository.LaptopHistoryRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.LaptopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public LaptopHistoryResponse createHistory(@RequestBody LaptopHistoryRequest request) {
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
        return mapToResponse(saved);
    }

    //Get All Laptop Histories with Pagination
    @GetMapping
    public Page<LaptopHistoryResponse> getAllHistories(Pageable pageable) {
        return laptopHistoryRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // Get History by ID
    @GetMapping("/{id}")
    public LaptopHistoryResponse getHistoryById(@PathVariable Long id) {
        LaptopHistory history = laptopHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop history not found with id " + id));
        return mapToResponse(history);
    }

    // Update Laptop History
    @PutMapping("/{id}")
    public LaptopHistoryResponse updateHistory(@PathVariable Long id, @RequestBody LaptopHistoryRequest request) {
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
        return mapToResponse(updated);
    }

    // Delete Laptop History
    @DeleteMapping("/{id}")
    public String deleteHistory(@PathVariable Long id) {
        laptopHistoryRepository.deleteById(id);
        return "Laptop history deleted successfully";
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