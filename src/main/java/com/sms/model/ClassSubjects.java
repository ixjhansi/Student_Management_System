package com.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CLASS_SUBJECTS")
public class ClassSubjects {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	// Many ClassSubjects belong to one ClassEntity
	@ManyToOne
	@JoinColumn(name = "CLASS_ID", referencedColumnName = "ID")
	private ClassEntity classEntity;

	// Many ClassSubjects belong to one Subject
	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
	private Subject subject;

	// constructors
	public ClassSubjects() {
		super();
	}

	public ClassSubjects(ClassEntity classEntity, Subject subject) {
		super();
		this.classEntity = classEntity;
		this.subject = subject;
	}

	public ClassSubjects(Long id, ClassEntity classEntity, Subject subject) {
		super();
		this.id = id;
		this.classEntity = classEntity;
		this.subject = subject;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClassEntity getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(ClassEntity classEntity) {
		this.classEntity = classEntity;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
