package com.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.model.LaptopHistory;

public interface LaptopHistoryRepository extends JpaRepository<LaptopHistory, Long> {
	List<LaptopHistory> findByStudent_Id(Long studentId);
}