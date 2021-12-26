package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.BookDaoImpl;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl Test")
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private BookDaoImpl bookDao;

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";
    private static final String EXPECTED_BOOK_TITLE = "Test title";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brown";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";

    @DisplayName("Return expected count books in db")
    @Test
    public void countAllBook_voidInput_shouldReturnExpectedBookCount(){
        // Config
        when(bookDao.count()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        val actualBookCount = bookService.countAllBook();

        // Verify
        assertEquals(actualBookCount, EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Add book in db")
    @Test
    public void insert_validBook_shouldInsertBook(){
        // Config
        val saveBook = new Book(EXPECTED_BOOK_TITLE);
        doNothing().when(bookDao).insert(saveBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);

        // Call
        bookService.insertBook(saveBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);

        // Verify
        verify(bookDao, times(1)).insert(saveBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
    }

    @DisplayName("Return expected book by id")
    @Test
    public void getBookById_validBookId_shouldReturnExpectedBookById(){
        // Config
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE_2);
        expectedGenre.setId(EXISTING_GENRE_ID);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthor(expectedAuthor);
        expectedBook.setGenre(expectedGenre);

        when(bookDao.getById(EXISTING_BOOK_ID)).thenReturn(expectedBook);

        // Call
        val actualBook = bookService.getBookById(expectedBook.getId());

        // Verify
        assertEquals(actualBook, expectedBook);
    }

    @DisplayName("Return expected list books")
    @Test
    public void getAllBook_voidInput_shouldReturnExpectedBooksList(){
        // Config
        // create 1 book
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthor(expectedAuthor);
        expectedBook.setGenre(expectedGenre);

        // create 2 book
        val expectedAuthor2 = new Author(EXISTING_AUTHOR_NAME_2);
        expectedAuthor2.setId(EXISTING_AUTHOR_ID_2);

        val expectedGenre2 = new Genre(EXISTING_GENRE_TYPE_2);
        expectedGenre2.setId(EXISTING_GENRE_ID_2);

        val expectedBook2 = new Book(EXISTING_BOOK_TITLE_2);
        expectedBook2.setId(EXISTING_BOOK_ID_2);
        expectedBook2.setAuthor(expectedAuthor2);
        expectedBook2.setGenre(expectedGenre2);

        List<Book> expectedBookList = List.of(expectedBook, expectedBook2);
        when(bookDao.getAll()).thenReturn(expectedBookList);

        // Call
        final List<Book> actualBookList = bookService.getAllBook();

        // Verify
        assertEquals(actualBookList, expectedBookList);
    }

    @DisplayName("Delete book by id")
    @Test
    public void deleteBookById_validId_shouldCorrectDeleteBookById(){
        // Config
        doNothing().when(bookDao).deleteById(EXISTING_BOOK_ID);

        // Call
        bookService.deleteBookById(EXISTING_BOOK_ID);

        // Verify
        verify(bookDao, times(1)).deleteById(EXISTING_BOOK_ID);
    }
}
