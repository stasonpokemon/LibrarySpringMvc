package com.spring.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private Long id;

//    @NotEmpty(message = "Full name must be not empty")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotNull(message = "Year of birthday must be not empty")
    @Min(value = 1901, message = "Year of birthday must be greater than 1900")
    private Short yearOfBirthday;

}
