package com.oshovskii.otus.service.interfaces;

import com.oshovskii.otus.domain.Book;

import java.util.List;

public interface BookService {
    int countAllBook();
    void insertBook(Book book, Long authorId, Long genreId);
    Book getBookById(long id);
    List<Book> getAllBook();
    void deleteBookById(long id);
}
