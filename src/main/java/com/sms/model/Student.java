package com.sms.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "STUDENTS")
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "STATUS")
	private String status = "active"; // default active

	// STUDENT -> CLASS (Many-to-One)
	@ManyToOne
	@JoinColumn(name = "CLASS_ID", referencedColumnName = "ID")
	private ClassEntity classEntity;

//	Remove old @ManyToMany for subjects and replace it with:

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<SubjectMarks> subjectMarks;

	// Replace old ManyToMany laptops field with OneToMany LaptopHistory

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private Set<LaptopHistory> laptopHistories;

	public Student() {
		super();
	}

	public Student(String name, String email, String status, ClassEntity classEntity, Set<SubjectMarks> subjectMarks,
			Set<LaptopHistory> laptopHistories) {
		super();
		this.name = name;
		this.email = email;
		this.status = status;
		this.classEntity = classEntity;
		this.subjectMarks = subjectMarks;
		this.laptopHistories = laptopHistories;
	}

	public Student(Long id, String name, String email, String status, ClassEntity classEntity,
			Set<SubjectMarks> subjectMarks, Set<LaptopHistory> laptopHistories) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.status = status;
		this.classEntity = classEntity;
		this.subjectMarks = subjectMarks;
		this.laptopHistories = laptopHistories;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<SubjectMarks> getSubjectMarks() {
		return subjectMarks;
	}

	public void setSubjectMarks(Set<SubjectMarks> subjectMarks) {
		this.subjectMarks = subjectMarks;
	}

	public Set<LaptopHistory> getLaptopHistories() {
		return laptopHistories;
	}

	public void setLaptopHistories(Set<LaptopHistory> laptopHistories) {
		this.laptopHistories = laptopHistories;
	}
}
