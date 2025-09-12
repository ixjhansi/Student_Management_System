package com.sms.service;

import org.springframework.stereotype.Service;

import com.sms.model.Room;
import com.sms.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	public Room createRoom(Room room) {
		return roomRepository.save(room);
	}

	public Optional<Room> getRoomById(Long id) {
		return roomRepository.findById(id);
	}

	// Pagination support
	public Page<Room> getAllRooms(Pageable pageable) {
		return roomRepository.findAll(pageable);
	}

	/*
	 * // Old method if you still need it public List<Room> getAllRooms() { return
	 * roomRepository.findAll(); }
	 */
	public Room updateRoom(Long id, Room roomDetails) {
		return roomRepository.findById(id).map(room -> {
			room.setCapacity(roomDetails.getCapacity());
			room.setStatus(roomDetails.getStatus());
			return roomRepository.save(room);
		}).orElseThrow(() -> new RuntimeException("Room not found with id " + id));
	}

	public void deleteRoom(Long id) {
		roomRepository.deleteById(id);
	}
}