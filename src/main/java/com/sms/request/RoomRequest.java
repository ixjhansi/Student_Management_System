package com.sms.request;

public class RoomRequest {
    private Integer capacity;
    private String status;

    // Getters and Setters
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}