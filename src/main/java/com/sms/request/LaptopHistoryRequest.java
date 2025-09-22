package com.sms.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class LaptopHistoryRequest {

    @NotNull(message = "Date issued cannot be null or empty")
    private LocalDate dateIssued;

    @NotNull(message = "Date returned cannot be null or empty")
    private LocalDate dateReturned;

    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Laptop ID cannot be null")
    private Long laptopId;

    // Getters and Setters
    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(Long laptopId) {
        this.laptopId = laptopId;
    }
}
