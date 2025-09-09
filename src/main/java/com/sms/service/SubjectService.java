package com.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sms.model.Subject;
import com.sms.repository.SubjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public Page<Subject> getAllSubjects(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    public Subject updateSubject(Long id, Subject subjectDetails) {
        return subjectRepository.findById(id).map(subject -> {
            subject.setName(subjectDetails.getName());
            return subjectRepository.save(subject);
        }).orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
    }

    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}