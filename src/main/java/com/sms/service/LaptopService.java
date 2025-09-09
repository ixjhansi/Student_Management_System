package com.sms.service;
import org.springframework.stereotype.Service;

import com.sms.model.Laptop;
import com.sms.repository.LaptopRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class LaptopService {

    @Autowired
    private LaptopRepository laptopRepository;

    public Laptop createLaptop(Laptop laptop) {
        return laptopRepository.save(laptop);
    }

    public Optional<Laptop> getLaptopById(Long id) {
        return laptopRepository.findById(id);
    }

    // New method for pagination
    public Page<Laptop> getAllLaptops(Pageable pageable) {
        return laptopRepository.findAll(pageable);
    }
    /*
    // If you still need the non-paginated version
    public List<Laptop> getAllLaptops() {
        return laptopRepository.findAll();
    }
	*/
    public Laptop updateLaptop(Long id, Laptop laptopDetails) {
        return laptopRepository.findById(id).map(laptop -> {
            laptop.setSerialNumber(laptopDetails.getSerialNumber());
            laptop.setModel(laptopDetails.getModel());
            laptop.setStatus(laptopDetails.getStatus());
            return laptopRepository.save(laptop);
        }).orElseThrow(() -> new RuntimeException("Laptop not found with id " + id));
    }

    public void deleteLaptop(Long id) {
        laptopRepository.deleteById(id);
    }
}