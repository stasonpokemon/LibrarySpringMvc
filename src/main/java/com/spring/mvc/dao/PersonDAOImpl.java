package com.spring.mvc.dao;

import com.spring.mvc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM people", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Optional<Person> findByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM people WHERE full_name = ?", new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO people(full_name, year_of_birthday) VALUES(?, ?)", person.getFullName(), person.getYearOfBirthday());
    }

    @Override
    public void update(Long id, Person person) {
        jdbcTemplate.update("UPDATE people SET full_name = ?, year_of_birthday = ? WHERE id = ?", person.getFullName(), person.getYearOfBirthday(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM people WHERE id = ?", id);
    }


}
