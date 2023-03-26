package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
public class IndexController {
    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Accident accident = new Accident();
        accident.setName("name");
        accident.setText("text");
        accident.setAddress("address");
        System.out.println(accident);
        accidentService.add(accident);
        var accidents = accidentService.findAll();
        model.addAttribute("accidents", accidents);
        model.addAttribute("user", "IVAN IVANOV");
        return "index";
    }
}


