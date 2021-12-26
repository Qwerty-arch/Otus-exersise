package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.AuthorDaoImpl;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("AuthorServiceImpl Test")
@SpringBootTest(classes = AuthorServiceImpl.class)
public class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private AuthorDaoImpl authorDao;

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    @DisplayName("Return expected author by id")
    @Test
    public void getAuthorById_validAuthorId_shouldReturnExpectedAuthorById(){
        // Config
        val expectedBook = new Book(EXISTING_BOOK_TITLE);
        expectedBook.setId(EXISTING_BOOK_ID);

        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);
        expectedAuthor.setId(EXISTING_AUTHOR_ID);
        expectedAuthor.setBooks(Set.of(expectedBook));


        when(authorDao.getById(EXISTING_AUTHOR_ID)).thenReturn(expectedAuthor);

        // Call
        val actualAuthor = authorService.getAuthorById(expectedAuthor.getId());

        // Verify
        assertEquals(actualAuthor, expectedAuthor);
    }
}
