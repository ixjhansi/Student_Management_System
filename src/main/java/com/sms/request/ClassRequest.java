package com.sms.request;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

public class ClassRequest {

    @NotBlank(message = "Class name cannot be empty")
    private String name;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    private List<Long> teacherIds;   // handled via TeacherSubjects
    private List<Long> subjectIds;   // handled via ClassSubjects
    private List<Long> studentIds;   // handled via Students
    private List<Long> roomIds;      // handled via ClassRoomAllocation

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

    public List<Long> getSubjectIds() {
        return subjectIds;
    }
    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }
    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }
    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
}
