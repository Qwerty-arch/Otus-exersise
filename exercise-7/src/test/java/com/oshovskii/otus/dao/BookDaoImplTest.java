package com.oshovskii.otus.dao;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BookDaoImpl Test")
@JdbcTest
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

    @Autowired
    private BookDaoImpl bookDao;

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_BOOK_ID_2 = 2L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long SAVE_BOOK_ID = 3L;
    private static final String SAVE_BOOK_TITLE = "The Da Vinci Code";

    private static final String EXISTING_GENRE_TYPE = "Detective";
    private static final String EXISTING_GENRE_TYPE_2 = "Roman";
    private static final Long EXISTING_GENRE_ID = 1L;
    private static final Long EXISTING_GENRE_ID_2 = 2L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final String EXISTING_AUTHOR_NAME_2 = "Dan Brow";
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID_2 = 2L;


    @DisplayName("Return expected count books in db")
    @Test
    void count_voidInput_shouldReturnExpectedBookCount() {
        // Call
        val actualPersonsCount = bookDao.count();

        // Verify
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("Return expected book by id")
    @Test
    void getById_validBookId_shouldReturnExpectedBookById() {
        // Config
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);

        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setAuthor(expectedAuthor);
        expectedBook.setGenre(expectedGenre);

        // Call
        val actualBook = bookDao.getById(EXISTING_BOOK_ID);

        // Verify
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Add book in db")
    @Test
    void insert_validBookAndAuthorAndGenre_shouldInsertBook() {
        // Config
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);

        val expectedBook = new Book(SAVE_BOOK_TITLE);
        expectedBook.setId(SAVE_BOOK_ID);
        expectedBook.setAuthor(expectedAuthor);
        expectedBook.setGenre(expectedGenre);

        // Call
        bookDao.insert(expectedBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);

        // Verify
        Book actualBook = bookDao.getById(SAVE_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete book by id")
    @Test
    void deleteById_validId_shouldCorrectDeleteBookById() {
        // Config
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        // Call
        bookDao.deleteById(EXISTING_BOOK_ID);

        // Verify
        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @DisplayName("Return expected list books")
    @Test
    void getAll_voidInput_shouldReturnExpectedBooksList() {
        // Verify
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

        // Call
        val actualBookList = bookDao.getAll();

        // Verify
        assertThat(actualBookList)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook, expectedBook2);
    }
}
