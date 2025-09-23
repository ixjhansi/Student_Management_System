package com.sms.service;

import org.springframework.stereotype.Service;
import com.sms.model.Student;
import com.sms.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Existing getAll
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // 🔹 New filter method
    public Page<Student> filterStudents(Long id, String name, Pageable pageable) {
        return studentRepository.filterStudents(id, name, pageable);
    }

    public List<Student> getStudentsBySubjectId(Long subjectId) {
        return studentRepository.findStudentsBySubjectId(subjectId);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id).map(student -> {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setStatus(studentDetails.getStatus());
            student.setClassEntity(studentDetails.getClassEntity());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
