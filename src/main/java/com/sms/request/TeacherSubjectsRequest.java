package com.sms.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TeacherSubjectsRequest {

    @NotNull(message = "Teacher ID cannot be null")
    private Long teacherId;

    @NotNull(message = "Subject ID cannot be null")
    private Long subjectId;

    @NotNull(message = "Class ID cannot be null")
    private Long classId;

    @NotBlank(message = "Role cannot be empty")
    private String role;

    // Getters and Setters
    public Long getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getClassId() {
        return classId;
    }
    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
