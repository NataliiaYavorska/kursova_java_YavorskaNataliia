package com.itvdn.cbs.Security.services;

import com.itvdn.cbs.Security.models.Books;
import com.itvdn.cbs.Security.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional
    public void register(Books book) {
        booksRepository.save(book);
    }
}
