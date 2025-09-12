package com.sms.service;

import org.springframework.stereotype.Service;
import com.sms.model.ClassEntity;
import com.sms.model.ClassSubjects;
import com.sms.repository.ClassSubjectsRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ClassSubjectsService {

	@Autowired
	private ClassSubjectsRepository classSubjectsRepository;

	public ClassSubjects createClassSubject(ClassSubjects cs) {
		return classSubjectsRepository.save(cs);
	}

	public Optional<ClassSubjects> getById(Long id) {
		return classSubjectsRepository.findById(id);
	}

	// Pagination added here
	public Page<ClassSubjects> getAll(Pageable pageable) {
		return classSubjectsRepository.findAll(pageable);
	}

	public void delete(Long id) {
		classSubjectsRepository.deleteById(id);
	}

	// 🔹 New: Get all subjects by classId
	public List<ClassSubjects> getSubjectsByClassId(Long classId) {
		return classSubjectsRepository.findByClassEntity_Id(classId);
	}
}