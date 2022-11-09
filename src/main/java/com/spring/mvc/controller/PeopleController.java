package com.spring.mvc.controller;

import com.spring.mvc.dao.BookDAO;
import com.spring.mvc.dao.PersonDAO;
import com.spring.mvc.entity.Person;
import com.spring.mvc.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PersonValidator personValidator;

    @GetMapping
    public String showPeoplePage(Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "people/show-people";
    }

    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("person", personDAO.findById(id));
        model.addAttribute("books", bookDAO.findAllByPersonId(id));
        return "people/show-person";
    }

    @GetMapping("/new")
    public String createPersonPage(@ModelAttribute("person") Person person,
                                   Model model) {
        model.addAttribute("person", person);
        return "people/create-person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/create-person";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String updatePersonPage(@PathVariable("id") Long id,
                                   Model model) {
        model.addAttribute("person", personDAO.findById(id));
        return "people/update-person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") Long id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/update-person";
        }
        personDAO.update(id, person);
        return "redirect:/people/" + id;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
