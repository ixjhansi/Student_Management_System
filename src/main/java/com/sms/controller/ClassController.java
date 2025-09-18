package com.sms.controller;

import com.sms.model.ClassEntity;
import com.sms.request.ClassRequest;
import com.sms.response.ClassResponse;
import com.sms.repository.ClassRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    @Autowired
    private ClassRepository classRepository;

    // Create
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ClassResponse createClass(@Valid @RequestBody ClassRequest request) {
        try {
            ClassEntity classEntity = new ClassEntity();
            classEntity.setName(request.getName().trim());
            classEntity.setStatus(request.getStatus().trim());

            ClassEntity saved = classRepository.save(classEntity);
            return mapToResponse(saved);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating class", e);
        }
    }

    // Get all with pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Page<ClassResponse> getAllClasses(Pageable pageable) {
        try {
            return classRepository.findAll(pageable).map(this::mapToResponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching classes", e);
        }
    }

    // Get by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin','student')")
    public ClassResponse getClassById(@PathVariable Long id) {
        try {
            ClassEntity classEntity = classRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
            return mapToResponse(classEntity);
        } catch (ResponseStatusException e) {
            throw e; // pass 404 as is
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching class", e);
        }
    }

    // Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ClassResponse updateClass(@PathVariable Long id, @Valid @RequestBody ClassRequest request) {
        try {
            ClassEntity classEntity = classRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

            classEntity.setName(request.getName().trim());
            classEntity.setStatus(request.getStatus().trim());

            ClassEntity updated = classRepository.save(classEntity);
            return mapToResponse(updated);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating class", e);
        }
    }

    // Delete (soft delete)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteClass(@PathVariable Long id) {
        try {
            ClassEntity classEntity = classRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

            classEntity.setStatus("Inactive"); // soft delete
            classRepository.save(classEntity);

            return "Class marked as inactive successfully";
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting class", e);
        }
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
