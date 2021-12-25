package com.oshovskii.otus.dao;

import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GenreDaoImpl Test")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl genreDao;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_TYPE = "Detective";


    @DisplayName("Return expected genre by id")
    @Test
    void getById_validGenreId_shouldReturnExpectedGenreById() {
        // Config
        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setBooks(Set.of(expectedBook));

        // Call
        val actualGenre = genreDao.getById(EXISTING_GENRE_ID);

        // Verify
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}
