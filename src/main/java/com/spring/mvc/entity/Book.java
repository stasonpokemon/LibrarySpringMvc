package com.spring.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@ToString(of = {"id", "name", "author", "releaseYear"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Name must be not empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Author must be not empty")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Release year must be not empty")
    @Min(value = 1901, message = "Release year must be greater than 1900")
    @Column(name = "release_year")
    private Short releaseYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "book_date")
    private Date bookDate;

    @Transient
    private Boolean isOverdue;


}
