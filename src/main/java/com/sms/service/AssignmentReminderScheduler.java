package com.sms.service;

import com.sms.model.Assignment;
import com.sms.model.Student;
import com.sms.repository.AssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignmentReminderScheduler {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    @Scheduled(cron = "0 * * * * ?") // runs every hour
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusDays(1); // 1 day before due

        List<Assignment> assignments = assignmentRepository.findAll();

        for (Assignment assignment : assignments) {
            if (assignment.getDueDate() == null || assignment.isReminderSent()) continue;

            // Send reminder if due date is ~1 day from now
            if (assignment.getDueDate().isAfter(now) &&
                assignment.getDueDate().isBefore(reminderTime)) {

                assignment.getClassEntity().getStudents().forEach(student -> {
                    if ("active".equalsIgnoreCase(student.getStatus())) {
                        sendReminderMail(student.getEmail(), assignment);
                    }
                });

                // Mark reminder as sent so it won’t send again
                assignment.setReminderSent(true);
                assignmentRepository.save(assignment);
            }
        }
    }

    private void sendReminderMail(String to, Assignment assignment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Reminder: Assignment Due Soon - " + assignment.getTitle());
        message.setText("Hello,\n\n" +
                "This is a reminder that your assignment is due soon.\n\n" +
                "Title: " + assignment.getTitle() + "\n" +
                "Description: " + assignment.getDescription() + "\n" +
                "Due Date: " + assignment.getDueDate() + "\n\n" +
                "Please complete it on time.\n\n" +
                "Regards,\nStudent Management System");
        mailSender.send(message);
    }
}
