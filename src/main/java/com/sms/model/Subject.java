package com.sms.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SUBJECT")
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STATUS")
	private String status="active";  // values: "active" or "inactive"

	// Remove old @ManyToMany for teachers and classes

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private Set<TeacherSubjects> teacherSubjects;

	// Remove the old @ManyToMany for classes, and replace it with:
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private Set<ClassSubjects> classSubjects;

//	Remove old @ManyToMany for students and replace it with:

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private Set<SubjectMarks> subjectMarks;


	// CONSTRUCTORS
	public Subject() {
		super();
	}

	

	public Subject(String name, Set<TeacherSubjects> teacherSubjects, Set<ClassSubjects> classSubjects,
			Set<SubjectMarks> subjectMarks, String status) {
		super();
		this.name = name;
		this.teacherSubjects = teacherSubjects;
		this.classSubjects = classSubjects;
		this.subjectMarks = subjectMarks;
		this.status = status;
	}



	public Subject(Long id, String name, Set<TeacherSubjects> teacherSubjects, Set<ClassSubjects> classSubjects,
			Set<SubjectMarks> subjectMarks, String status) {
		super();
		this.id = id;
		this.name = name;
		this.teacherSubjects = teacherSubjects;
		this.classSubjects = classSubjects;
		this.subjectMarks = subjectMarks;
		this.status = status;
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

	public Set<TeacherSubjects> getTeacherSubjects() {
		return teacherSubjects;
	}

	public void setTeacherSubjects(Set<TeacherSubjects> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}

	public Set<ClassSubjects> getClassSubjects() {
		return classSubjects;
	}

	public void setClassSubjects(Set<ClassSubjects> classSubjects) {
		this.classSubjects = classSubjects;
	}

	public Set<SubjectMarks> getSubjectMarks() {
		return subjectMarks;
	}

	public void setSubjectMarks(Set<SubjectMarks> subjectMarks) {
		this.subjectMarks = subjectMarks;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}
	
}
