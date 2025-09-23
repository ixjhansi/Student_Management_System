package com.sms.repository;

import com.sms.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT DISTINCT sm.student FROM SubjectMarks sm WHERE sm.subject.id = :subjectId")
    List<Student> findStudentsBySubjectId(@Param("subjectId") Long subjectId);

    // 🔹 New filter query
    @Query("SELECT s FROM Student s " +
           "WHERE (:id IS NULL OR s.id = :id) " +
           "AND (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Student> filterStudents(@Param("id") Long id,
                                 @Param("name") String name,
                                 Pageable pageable);
}
