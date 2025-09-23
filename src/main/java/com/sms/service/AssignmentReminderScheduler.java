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
    private MailService mailService;

    @Transactional
    @Scheduled(cron = "0 * * * * ?") // runs every minute
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();

        List<Assignment> assignments = assignmentRepository.findAll();

        for (Assignment assignment : assignments) {
            if (assignment.isReminderSent() || assignment.getDueDate() == null) continue;

            // Check if current time is 1 minute before dueDate
            LocalDateTime reminderTime = assignment.getDueDate().minusMinutes(1);//for testing
            //LocalDateTime reminderTime = assignment.getDueDate().minusHours(24);//previous day

            if (now.isAfter(reminderTime.minusSeconds(30)) && now.isBefore(reminderTime.plusSeconds(30))) {

                assignment.getClassEntity().getStudents().forEach(student -> {
                    if ("active".equalsIgnoreCase(student.getStatus()) && student.getEmail() != null) {
                        // Prepare Thymeleaf context
                        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
                        context.setVariable("studentName", student.getName());
                        context.setVariable("assignmentTitle", assignment.getTitle());
                        context.setVariable("assignmentDescription", assignment.getDescription());
                        context.setVariable("dueDate", assignment.getDueDate().toString());

                        // Send HTML mail using template
                        mailService.sendHtmlMail(student.getEmail(), 
                                "Reminder: Assignment Due Soon - " + assignment.getTitle(),
                                "assignmentReminder", context);
                    }
                });

                assignment.setReminderSent(true); // mark as sent
                assignmentRepository.save(assignment);
                System.out.println("✅ Reminder sent for assignment: " + assignment.getTitle());
            }
        }
    }
}
