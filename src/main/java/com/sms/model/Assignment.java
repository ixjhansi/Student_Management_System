package com.sms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ASSIGNMENTS")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "CLASS_ID")
    private ClassEntity classEntity;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDateTime dueDate;

    // ✅ New field to track if the report was sent
    @Column(name = "REPORT_SENT", nullable = false)
    private boolean reportSent = false;

    @Column(name = "REMINDER_SENT", nullable = false)
    private boolean reminderSent = false;

    public Assignment() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ClassEntity getClassEntity() { return classEntity; }
    public void setClassEntity(ClassEntity classEntity) { this.classEntity = classEntity; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public boolean isReportSent() { return reportSent; }
    public void setReportSent(boolean reportSent) { this.reportSent = reportSent; }
    

    public boolean isReminderSent() { return reminderSent; }
    public void setReminderSent(boolean reminderSent) { this.reminderSent = reminderSent; }

}
