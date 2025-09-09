package com.sms.service;
import org.springframework.stereotype.Service;

import com.sms.model.ClassRoomAllocation;
import com.sms.repository.ClassRoomAllocationRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ClassRoomAllocationService {

    @Autowired
    private ClassRoomAllocationRepository allocationRepository;

    public ClassRoomAllocation createAllocation(ClassRoomAllocation allocation) {
        return allocationRepository.save(allocation);
    }

    public Optional<ClassRoomAllocation> getById(Long id) {
        return allocationRepository.findById(id);
    }

    // Pagination support
    public Page<ClassRoomAllocation> getAll(Pageable pageable) {
        return allocationRepository.findAll(pageable);
    }

    public void delete(Long id) {
        allocationRepository.deleteById(id);
    }
}