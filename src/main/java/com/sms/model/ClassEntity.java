package com.sms.model;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "CLASS")
public class ClassEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STATUS", nullable = false)
    private String status = "Active"; // default value


	// Remove old @ManyToMany for teachers

	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private Set<TeacherSubjects> teacherSubjects;

	// CLASS <-> STUDENTS (One-to-Many)
	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private Set<Student> students;

	// Remove the old @ManyToMany for subjects, and replace it with:
	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private Set<ClassSubjects> classSubjects;

//	Remove the old @ManyToMany with Room, and replace it with:

	@OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
	private Set<ClassRoomAllocation> classRoomAllocations;

	// constructors
	public ClassEntity() {
		super();
	}

	
	public ClassEntity(String name, String status, Set<TeacherSubjects> teacherSubjects, Set<Student> students,
			Set<ClassSubjects> classSubjects, Set<ClassRoomAllocation> classRoomAllocations) {
		super();
		this.name = name;
		this.status = status;
		this.teacherSubjects = teacherSubjects;
		this.students = students;
		this.classSubjects = classSubjects;
		this.classRoomAllocations = classRoomAllocations;
	}


	public ClassEntity(Long id, String name, String status, Set<TeacherSubjects> teacherSubjects, Set<Student> students,
			Set<ClassSubjects> classSubjects, Set<ClassRoomAllocation> classRoomAllocations) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.teacherSubjects = teacherSubjects;
		this.students = students;
		this.classSubjects = classSubjects;
		this.classRoomAllocations = classRoomAllocations;
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

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<ClassSubjects> getClassSubjects() {
		return classSubjects;
	}

	public void setClassSubjects(Set<ClassSubjects> classSubjects) {
		this.classSubjects = classSubjects;
	}

	public Set<ClassRoomAllocation> getClassRoomAllocations() {
		return classRoomAllocations;
	}

	public void setClassRoomAllocations(Set<ClassRoomAllocation> classRoomAllocations) {
		this.classRoomAllocations = classRoomAllocations;
	}
}
