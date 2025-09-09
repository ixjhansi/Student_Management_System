package com.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SUBJECT_MARKS")
public class SubjectMarks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MARKS")
	private Integer marks;

	// Many marks entries belong to one Student
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID")
	private Student student;

	// Many marks entries belong to one Subject
	@ManyToOne
	@JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID")
	private Subject subject;

	// CONSTRUCTORS
	public SubjectMarks() {
		super();
	}

	public SubjectMarks(Integer marks, Student student, Subject subject) {
		super();
		this.marks = marks;
		this.student = student;
		this.subject = subject;
	}

	public SubjectMarks(Long id, Integer marks, Student student, Subject subject) {
		super();
		this.id = id;
		this.marks = marks;
		this.student = student;
		this.subject = subject;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
