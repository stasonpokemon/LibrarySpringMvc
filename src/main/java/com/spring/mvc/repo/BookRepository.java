package com.spring.mvc.repo;

import com.spring.mvc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByPersonId(Long id);


    List<Book> findBooksByNameStartsWith(String name);
}
