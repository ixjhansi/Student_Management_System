package com.sms.repository;

import com.sms.model.Student;
import com.sms.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT DISTINCT sm.student FROM SubjectMarks sm WHERE sm.subject.id = :subjectId")
    List<Student> findStudentsBySubjectId(@Param("subjectId") Long subjectId);
}