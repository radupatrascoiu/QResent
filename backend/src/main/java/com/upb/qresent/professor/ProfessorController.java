package com.upb.qresent.professor;

import org.springframework.stereotype.Controller;

@Controller
public class ProfessorController {
    private ProfessorRepository professorRepository;

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }
}
