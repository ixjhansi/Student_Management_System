package com.sms.controller;

import com.sms.model.Laptop;
import com.sms.request.LaptopRequest;
import com.sms.response.LaptopResponse;
import com.sms.repository.LaptopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    @Autowired
    private LaptopRepository laptopRepository;

    // Create Laptop
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public LaptopResponse createLaptop(@Valid @RequestBody LaptopRequest request) {
        try {
            Laptop laptop = new Laptop();
            laptop.setSerialNumber(request.getSerialNumber());
            laptop.setModel(request.getModel());
            laptop.setStatus(request.getStatus());

            Laptop saved = laptopRepository.save(laptop);
            return mapToResponse(saved);
        } catch (Exception ex) {
            throw new RuntimeException("Error creating laptop", ex);
        }
    }

    // Get All Laptops (Paginated)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Page<LaptopResponse> getAllLaptops(Pageable pageable) {
        try {
            return laptopRepository.findAll(pageable).map(this::mapToResponse);
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching laptops", ex);
        }
    }

    // Get Laptop by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public LaptopResponse getLaptopById(@PathVariable Long id) {
        try {
            Laptop laptop = laptopRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Laptop not found with id " + id));
            return mapToResponse(laptop);
        } catch (Exception ex) {
            throw new RuntimeException("Error fetching laptop with id " + id, ex);
        }
    }

    // Update Laptop
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public LaptopResponse updateLaptop(@PathVariable Long id, @Valid @RequestBody LaptopRequest request) {
        try {
            Laptop laptop = laptopRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Laptop not found with id " + id));

            laptop.setSerialNumber(request.getSerialNumber());
            laptop.setModel(request.getModel());
            laptop.setStatus(request.getStatus());

            Laptop updated = laptopRepository.save(laptop);
            return mapToResponse(updated);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating laptop with id " + id, ex);
        }
    }

    // Delete Laptop
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteLaptop(@PathVariable Long id) {
        try {
            if (!laptopRepository.existsById(id)) {
                throw new RuntimeException("Laptop not found with id " + id);
            }
            laptopRepository.deleteById(id);
            return "Laptop deleted successfully";
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting laptop with id " + id, ex);
        }
    }

    // Mapper: Entity -> Response DTO
    private LaptopResponse mapToResponse(Laptop laptop) {
        LaptopResponse response = new LaptopResponse();
        response.setId(laptop.getId());
        response.setSerialNumber(laptop.getSerialNumber());
        response.setModel(laptop.getModel());
        response.setStatus(laptop.getStatus());
        return response;
    }
}
