package com.sms.controller;

import com.sms.model.ClassSubjects;
import com.sms.model.ClassEntity;
import com.sms.model.Subject;

import com.sms.request.ClassSubjectsRequest;
import com.sms.response.ClassSubjectsResponse;

import com.sms.repository.ClassSubjectsRepository;
import com.sms.repository.ClassRepository;
import com.sms.repository.SubjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/class-subjects")
public class ClassSubjectsController {

	@Autowired
	private ClassSubjectsRepository classSubjectsRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private com.sms.service.ClassSubjectsService classSubjectsService;

	// Create ClassSubjects mapping
	@PostMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public ClassSubjectsResponse createClassSubject(@RequestBody ClassSubjectsRequest request) {
		ClassEntity classEntity = classRepository.findById(request.getClassId())
				.orElseThrow(() -> new RuntimeException("Class not found with id " + request.getClassId()));
		Subject subject = subjectRepository.findById(request.getSubjectId())
				.orElseThrow(() -> new RuntimeException("Subject not found with id " + request.getSubjectId()));

		ClassSubjects classSubjects = new ClassSubjects();
		classSubjects.setClassEntity(classEntity);
		classSubjects.setSubject(subject);

		ClassSubjects saved = classSubjectsRepository.save(classSubjects);
		return mapToResponse(saved);
	}

	// Get All with Pagination
	@GetMapping
	@PreAuthorize("hasAnyAuthority('admin')")
	public Page<ClassSubjectsResponse> getAllClassSubjects(Pageable pageable) {
		return classSubjectsRepository.findAll(pageable).map(this::mapToResponse);
	}

	// 🔹 New GET: Get all subjects by classId
	@GetMapping("/class/{classId}")
	@PreAuthorize("hasAnyAuthority('admin','student','teacher')")
	public List<ClassSubjectsResponse> getSubjectsByClassId(@PathVariable Long classId) {
		return classSubjectsService.getSubjectsByClassId(classId).stream().map(this::mapToResponse).toList();
	}

	// Delete ClassSubjects mapping
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('admin')")
	public String deleteClassSubjects(@PathVariable Long id) {
		classSubjectsRepository.deleteById(id);
		return "ClassSubjects mapping deleted successfully";
	}

	// Mapper: Entity -> Response DTO
	private ClassSubjectsResponse mapToResponse(ClassSubjects cs) {
		ClassSubjectsResponse response = new ClassSubjectsResponse();
		response.setId(cs.getId());
		response.setClassName(cs.getClassEntity().getName());
		response.setSubjectName(cs.getSubject().getName());
		return response;
	}
}