package com.sms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sms.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // 🔹 New filter query
    @Query("SELECT t FROM Teacher t " +
           "WHERE (:id IS NULL OR t.id = :id) " +
           "AND (:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Teacher> filterTeachers(@Param("id") Long id,
                                 @Param("name") String name,
                                 Pageable pageable);
}
