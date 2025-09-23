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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

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
    private MailService mailService;

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

        for (Student student : classEntity.getStudents()) {
            if ("active".equalsIgnoreCase(student.getStatus())) {
                sendAssignmentMail(student.getEmail(), assignment, teacher.getName(), classEntity.getName(), student.getName());
            }
        }

        return saved;
    }

    private void sendAssignmentMail(String to, Assignment assignment,
                                    String teacherName, String className, String studentName) {

        Context context = new Context();
        context.setVariable("studentName", studentName);
        context.setVariable("assignmentTitle", assignment.getTitle());
        context.setVariable("assignmentDescription", assignment.getDescription());
        context.setVariable("dueDate", assignment.getDueDate());
        context.setVariable("teacherName", teacherName);
        context.setVariable("className", className);

        mailService.sendHtmlMail(to, "New Assignment: " + assignment.getTitle(),
                "newAssignment", context);
    }
}
