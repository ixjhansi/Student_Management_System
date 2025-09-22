package com.sms.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequest {

    @NotNull(message = "Capacity cannot be null")
    private Integer capacity;

    @NotBlank(message = "Status cannot be empty")
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
