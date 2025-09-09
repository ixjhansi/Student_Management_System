package com.sms.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ROOMS")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CAPACITY")
	private Integer capacity;

	@Column(name = "STATUS")
	private String status;

//	Remove the old @ManyToMany with ClassEntity, and replace it with:

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
	private Set<ClassRoomAllocation> classRoomAllocations;

	// CONSTRUCTORS

	public Room() {
		super();
	}

	public Room(Integer capacity, String status, Set<ClassRoomAllocation> classRoomAllocations) {
		super();
		this.capacity = capacity;
		this.status = status;

		this.classRoomAllocations = classRoomAllocations;
	}

	public Room(Long id, Integer capacity, String status, Set<ClassRoomAllocation> classRoomAllocations) {
		super();
		this.id = id;
		this.capacity = capacity;
		this.status = status;

		this.classRoomAllocations = classRoomAllocations;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<ClassRoomAllocation> getClassRoomAllocations() {
		return classRoomAllocations;
	}

	public void setClassRoomAllocations(Set<ClassRoomAllocation> classRoomAllocations) {
		this.classRoomAllocations = classRoomAllocations;
	}
}
