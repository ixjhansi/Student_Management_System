package com.sms.service;

import com.sms.model.Assignment;
import com.sms.model.AssignmentSubmission;
import com.sms.model.Student;
import com.sms.repository.AssignmentRepository;
import com.sms.repository.AssignmentSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherSubmissionReportScheduler {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentSubmissionRepository submissionRepository;

    @Autowired
    private MailService mailService;

    // DTO to send to Thymeleaf template
    public static class StudentReportDTO {
        private Long id;
        private String name;
        private String status;

        public StudentReportDTO(Long id, String name, String status) {
            this.id = id;
            this.name = name;
            this.status = status;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public String getStatus() { return status; }
    }

    @Transactional
    @Scheduled(cron = "0 * * * * ?") // runs every minute
    public void sendSubmissionReports() {
        LocalDateTime now = LocalDateTime.now();
        List<Assignment> assignments = assignmentRepository.findAll();

        for (Assignment assignment : assignments) {

            if (assignment.isReportSent()) continue;
            if (assignment.getTeacher() == null || assignment.getTeacher().getEmail() == null) continue;
            if (assignment.getDueDate() == null) continue;

            // 1 minute after due date window
            LocalDateTime reportTime = assignment.getDueDate().plusMinutes(2);
            if (now.isAfter(reportTime.minusMinutes(1)) && now.isBefore(reportTime.plusMinutes(1))) {

                List<Student> students = new ArrayList<>(assignment.getClassEntity().getStudents());
                List<AssignmentSubmission> submissions = submissionRepository.findByAssignmentId(assignment.getId());

                List<StudentReportDTO> studentList = new ArrayList<>();
                for (Student student : students) {
                    String status = submissions.stream()
                            .filter(s -> s.getStudent().getId().equals(student.getId()))
                            .map(AssignmentSubmission::getStatus)
                            .findFirst()
                            .orElse("Not Submitted");

                    studentList.add(new StudentReportDTO(student.getId(), student.getName(), status));
                }

                // Prepare Thymeleaf context
                Context context = new Context();
                context.setVariable("assignmentTitle", assignment.getTitle());
                context.setVariable("students", studentList);

                // Send HTML mail
                mailService.sendHtmlMail(
                        assignment.getTeacher().getEmail(),
                        "Assignment Submission Report: " + assignment.getTitle(),
                        "assignmentReport",
                        context
                );

                assignment.setReportSent(true);
                assignmentRepository.save(assignment);
                System.out.println("✅ Report sent to teacher for assignment: " + assignment.getTitle());
            }
        }
    }
}
