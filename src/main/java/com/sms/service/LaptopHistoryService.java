package com.sms.service;
import org.springframework.stereotype.Service;

import com.sms.model.LaptopHistory;
import com.sms.repository.LaptopHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class LaptopHistoryService {

    @Autowired
    private LaptopHistoryRepository laptopHistoryRepository;

    public LaptopHistory createHistory(LaptopHistory history) {
        return laptopHistoryRepository.save(history);
    }

    public Optional<LaptopHistory> getHistoryById(Long id) {
        return laptopHistoryRepository.findById(id);
    }

    // Pagination support
    public Page<LaptopHistory> getAllHistories(Pageable pageable) {
        return laptopHistoryRepository.findAll(pageable);
    }
/*
    // Old method if needed
    public List<LaptopHistory> getAllHistories() {
        return laptopHistoryRepository.findAll();
    }
*/
    public void deleteHistory(Long id) {
        laptopHistoryRepository.deleteById(id);
    }
}