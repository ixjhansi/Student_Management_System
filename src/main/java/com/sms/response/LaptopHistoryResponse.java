package com.sms.response;

import java.time.LocalDate;

public class LaptopHistoryResponse {
    private Long id;
    private LocalDate dateIssued;
    private LocalDate dateReturned;
    private Long studentId;
    private String studentName;
    private Long laptopId;
    private String laptopSerialNumber;

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getLaptopId() {
        return laptopId;
    }
    public void setLaptopId(Long laptopId) {
        this.laptopId = laptopId;
    }

    public String getLaptopSerialNumber() {
        return laptopSerialNumber;
    }
    public void setLaptopSerialNumber(String laptopSerialNumber) {
        this.laptopSerialNumber = laptopSerialNumber;
    }
}