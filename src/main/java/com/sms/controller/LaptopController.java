package com.sms.controller;

import com.sms.model.Laptop;

import com.sms.request.LaptopRequest;
import com.sms.response.LaptopResponse;

import com.sms.repository.LaptopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    @Autowired
    private LaptopRepository laptopRepository;

    // Create Laptop
    @PostMapping
    public LaptopResponse createLaptop(@RequestBody LaptopRequest request) {
        Laptop laptop = new Laptop();
        laptop.setSerialNumber(request.getSerialNumber());
        laptop.setModel(request.getModel());
        laptop.setStatus(request.getStatus());

        Laptop saved = laptopRepository.save(laptop);
        return mapToResponse(saved);
    }

    // Get All Laptops (Paginated)
    @GetMapping
    public Page<LaptopResponse> getAllLaptops(Pageable pageable) {
        return laptopRepository.findAll(pageable)
                               .map(this::mapToResponse);
    }

    // Get Laptop by ID
    @GetMapping("/{id}")
    public LaptopResponse getLaptopById(@PathVariable Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id " + id));
        return mapToResponse(laptop);
    }

    // Update Laptop
    @PutMapping("/{id}")
    public LaptopResponse updateLaptop(@PathVariable Long id, @RequestBody LaptopRequest request) {
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Laptop not found with id " + id));

        laptop.setSerialNumber(request.getSerialNumber());
        laptop.setModel(request.getModel());
        laptop.setStatus(request.getStatus());

        Laptop updated = laptopRepository.save(laptop);
        return mapToResponse(updated);
    }

    // Delete Laptop
    @DeleteMapping("/{id}")
    public String deleteLaptop(@PathVariable Long id) {
        laptopRepository.deleteById(id);
        return "Laptop deleted successfully";
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