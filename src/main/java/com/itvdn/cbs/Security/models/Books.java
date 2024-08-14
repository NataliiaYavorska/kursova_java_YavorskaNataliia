package com.itvdn.cbs.Security.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "Books")
public class Books {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Назва книги не повинно бути порожнім")
    @Size(min = 2, max = 100, message = "Назва повинно бути від 2 до 100 символів довжиною")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Ім'я автора не повинно бути порожнім")
    @Size(min = 2, max = 100, message = "Ім'я автора повинно бути від 2 до 100 символів довжиною")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Рік книги не повинно бути порожнім")
    @Min(value = 1000, message = "Рік книги повинен бути більшим або рівним 1000")
    @Max(value = 2024, message = "Рік книги повинен бути меншим або рівним 2024")
    @Column(name = "year_release")
    private int yearRelease;

    // Конструктор за замовчумванням необхідний для Spring
    public Books() {
    }

    public Books(String name, String author, int yearRelease) {
        this.name = name;
        this.author = author;
        this.yearRelease = yearRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty(message = "Назва книги не повинно бути порожнім") @Size(min = 2, max = 100, message = "Назва повинно бути від 2 до 100 символів довжиною") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Назва книги не повинно бути порожнім") @Size(min = 2, max = 100, message = "Назва повинно бути від 2 до 100 символів довжиною") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Ім'я автора не повинно бути порожнім") @Size(min = 2, max = 100, message = "Ім'я автора повинно бути від 2 до 100 символів довжиною") String getAuthor() {
        return author;
    }

    public void setAuthor(@NotEmpty(message = "Ім'я автора не повинно бути порожнім") @Size(min = 2, max = 100, message = "Ім'я автора повинно бути від 2 до 100 символів довжиною") String author) {
        this.author = author;
    }

    @NotNull(message = "Рік книги не повинно бути порожнім")
    @Min(value = 1000, message = "Рік книги повинен бути більшим або рівним 1000")
    @Max(value = 9999, message = "Рік книги повинен бути меншим або рівним 9999")
    public int getYearRelease() {
        return yearRelease;
    }

    public void setYearRelease(@NotNull(message = "Рік книги не повинно бути порожнім") @Min(value = 1000, message = "Рік книги повинен бути більшим або рівним 1000") @Max(value = 2024, message = "Рік книги повинен бути меншим або рівним 2024") int yearRelease) {
        this.yearRelease = yearRelease;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearRelease=" + yearRelease +
                '}';
    }
}