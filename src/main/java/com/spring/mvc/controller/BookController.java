package com.spring.mvc.controller;

import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.Person;
import com.spring.mvc.service.BookService;
import com.spring.mvc.service.PersonService;
import com.spring.mvc.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BookValidator bookValidator;

    @GetMapping("/search")
    public String searchBooksPage() {
        return "books/search-books";
    }

    @PostMapping("/search")
    public String searchBooks(@RequestParam("name") String name,
                              Model model) {
        List<Book> books = bookService.findBooksByNameStartsWith(name);
        books.forEach(System.out::println);
        System.out.println("--------------");
        System.out.println(books.size());
        System.out.println(books.isEmpty());
        System.out.println(books == null);
        model.addAttribute("books", books);
        return "books/search-books";
    }

    @GetMapping()
    public String showBooksPage(@RequestParam(name = "page", required = false) Integer page,
                                @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
                                @RequestParam(name = "sort_by_year", required = false) Boolean sortByBear,
                                Model model) {
        if (page != null && booksPerPage != null && sortByBear != null) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage, Sort.by("releaseYear")));
            return "books/show-all-books";
        } else if (page != null && booksPerPage != null) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage));
            return "books/show-all-books";
        } else if (sortByBear != null) {
            model.addAttribute("books", bookService.findAll(Sort.by("releaseYear")));
            return "books/show-all-books";
        } else {
            model.addAttribute("books", bookService.findAll());
            return "books/show-all-books";
        }
    }

    @GetMapping("/{id}")
    public String showBookPage(@PathVariable("id") Long id,
                               @ModelAttribute("person") Person person,
                               Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        if (book.getPerson() != null) {
            model.addAttribute("person", book.getPerson());
        } else {
            model.addAttribute("person", person);
            model.addAttribute("people", personService.findAll());
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
        if (bindingResult.hasErrors()) {
            return "books/create-book";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String updateBookPage(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/books/update-book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") Long id,
                             @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/books/update-book";
        }
        bookService.update(id, book);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBookToPerson(@PathVariable("id") Long id,
                                     @ModelAttribute("person") Person person) {
        bookService.assign(id, person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String removeBookFromPerson(@PathVariable("id") Long id,
                                       @ModelAttribute("person") Person person) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }


}
