package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.ClassSubjects;

@Repository
public interface ClassSubjectsRepository extends JpaRepository<ClassSubjects, Long> {

}
