package com.sms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ASSIGNMENT_SUBMISSIONS")
public class AssignmentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ASSIGNMENT_ID", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String status = "Not Submitted";

    public AssignmentSubmission() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Assignment getAssignment() { return assignment; }
    public void setAssignment(Assignment assignment) { this.assignment = assignment; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
