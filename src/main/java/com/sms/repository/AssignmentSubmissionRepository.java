package com.sms.repository;

import com.sms.model.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentSubmissionRepository extends JpaRepository<AssignmentSubmission, Long> {
    // Use assignmentId to fetch submissions safely
    List<AssignmentSubmission> findByAssignmentId(Long assignmentId);
}
