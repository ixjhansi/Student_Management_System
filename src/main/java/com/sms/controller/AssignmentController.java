package com.sms.controller;

import com.sms.model.Assignment;
import com.sms.request.AssignmentRequest;
import com.sms.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping
    
    public Assignment createAssignment(@RequestBody AssignmentRequest request) {
        return assignmentService.createAssignment(
                request.getTitle(),
                request.getDescription(),
                request.getDueDate(),
                request.getClassId(),
                request.getTeacherId()
        );
    }
}
