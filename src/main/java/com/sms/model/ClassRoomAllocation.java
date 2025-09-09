package com.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CLASS_ROOM_ALLOCATION")
public class ClassRoomAllocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "STATUS")
	private String status;

	// Many allocations belong to one Class
	@ManyToOne
	@JoinColumn(name = "CLASS_ID", referencedColumnName = "ID")
	private ClassEntity classEntity;

	// Many allocations belong to one Room
	@ManyToOne
	@JoinColumn(name = "ROOM_ID", referencedColumnName = "ID")
	private Room room;

	// constructors
	public ClassRoomAllocation() {
		super();
	}

	public ClassRoomAllocation(String status, ClassEntity classEntity, Room room) {
		super();
		this.status = status;
		this.classEntity = classEntity;
		this.room = room;
	}

	public ClassRoomAllocation(Long id, String status, ClassEntity classEntity, Room room) {
		super();
		this.id = id;
		this.status = status;
		this.classEntity = classEntity;
		this.room = room;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
}
