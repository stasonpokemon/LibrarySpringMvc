package com.spring.mvc.dao;

import com.spring.mvc.entity.Person;

import java.util.Optional;

public interface PersonDAO extends EntityDAO<Person> {


    Optional<Person> findByFullName(String fullName);
}
