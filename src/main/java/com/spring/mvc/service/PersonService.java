package com.spring.mvc.service;

import com.spring.mvc.entity.Person;
import com.spring.mvc.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);/*orElseThrow(() -> new NullPointerException(new StringBuilder()
                .append("Person with id = ")
                .append(id)
                .append(" not found").toString()));*/
    }

    public Optional<Person> findByFullName(String fullName) {
        return personRepository.findByFullName(fullName);
    }


    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(Long id, Person personFromForm) {
        Person personFromDb = findById(id);
        personFromDb.setFullName(personFromForm.getFullName());
        personFromDb.setYearOfBirthday(personFromForm.getYearOfBirthday());
        personRepository.save(personFromDb);

    }

    @Transactional
    public void delete(Long id) {
        Person person = findById(id);
        personRepository.delete(person);
    }


}
