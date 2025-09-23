package com.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sms.model.Teacher;
import com.sms.repository.TeacherRepository;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    // CREATE
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // READ by ID
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // READ all with Pagination
    public Page<Teacher> getTeachersPaginated(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    // 🔹 New filter method
    public Page<Teacher> filterTeachers(Long id, String name, Pageable pageable) {
        return teacherRepository.filterTeachers(id, name, pageable);
    }

    // UPDATE
    public Teacher updateTeacher(Long id, Teacher teacherDetails) {
        return teacherRepository.findById(id).map(teacher -> {
            teacher.setName(teacherDetails.getName());
            teacher.setStatus(teacherDetails.getStatus());
            teacher.setPhone(teacherDetails.getPhone());
            teacher.setEmail(teacherDetails.getEmail());
            teacher.setQualification(teacherDetails.getQualification());
            return teacherRepository.save(teacher);
        }).orElseThrow(() -> new RuntimeException("Teacher not found with id " + id));
    }

    // DELETE
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
