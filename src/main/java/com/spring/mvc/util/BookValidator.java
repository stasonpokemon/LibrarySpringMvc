package com.spring.mvc.util;

import com.spring.mvc.entity.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getReleaseYear() != null) {
            if (book.getReleaseYear() > currentYear) {
                errors.rejectValue("releaseYear", "", "Release year must be less than " + currentYear);
            }
        }
    }
}
