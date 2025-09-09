package com.sms.controller;

import com.sms.model.ClassEntity;

import com.sms.request.ClassRequest;
import com.sms.response.ClassResponse;

import com.sms.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassRepository classRepository;

    // Create
    @PostMapping
    public ClassResponse createClass(@RequestBody ClassRequest request) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setName(request.getName());
        classEntity.setStatus(request.getStatus());
        ClassEntity saved = classRepository.save(classEntity);
        return mapToResponse(saved);
    }

    // ⬇️ Changed this to support pagination
    @GetMapping
    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable)
                .map(this::mapToResponse); // convert each entity → response
    }

    // Get by ID
    @GetMapping("/{id}")
    public ClassResponse getClassById(@PathVariable Long id) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));
        return mapToResponse(classEntity);
    }

    // Update
    @PutMapping("/{id}")
    public ClassResponse updateClass(@PathVariable Long id, @RequestBody ClassRequest request) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        classEntity.setName(request.getName());

        ClassEntity updated = classRepository.save(classEntity);
        return mapToResponse(updated);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deleteClass(@PathVariable Long id) {
        ClassEntity classEntity = classRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        classEntity.setStatus("Inactive"); // soft delete
        classRepository.save(classEntity);

        return "Class marked as inactive successfully";
    }


    // Mapper
    private ClassResponse mapToResponse(ClassEntity classEntity) {
        ClassResponse response = new ClassResponse();
        response.setId(classEntity.getId());
        response.setName(classEntity.getName());
        response.setStatus(classEntity.getStatus());
        return response;
    }
}