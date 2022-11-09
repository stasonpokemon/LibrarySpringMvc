package com.spring.mvc.dao;

import com.spring.mvc.entity.Book;

import java.util.List;

public interface BookDAO extends EntityDAO<Book> {

    List<Book> findAllByPersonId(Long id);
}
