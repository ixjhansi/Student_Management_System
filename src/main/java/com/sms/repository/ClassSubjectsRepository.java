package com.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.ClassSubjects;

@Repository
public interface ClassSubjectsRepository extends JpaRepository<ClassSubjects, Long> {
	// Derived query: finds all ClassSubjects by ClassEntity.id
	List<ClassSubjects> findByClassEntity_Id(Long classId);

}
