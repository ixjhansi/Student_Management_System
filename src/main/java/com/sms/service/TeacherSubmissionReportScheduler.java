package com.sms.service;

import com.sms.model.Assignment;
import com.sms.model.AssignmentSubmission;
import com.sms.model.Student;
import com.sms.repository.AssignmentRepository;
import com.sms.repository.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
//teacher
@Service
public class TeacherSubmissionReportScheduler {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Scheduler runs every minute, sends report only once per assignment
     */
    @Transactional
    @Scheduled(cron = "0 */1 * * * ?") // every 1 minute
    public void sendSubmissionReports() {
        List<Assignment> assignments = assignmentRepository.findAll();

        for (Assignment assignment : assignments) {
            if (assignment.getTeacher() == null || assignment.getTeacher().getEmail() == null) continue;
            if (assignment.isReportSent()) continue;
            if (assignment.getDueDate() == null || assignment.getDueDate().plusMinutes(1).isAfter(java.time.LocalDateTime.now()))
                continue;

            // Fetch all students in the class
            List<Student> students = new ArrayList<>(assignment.getClassEntity().getStudents());

            // Fetch all submissions for this assignment
            List<AssignmentSubmission> submissions = submissionRepository.findByAssignmentId(assignment.getId());

            StringBuilder report = new StringBuilder();
            report.append("📘 Submission Report for Assignment: ").append(assignment.getTitle()).append("\n\n");

            for (Student student : students) {
                String status = submissions.stream()
                        .filter(s -> s.getStudent().getId().equals(student.getId()))
                        .map(AssignmentSubmission::getStatus)
                        .findFirst()
                        .orElse("Not Submitted");

                report.append("👨‍🎓 Student ID: ").append(student.getId())
                      .append(", Name: ").append(student.getName())
                      .append(", Status: ").append(status)
                      .append("\n");
            }

            sendMail(assignment.getTeacher().getEmail(),
                    "Assignment Submission Report: " + assignment.getTitle(),
                    report.toString());

            // Mark as sent
            assignment.setReportSent(true);
            assignmentRepository.save(assignment);
        }
    }

    private void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            System.out.println("✅ Report sent to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send mail: " + e.getMessage());
        }
    }
}
