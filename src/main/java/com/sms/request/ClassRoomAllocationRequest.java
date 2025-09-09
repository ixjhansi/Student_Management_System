package com.sms.request;

public class ClassRoomAllocationRequest {
    private String status;
    private Long classId;
    private Long roomId;

    // Getters and Setters
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getClassId() {
        return classId;
    }
    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}