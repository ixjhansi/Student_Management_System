package com.sms.controller;

import com.sms.model.AssignmentSubmission;
import com.sms.model.Assignment;
import com.sms.model.Student;
import com.sms.repository.AssignmentRepository;
import com.sms.repository.AssignmentSubmissionRepository;
import com.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
public class AssignmentSubmissionController {

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin','student','teacher')")
    public AssignmentSubmission updateSubmission(
            @RequestParam Long assignmentId,
            @RequestParam Long studentId,
            @RequestParam String status) {

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        AssignmentSubmission submission = submissionRepository
                .findByAssignmentId(assignmentId)
                .stream()
                .filter(s -> s.getStudent().getId().equals(studentId))
                .findFirst()
                .orElseGet(() -> {
                    AssignmentSubmission newSubmission = new AssignmentSubmission();
                    newSubmission.setAssignment(assignment);
                    newSubmission.setStudent(student);
                    return newSubmission;
                });

        submission.setStatus(status);
        return submissionRepository.save(submission);
    }
}
