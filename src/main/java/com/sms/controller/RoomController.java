package com.sms.controller;

import com.sms.model.Room;
import com.sms.request.RoomRequest;
import com.sms.response.RoomResponse;
import com.sms.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // Create Room
    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomRequest request) {
        try {
            Room room = new Room();
            room.setCapacity(request.getCapacity());
            room.setStatus(request.getStatus());

            Room saved = roomRepository.save(room);
            return ResponseEntity.ok(mapToResponse(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get All Rooms with Pagination
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllRooms(Pageable pageable) {
        try {
            Page<RoomResponse> page = roomRepository.findAll(pageable).map(this::mapToResponse);
            return ResponseEntity.ok(page);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Get Room by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Room not found with id " + id));
            return ResponseEntity.ok(mapToResponse(room));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Update Room
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Room not found with id " + id));

            room.setCapacity(request.getCapacity());
            room.setStatus(request.getStatus());

            Room updated = roomRepository.save(room);
            return ResponseEntity.ok(mapToResponse(updated));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete Room
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            if (!roomRepository.existsById(id)) {
                throw new RuntimeException("Room not found with id " + id);
            }
            roomRepository.deleteById(id);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
