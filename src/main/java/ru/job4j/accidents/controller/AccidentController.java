package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;

    @GetMapping("/accidents")
    public String index(Model model) {
        var accidents = accidentService.findAll();
        model.addAttribute("accidents", accidents);
        model.addAttribute("user", "IVAN IVANOV");
        return "/accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = accidentTypeService.findAll();
        model.addAttribute("types", types);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model) {
        try {
            accidentService.create(accident);
            return "redirect:/accidents";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/edit_accident/{id}")
    public String updateById(Model model, @PathVariable int id) {
        var accidentOptional = accidentService.findById(id);
        List<AccidentType> types = accidentTypeService.findAll();
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Происшествие с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("types", types);
        model.addAttribute("accident", accidentOptional.get());
        return "/edit_accident";
    }

    @PostMapping("/edit_accident")
    public String update(@ModelAttribute Accident accident, Model model) {
        try {
            accidentService.replace(accident.getId(), accident);
            return "redirect:/accidents";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findById(id).get());
        return "/formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.replace(accident.getId(), accident);
        return "redirect:/";
    }
}