package com.upb.qresent.administrator;

import org.springframework.stereotype.Controller;

@Controller
public class AdministratorController {
    private AdministratorRepository administratorRepository;

    public AdministratorController(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }
}
