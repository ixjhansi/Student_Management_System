package com.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.TeacherSubjects;

@Repository
public interface TeacherSubjectsRepository extends JpaRepository<TeacherSubjects, Long> {
	List<TeacherSubjects> findByTeacher_Id(Long teacherId);
}
