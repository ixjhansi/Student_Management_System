package com.sms.request;

public class ClassSubjectsRequest {
    private Long classId;
    private Long subjectId;

    // Getters and Setters
    public Long getClassId() {
        return classId;
    }
    public void setClassId(Long classId) {
        this.classId = classId;
    }
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}