package com.spring.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
@ToString(of = {"id", "fullName", "yearOfBirthday"})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @NotEmpty(message = "Full name must be not empty")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Column(name = "full_name")
    private String fullName;

    @NotNull(message = "Year of birthday must be not empty")
    @Min(value = 1901, message = "Year of birthday must be greater than 1900")
    @Column(name = "year_of_birthday")
    private Short yearOfBirthday;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Book> books;

}
