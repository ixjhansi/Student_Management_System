package com.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.model.LaptopHistory;

public interface LaptopHistoryRepository extends JpaRepository<LaptopHistory, Long> {
}