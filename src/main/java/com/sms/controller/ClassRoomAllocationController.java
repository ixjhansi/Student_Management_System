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

@RestController
@RequestMapping("/api/class-room-allocations")
public class ClassRoomAllocationController {

	@Autowired
	private ClassRoomAllocationRepository allocationRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private RoomRepository roomRepository;

	// Create
	@PostMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public ClassRoomAllocationResponse createAllocation(@RequestBody ClassRoomAllocationRequest request) {
		ClassEntity classEntity = classRepository.findById(request.getClassId())
				.orElseThrow(() -> new RuntimeException("Class not found"));

		Room room = roomRepository.findById(request.getRoomId())
				.orElseThrow(() -> new RuntimeException("Room not found"));

		ClassRoomAllocation allocation = new ClassRoomAllocation();
		allocation.setStatus(request.getStatus());
		allocation.setClassEntity(classEntity);
		allocation.setRoom(room);

		ClassRoomAllocation saved = allocationRepository.save(allocation);
		return mapToResponse(saved);
	}

	// ⬇️ Changed: Pagination support added
	@GetMapping
	@PreAuthorize("hasAnyAuthority('sdmin')")
	public Page<ClassRoomAllocationResponse> getAllAllocations(Pageable pageable) {
		return allocationRepository.findAll(pageable).map(this::mapToResponse); // convert each entity → response DTO
	}

	// Get by ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ClassRoomAllocationResponse getAllocationById(@PathVariable Long id) {
		ClassRoomAllocation allocation = allocationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Allocation not found"));
		return mapToResponse(allocation);
	}

	// Update
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public ClassRoomAllocationResponse updateAllocation(@PathVariable Long id,
			@RequestBody ClassRoomAllocationRequest request) {
		ClassRoomAllocation allocation = allocationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Allocation not found"));

		ClassEntity classEntity = classRepository.findById(request.getClassId())
				.orElseThrow(() -> new RuntimeException("Class not found"));

		Room room = roomRepository.findById(request.getRoomId())
				.orElseThrow(() -> new RuntimeException("Room not found"));

		allocation.setStatus(request.getStatus());
		allocation.setClassEntity(classEntity);
		allocation.setRoom(room);

		ClassRoomAllocation updated = allocationRepository.save(allocation);
		return mapToResponse(updated);
	}

	// Delete
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public String deleteAllocation(@PathVariable Long id) {
		allocationRepository.deleteById(id);
		return "Class-Room allocation deleted successfully";
	}

	// Mapper
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