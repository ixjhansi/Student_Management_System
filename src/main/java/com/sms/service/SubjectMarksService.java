package com.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sms.model.SubjectMarks;
import com.sms.repository.SubjectMarksRepository;


import java.util.Optional;

@Service
public class SubjectMarksService {

    @Autowired
    private SubjectMarksRepository subjectMarksRepository;

    // Create
    public SubjectMarks createSubjectMarks(SubjectMarks sm) {
        return subjectMarksRepository.save(sm);
    }

    // Get by ID
    public Optional<SubjectMarks> getById(Long id) {
        return subjectMarksRepository.findById(id);
    }
/*
    // Get all (non-paginated)
    public List<SubjectMarks> getAll() {
        return subjectMarksRepository.findAll();
    }
*/
    // Get all with pagination
    public Page<SubjectMarks> getAllPaginated(Pageable pageable) {
        return subjectMarksRepository.findAll(pageable);
    }

    // Delete
    public void delete(Long id) {
        subjectMarksRepository.deleteById(id);
    }
}