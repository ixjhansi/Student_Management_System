package com.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sms.model.TeacherSubjects;
import com.sms.repository.TeacherSubjectsRepository;


import java.util.Optional;

@Service
public class TeacherSubjectsService {

	@Autowired
	private TeacherSubjectsRepository teacherSubjectsRepository;

	// CREATE
	public TeacherSubjects createTeacherSubject(TeacherSubjects ts) {
		return teacherSubjectsRepository.save(ts);
	}

	// READ by ID
	public Optional<TeacherSubjects> getById(Long id) {
		return teacherSubjectsRepository.findById(id);
	}
/*
	// READ all (without pagination)
	public List<TeacherSubjects> getAll() {
		return teacherSubjectsRepository.findAll();
	}
*/
	// READ all with pagination
	public Page<TeacherSubjects> getAll(Pageable pageable) {
		return teacherSubjectsRepository.findAll(pageable);
	}

	// DELETE
	public void delete(Long id) {
		teacherSubjectsRepository.deleteById(id);
	}
}