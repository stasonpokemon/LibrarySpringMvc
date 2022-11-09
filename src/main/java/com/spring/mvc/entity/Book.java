package com.spring.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;

    @NotEmpty(message = "Name must be not empty")
    private String name;

    @NotEmpty(message = "Author must be not empty")
    private String author;

    @NotNull(message = "Release year must be not empty")
    @Min(value = 1901, message = "Release year must be greater than 1900")
    private Short releaseYear;

    private Long personId;

}
