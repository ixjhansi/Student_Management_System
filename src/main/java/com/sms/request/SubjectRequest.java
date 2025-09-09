package com.sms.request;

import java.util.List;

public class SubjectRequest {
    private String name;
    private String status;
    // IDs for relations (handled via join tables)
    private List<Long> teacherIds;
    private List<Long> classIds;
    private List<Long> studentIds;

    // Getters and Setters
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
	public List<Long> getTeacherIds() {
        return teacherIds;
    }
    public void setTeacherIds(List<Long> teacherIds) {
        this.teacherIds = teacherIds;
    }
    public List<Long> getClassIds() {
        return classIds;
    }
    public void setClassIds(List<Long> classIds) {
        this.classIds = classIds;
    }
    public List<Long> getStudentIds() {
        return studentIds;
    }
    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}