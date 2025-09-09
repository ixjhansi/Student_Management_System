package com.sms.request;

public class SubjectMarksRequest {
    private Long studentId;
    private Long subjectId;
    private Integer marks;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getMarks() {
        return marks;
    }
    public void setMarks(Integer marks) {
        this.marks = marks;
    }
}