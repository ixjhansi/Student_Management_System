package com.sms.controller;

import com.sms.request.ClassRoomAllocationRequest;
import com.sms.response.ClassRoomAllocationResponse;

import com.sms.model.ClassEntity;
import com.sms.model.ClassRoomAllocation;
import com.sms.model.Room;

import com.sms.repository.ClassRepository;
import com.sms.repository.ClassRoomAllocationRepository;
import com.sms.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/class-room-allocations")
public class ClassRoomAllocationController {

    @Autowired
    private ClassRoomAllocationRepository allocationRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private RoomRepository roomRepository;

    // ✅ Create Allocation
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ClassRoomAllocationResponse createAllocation(@RequestBody ClassRoomAllocationRequest request) {
        try {
            // validate status
            if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status could not be empty");
            }

            ClassEntity classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class is not found"));

            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room is not found"));

            ClassRoomAllocation allocation = new ClassRoomAllocation();
            allocation.setStatus(request.getStatus().trim());
            allocation.setClassEntity(classEntity);
            allocation.setRoom(room);

            ClassRoomAllocation saved = allocationRepository.save(allocation);
            return mapToResponse(saved);
        } catch (ResponseStatusException ex) {
            throw ex; // handled by GlobalExceptionHandler
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating allocation", e);
        }
    }

    // ✅ Get All Allocations (with pagination)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Page<ClassRoomAllocationResponse> getAllAllocations(Pageable pageable) {
        try {
            return allocationRepository.findAll(pageable).map(this::mapToResponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching allocations", e);
        }
    }

    // ✅ Get Allocation by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ClassRoomAllocationResponse getAllocationById(@PathVariable Long id) {
        try {
            ClassRoomAllocation allocation = allocationRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Allocation not found"));
            return mapToResponse(allocation);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching allocation", e);
        }
    }

    // ✅ Update Allocation
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ClassRoomAllocationResponse updateAllocation(@PathVariable Long id,
                                                        @RequestBody ClassRoomAllocationRequest request) {
        try {
            if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status could not be empty");
            }

            ClassRoomAllocation allocation = allocationRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Allocation not found"));

            ClassEntity classEntity = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class is not found"));

            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room is not found"));

            allocation.setStatus(request.getStatus().trim());
            allocation.setClassEntity(classEntity);
            allocation.setRoom(room);

            ClassRoomAllocation updated = allocationRepository.save(allocation);
            return mapToResponse(updated);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating allocation", e);
        }
    }

    // ✅ Delete Allocation
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteAllocation(@PathVariable Long id) {
        try {
            if (!allocationRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Allocation not found");
            }
            allocationRepository.deleteById(id);
            return "Class-Room allocation deleted successfully";
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting allocation", e);
        }
    }

    // ✅ Mapper Method
    private ClassRoomAllocationResponse mapToResponse(ClassRoomAllocation allocation) {
        ClassRoomAllocationResponse response = new ClassRoomAllocationResponse();
        response.setId(allocation.getId());
        response.setStatus(allocation.getStatus());

        if (allocation.getClassEntity() != null) {
            response.setClassId(allocation.getClassEntity().getId());
            response.setClassName(allocation.getClassEntity().getName());
        }

        if (allocation.getRoom() != null) {
            response.setRoomId(allocation.getRoom().getId());
            response.setRoomCapacity(allocation.getRoom().getCapacity());
            response.setRoomStatus(allocation.getRoom().getStatus());
        }

        return response;
    }
}
