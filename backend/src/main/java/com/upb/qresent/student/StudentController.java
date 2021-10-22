package com.upb.qresent.student;

import org.springframework.stereotype.Controller;

@Controller
public class StudentController {
    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
}
