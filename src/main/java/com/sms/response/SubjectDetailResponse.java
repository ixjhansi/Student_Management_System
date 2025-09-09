package com.sms.response;

import java.util.List;

public class SubjectDetailResponse {
    private Long id;
    private String name;
    private String status;
    private List<String> teachers;   // teacher names
    private List<String> classes;    // class names
    private List<String> students;   // student names

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
	public List<String> getTeachers() {
        return teachers;
    }
    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }
    public List<String> getClasses() {
        return classes;
    }
    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
    public List<String> getStudents() {
        return students;
    }
    public void setStudents(List<String> students) {
        this.students = students;
    }
}