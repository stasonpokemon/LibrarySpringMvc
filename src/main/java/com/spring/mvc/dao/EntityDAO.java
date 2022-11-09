package com.spring.mvc.dao;

import java.util.List;

public interface EntityDAO<T>{

    List<T> findAll();

    T findById(Long id);

    void save(T t);

    void update(Long id, T t);

    void delete(Long id);
}
