package com.spring.mvc.controller;

import com.spring.mvc.dao.BookDAO;
import com.spring.mvc.dao.PersonDAO;
import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.Person;
import com.spring.mvc.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private BookValidator bookValidator;

    @GetMapping()
    public String showBooksPage(Model model) {
        model.addAttribute("books", bookDAO.findAll());
        return "books/show-all-books";
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") Long id,
                               @ModelAttribute("person") Person person,
                               Model model) {
        Book book = bookDAO.findById(id);
        model.addAttribute("book", book);
        if (book.getPersonId() != null) {
            model.addAttribute("person", personDAO.findById(book.getPersonId()));
        } else {
            model.addAttribute("person", person);
            model.addAttribute("people", personDAO.findAll());
        }
        return "books/show-book";
    }

    @GetMapping("/new")
    public String createBookPage(@ModelAttribute("book") Book book,
                                 Model model) {
        model.addAttribute("book", book);
        return "books/create-book";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "books/create-book";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String updateBookPage(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("book", bookDAO.findById(id));
        return "/books/update-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") Long id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "/books/update-book";
        }
        bookDAO.update(id, book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBookToPerson(@PathVariable("id") Long id,
                                     @ModelAttribute("person") Person person) {
        bookDAO.assign(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String removeBookFromPerson(@PathVariable("id") Long id,
                                       @ModelAttribute("person") Person person) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookDAO.delete(id);
        return "redirect:/books";
    }


}
