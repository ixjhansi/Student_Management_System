package com.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TEACHER_SUBJECTS")
public class TeacherSubjects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "ROLE")
	private String role;

	// Many TeacherSubjects belong to one Teacher
	@ManyToOne
	@JoinColumn(name = "TEACHER_ID", referencedColumnName = "ID")
	private Teacher teacher;

	// Many TeacherSubjects belong to one Subject
	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
	private Subject subject;

	// Many TeacherSubjects belong to one Class
	@ManyToOne
	@JoinColumn(name = "CLASS_ID", referencedColumnName = "ID")
	private ClassEntity classEntity;

	// CONSTRUCTORS

	public TeacherSubjects() {
		super();
	}

	public TeacherSubjects(String role, Teacher teacher, Subject subject, ClassEntity classEntity) {
		super();
		this.role = role;
		this.teacher = teacher;
		this.subject = subject;
		this.classEntity = classEntity;
	}

	public TeacherSubjects(Long id, String role, Teacher teacher, Subject subject, ClassEntity classEntity) {
		super();
		this.id = id;
		this.role = role;
		this.teacher = teacher;
		this.subject = subject;
		this.classEntity = classEntity;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}
}
