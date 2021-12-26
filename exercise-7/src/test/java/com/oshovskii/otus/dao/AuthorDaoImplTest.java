package com.oshovskii.otus.dao;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDaoImpl Test")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {

    @Autowired
    private AuthorDaoImpl authorDao;

    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";
    private static final Long EXISTING_AUTHOR_ID = 1L;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    @DisplayName("Return expected author by id")
    @Test
    void getById_validAuthorId_shouldReturnExpectedAuthorById() {
        // Config
        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);

        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);
        expectedAuthor.setBooks(Set.of(expectedBook));

        // Call
        val actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID);

        // Verify
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}
