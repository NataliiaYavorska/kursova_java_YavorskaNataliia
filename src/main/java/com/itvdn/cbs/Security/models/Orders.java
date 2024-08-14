package com.itvdn.cbs.Security.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @NotNull(message = "Читач не може бути порожнім")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull(message = "Книга не може бути порожня")
    private Books books;

    @Column(name = "status")
    private int status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "return_date")
    private LocalDateTime return_date;

    // Конструктор за замовчумванням необхідний для Spring
    public Orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public @NotNull(message = "Читач не може бути порожнім") Person getPerson() {
        return person;
    }

    public void setPerson(@NotNull(message = "Читач не може бути порожнім") Person person) {
        this.person = person;
    }

    public @NotNull(message = "Книга не може бути порожня") Books getBooks() {
        return books;
    }

    public void setBooks(@NotNull(message = "Книга не може бути порожня") Books books) {
        this.books = books;
    }

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
    }

    public LocalDateTime getReturnDate() {
        return return_date;
    }

    public void setReturnDate(LocalDateTime return_date) {
        this.return_date = return_date;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", person=" + person +
                ", books=" + books +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", return_date='" + return_date + '\'' +
                '}';
    }
}