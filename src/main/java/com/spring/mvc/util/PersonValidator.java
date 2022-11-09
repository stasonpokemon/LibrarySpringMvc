package com.spring.mvc.util;

import com.spring.mvc.dao.PersonDAO;
import com.spring.mvc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;

@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.findByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "The person with the given name exist");
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (person.getYearOfBirthday() != null) {
            if (person.getYearOfBirthday() > currentYear) {
                errors.rejectValue("yearOfBirthday", "", "getYear of birthday must be less than " + currentYear);
            }
        }
    }
}
