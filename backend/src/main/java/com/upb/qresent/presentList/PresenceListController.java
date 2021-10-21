package com.upb.qresent.presentList;

import org.springframework.stereotype.Controller;

@Controller
public class PresenceListController {
    private PresenceListRepository presenceListRepository;

    public PresenceListController(PresenceListRepository presenceListRepository) {
        this.presenceListRepository = presenceListRepository;
    }
}
