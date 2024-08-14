package com.itvdn.cbs.Security.repositories;

import com.itvdn.cbs.Security.models.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    Optional<Books> findByName(String name);
    @Query("SELECT b, o FROM Books b LEFT JOIN Orders o ON b.id = o.books.id AND o.status = 1")
    List<Object[]> findBooksWithStatus();
    @Query("SELECT b FROM Books b WHERE NOT EXISTS ( SELECT 1 FROM Orders o  WHERE o.books.id = b.id AND o.status = 1)")
    List<Books> findAvailableBooks();
}
