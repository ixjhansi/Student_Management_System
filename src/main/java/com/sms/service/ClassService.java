package com.sms.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sms.model.ClassEntity;
import com.sms.repository.ClassRepository;

import java.util.Optional;

@Service
public class ClassService {

    @Autowired
    private ClassRepository classRepository;

    public ClassEntity createClass(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    public Optional<ClassEntity> getClassById(Long id) {
        return classRepository.findById(id);
    }

    // ⬇️ Changed: return Page<ClassEntity> instead of List<ClassEntity>
    public Page<ClassEntity> getAllClasses(Pageable pageable) {
        return classRepository.findAll(pageable);
    }

    public ClassEntity updateClass(Long id, ClassEntity classDetails) {
        return classRepository.findById(id).map(classEntity -> {
            classEntity.setName(classDetails.getName());
            classEntity.setStatus(classDetails.getStatus());
            return classRepository.save(classEntity);
        }).orElseThrow(() -> new RuntimeException("Class not found with id " + id));
    }

    public void deleteClass(Long id) {
        classRepository.deleteById(id);
    }
}