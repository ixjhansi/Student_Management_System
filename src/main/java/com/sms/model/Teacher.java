package com.sms.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TEACHERS")
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "QUALIFICATION")
	private String qualification;

	@Column(name = "STATUS")
	private String status = "active"; // default active

	// Remove old @ManyToMany for subjects and classes

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
	private Set<TeacherSubjects> teacherSubjects;

	// constructors
	public Teacher() {
		super();
	}

	public Teacher(String name, String phone, String email, String qualification, String status,
			Set<TeacherSubjects> teacherSubjects) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.qualification = qualification;
		this.status = status;
		this.teacherSubjects = teacherSubjects;
	}

	public Teacher(Long id, String name, String phone, String email, String qualification, String status,
			Set<TeacherSubjects> teacherSubjects) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.qualification = qualification;
		this.status = status;
		this.teacherSubjects = teacherSubjects;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<TeacherSubjects> getTeacherSubjects() {
		return teacherSubjects;
	}

	public void setTeacherSubjects(Set<TeacherSubjects> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}
}
