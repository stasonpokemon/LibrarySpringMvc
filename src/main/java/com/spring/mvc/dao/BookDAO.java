package com.spring.mvc.dao;

import com.spring.mvc.entity.Book;
import com.spring.mvc.entity.Person;

import java.util.List;

public interface BookDAO extends EntityDAO<Book> {

    List<Book> findAllByPersonId(Long id);

    void release(Long id);

    void assign(Long id, Person person);
}
