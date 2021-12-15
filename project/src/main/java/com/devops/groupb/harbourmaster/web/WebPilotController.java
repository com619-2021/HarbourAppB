package com.devops.groupb.harbourmaster.web;


import com.devops.groupb.harbourmaster.dto.Pilot;
import com.devops.groupb.harbourmaster.service.PilotService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pilots")
public class WebPilotController {

    @Autowired
    PilotService pilotService;

    @GetMapping("/all")
    public String showAllPilots(Model model) {
        String pilotsModelName = "pilots";

        List<Pilot> pilots = pilotService.getAllPilots();

        model.addAttribute(pilotsModelName, pilots);

        return "pages/listPilots";
    }

}
