package com.sms.controller;

import com.sms.model.Room;

import com.sms.request.RoomRequest;
import com.sms.response.RoomResponse;

import com.sms.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	// Create Room
	@PostMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public RoomResponse createRoom(@RequestBody RoomRequest request) {
		Room room = new Room();
		room.setCapacity(request.getCapacity());
		room.setStatus(request.getStatus());

		Room saved = roomRepository.save(room);
		return mapToResponse(saved);
	}

	// Get All Rooms with Pagination
	@GetMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public Page<RoomResponse> getAllRooms(Pageable pageable) {
		return roomRepository.findAll(pageable).map(this::mapToResponse);
	}

	// Get Room by ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public RoomResponse getRoomById(@PathVariable Long id) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id " + id));
		return mapToResponse(room);
	}

	// Update Room
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public RoomResponse updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id " + id));

		room.setCapacity(request.getCapacity());
		room.setStatus(request.getStatus());

		Room updated = roomRepository.save(room);
		return mapToResponse(updated);
	}

	// Delete Room
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public String deleteRoom(@PathVariable Long id) {
		roomRepository.deleteById(id);
		return "Room deleted successfully";
	}

	// Mapper: Entity -> Response DTO
	private RoomResponse mapToResponse(Room room) {
		RoomResponse response = new RoomResponse();
		response.setId(room.getId());
		response.setCapacity(room.getCapacity());
		response.setStatus(room.getStatus());
		return response;
	}
}