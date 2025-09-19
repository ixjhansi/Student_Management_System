package com.sms.service;

import com.sms.model.Assignment;
import com.sms.model.ClassEntity;
import com.sms.model.Student;
import com.sms.model.Teacher;
import com.sms.repository.AssignmentRepository;
import com.sms.repository.ClassRepository;
import com.sms.repository.StudentRepository;
import com.sms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Transactional
    public Assignment createAssignment(String title, String description, 
                                       java.time.LocalDateTime dueDate, 
                                       Long classId, Long teacherId) {

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Assignment assignment = new Assignment();
        assignment.setTitle(title);
        assignment.setDescription(description);
        assignment.setDueDate(dueDate);
        assignment.setClassEntity(classEntity);
        assignment.setTeacher(teacher);

        Assignment saved = assignmentRepository.save(assignment);

        // Send mails to all students in the class
        for (Student student : classEntity.getStudents()) {
            if ("active".equalsIgnoreCase(student.getStatus())) {
                sendAssignmentMail(student.getEmail(), assignment, teacher.getName(), classEntity.getName());
            }
        }

        return saved;
    }

    private void sendAssignmentMail(String to, Assignment assignment, String teacherName, String className) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Assignment: " + assignment.getTitle());
        message.setText(
            "Hello,\n\n" +
            "A new assignment has been posted for your class: " + className + "\n\n" +
            "Title: " + assignment.getTitle() + "\n" +
            "Description: " + assignment.getDescription() + "\n" +
            "Due Date: " + assignment.getDueDate() + "\n\n" +
            "Assigned by: " + teacherName + "\n\n" +   // <- this line
            "Regards,\nStudent Management System"
        );
        mailSender.send(message);
    }

}
