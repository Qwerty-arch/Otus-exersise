package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.BookDaoImpl;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.service.interfaces.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDaoImpl bookDao;

    @Override
    public int countAllBook() {
        return bookDao.count();
    }

    @Override
    public void insertBook(Book book, Long authorId, Long genreId) {
        bookDao.insert(book, authorId, genreId);
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAllBook() {
        return bookDao.getAll();
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }
}
