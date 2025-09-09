package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sms.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
