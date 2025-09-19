package com.sms.request;

import jakarta.validation.constraints.NotBlank;

public class LaptopRequest {

    @NotBlank(message = "Serial number cannot be empty")
    private String serialNumber;

    @NotBlank(message = "Model cannot be empty")
    private String model;

    @NotBlank(message = "Status cannot be empty")
    private String status;

    // Getters and Setters
    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
