package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
public class IndexController {
    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        var accidents = accidentService.findAll();
        model.addAttribute("accidents", accidents);
        model.addAttribute("user", "IVAN IVANOV");
        return "/index";
    }
}


