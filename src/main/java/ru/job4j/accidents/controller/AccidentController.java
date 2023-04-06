package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/accidents")
    public String index(Model model) {
        var accidents = accidentService.findAll();
        model.addAttribute("accidents", accidents);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/accidents";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = accidentTypeService.findAll();
        model.addAttribute("types", types);
        List<Rule> rules = ruleService.findAll();
        model.addAttribute("rules", rules);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
        try {
            String[] ids = req.getParameterValues("rIds");
            Set<Rule> rules = ruleService.getSetRuleByIdArray(ids);
            accident.setRules(rules);
            accidentService.create(accident);
            return "redirect:/accidents";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/edit_accident/{id}")
    public String editById(Model model, @PathVariable int id) {
        var accidentOptional = accidentService.findById(id);
        List<AccidentType> types = accidentTypeService.findAll();
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Происшествие с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("types", types);
        model.addAttribute("accident", accidentOptional.get());
        List<Rule> rules = ruleService.findAll();
        model.addAttribute("rules", rules);
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/edit_accident";
    }

    @PostMapping("/edit_accident")
    public String edit(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
        try {
            String[] ids = req.getParameterValues("rIds");
            Set<Rule> rules = ruleService.getSetRuleByIdArray(ids);
            accident.setRules(rules);
            accidentService.replace(accident);
            return "redirect:/accidents";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/formUpdateAccident")
    public String updateById(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findById(id).get());
        model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "/formUpdateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        Set<Rule> rules = accidentService.findById(accident.getId()).get().getRules();
        accident.setRules(rules);
        accidentService.replace(accident);
        return "redirect:/";
    }
}