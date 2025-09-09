package com.sms.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LAPTOP_HISTORY")
public class LaptopHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "DATE_ISSUED")
	private LocalDate dateIssued;

	@Column(name = "DATE_RETURNED")
	private LocalDate dateReturned;

	// Many histories belong to one Student
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
	private Student student;

	// Many histories belong to one Laptop
	@ManyToOne
	@JoinColumn(name = "LAPTOP_ID", referencedColumnName = "ID")
	private Laptop laptop;

	// constructors
	public LaptopHistory() {
		super();
	}

	public LaptopHistory(LocalDate dateIssued, LocalDate dateReturned, Student student, Laptop laptop) {
		super();
		this.dateIssued = dateIssued;
		this.dateReturned = dateReturned;
		this.student = student;
		this.laptop = laptop;
	}

	public LaptopHistory(Long id, LocalDate dateIssued, LocalDate dateReturned, Student student, Laptop laptop) {
		super();
		this.id = id;
		this.dateIssued = dateIssued;
		this.dateReturned = dateReturned;
		this.student = student;
		this.laptop = laptop;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateIssued() {
		return dateIssued;
	}

	public void setDateIssued(LocalDate dateIssued) {
		this.dateIssued = dateIssued;
	}

	public LocalDate getDateReturned() {
		return dateReturned;
	}

	public void setDateReturned(LocalDate dateReturned) {
		this.dateReturned = dateReturned;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Laptop getLaptop() {
		return laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}
}
