package com.oshovskii.otus.dao.interfaces;

import com.oshovskii.otus.domain.Book;

import java.util.List;

public interface BookDao {
    int count();
    void insert(Book book, Long authorId, Long genreId);
    Book getById(Long id);
    List<Book> getAll();
    void deleteById(Long id);
}
