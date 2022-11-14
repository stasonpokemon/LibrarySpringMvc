package com.spring.mvc.service;

import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.Person;
import com.spring.mvc.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public List<Book> findAll(Sort releaseYear) {
        return bookRepository.findAll(releaseYear);
    }

    public Page<Book> findAll(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage));
    }

    public Page<Book> findAll(int page, int booksPerPage, Sort releaseYear) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage, releaseYear));
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NullPointerException(new StringBuilder()
                .append("Book with id = ")
                .append(id)
                .append(" not found").toString()));
    }

    public List<Book> findAllByPersonId(Long id) {
        Date currentDate = new Date();
        List<Book> books = bookRepository.findByPersonId(id);
        if (!books.isEmpty()) {
            books.forEach(book -> {
                Date bookDate = book.getBookDate();
                int differenceInDays = (int) ((currentDate.getTime() - bookDate.getTime()) / (24 * 60 * 60 * 1000));
                if (differenceInDays >= 10) {
                    book.setIsOverdue(true);
                }
            });
        }
        return books;
    }


    public List<Book> findBooksByNameStartsWith(String name) {
        return bookRepository.findBooksByNameStartsWith(name);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(Long id, Book bookFromForm) {
        Book bookFromDb = findById(id);
        bookFromDb.setAuthor(bookFromForm.getAuthor());
        bookFromDb.setName(bookFromForm.getName());
        bookFromDb.setReleaseYear(bookFromForm.getReleaseYear());
        bookRepository.save(bookFromDb);
    }

    @Transactional
    public void delete(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    @Transactional
    public void release(Long id) {
        Book book = findById(id);
        book.setPerson(null);
        book.setBookDate(null);
//        bookRepository.save(book);
    }

    @Transactional
    public void assign(Long id, Person person) {
        Book book = findById(id);
        book.setPerson(person);
        book.setIsOverdue(false);
        book.setBookDate(new Date());
//        bookRepository.save(book);
    }


}
