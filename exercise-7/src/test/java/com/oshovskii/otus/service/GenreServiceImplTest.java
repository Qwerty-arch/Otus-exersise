package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.GenreDaoImpl;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("GenreServiceImpl Test")
@SpringBootTest(classes = GenreServiceImpl.class)
public class GenreServiceImplTest {

    @Autowired
    private GenreServiceImpl genreService;

    @MockBean
    private GenreDaoImpl genreDao;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_TYPE = "Detective";

    @DisplayName("Return expected genre by id")
    @Test
    public void getGenreById_validGenreId_shouldReturnExpectedGenreById(){
        // Config
        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);

        val expectedGenre = new Genre(EXISTING_GENRE_TYPE);
        expectedGenre.setId(EXISTING_GENRE_ID);
        expectedGenre.setBooks(Set.of(expectedBook));

        when(genreDao.getById(EXISTING_GENRE_ID)).thenReturn(expectedGenre);

        // Call
        val actualGenre = genreService.getGenreById(expectedGenre.getId());

        // Verify
        assertEquals(actualGenre, expectedGenre);
    }
}
