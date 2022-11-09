package com.spring.mvc.dao;

import com.spring.mvc.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(name, author, release_year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(), book.getReleaseYear());
    }

    @Override
    public void update(Long id, Book book) {
        jdbcTemplate.update("UPDATE books SET name = ?, author = ?, person_id = ?, release_year = ? WHERE id = ?", book.getName(), book.getAuthor(), book.getPersonId(), book.getReleaseYear(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    @Override
    public List<Book> findAllByPersonId(Long id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
