package com.sms.request;

import java.util.List;

public class TeacherRequest {
    private String name;
    private String phone;
    private String email;
    private String qualification;
    private String status;

    // IDs for relations (subjects & classes will be handled via TeacherSubjects join)
    private List<Long> subjectIds;
    private List<Long> classIds;

    // Getters and Setters
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
    public List<Long> getSubjectIds() {
        return subjectIds;
    }
    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
    public List<Long> getClassIds() {
        return classIds;
    }
    public void setClassIds(List<Long> classIds) {
        this.classIds = classIds;
    }
}