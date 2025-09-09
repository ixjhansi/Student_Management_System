package com.sms.response;

public class ClassRoomAllocationResponse {
    private Long id;
    private String status;
    private Long classId;
    private String className;
    private Long roomId;
    private Integer roomCapacity;
    private String roomStatus;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomCapacity() {
        return roomCapacity;
    }
    public void setRoomCapacity(Integer roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public String getRoomStatus() {
        return roomStatus;
    }
    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
}