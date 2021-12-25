package com.oshovskii.otus.shell;

import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.domain.Genre;
import com.oshovskii.otus.service.AuthorServiceImpl;
import com.oshovskii.otus.service.BookServiceImpl;
import com.oshovskii.otus.service.GenreServiceImpl;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Test shell command")
@SpringBootTest
public class ConsolePresentationImplTest {
    @MockBean
    private BookServiceImpl bookServiceImpl;

    @MockBean
    private AuthorServiceImpl authorServiceImpl;

    @MockBean
    private GenreServiceImpl genreServiceImpl;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Добро пожаловать: %s";
    private static final String DEFAULT_LOGIN = "AnyUser";
    private static final String CUSTOM_LOGIN = "John";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOGIN_SHORT = "l";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";

    private static final String COMMAND_PUBLISH_GET_BOOK_BY_ID = "getById 1";
    private static final String COMMAND_PUBLISH_GET_AUTHOR_BY_ID = "getAuthorById 1";
    private static final String COMMAND_PUBLISH_GET_GENRE_BY_ID = "getGenreById 1";

    private static final String COMMAND_PUBLISH_ALL_BOOKS = "allBooks";
    private static final String COMMAND_PUBLISH_COUNT_BOOKS = "count";
    private static final String COMMAND_PUBLISH_INSERT_BOOK = "ins 1 1 TestBookTitle";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID = "deleteBook 1";
    private static final String COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE = "book with id 1 deleted";
    private static final String COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE = "insert book <TestBookTitle> completed";

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final String NEW_BOOK_TITLE = "TestBookTitle";

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_TITLE = "The Da Vinci Code";
    private static final String EXISTING_BOOK_TITLE_2 = "Angels and Demons";

    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "Dan Brown";

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXPECTED_GENRE_NAME = "TestGenre";


    @DisplayName("Should return greeting pattern for all login")
    @Test
    public void login_validLoginCommand_shouldReturnExpectedGreeting() {
        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LOGIN));

        res = (String) shell.evaluate(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, CUSTOM_LOGIN));
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, CUSTOM_LOGIN));
    }

    @DisplayName("Should return CommandNotCurrentlyAvailable if the user logged when trying to execute the test command")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void isPublishEventCommandAvailable_inputVoid_shouldReturnCommandNotCurrentlyAvailableObject() {
        // Config and Call
        Object res =  shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        //  Verify
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

    @DisplayName("Should return all books of the test command execution " +
            "and call service method if the command is executed after logging in")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void publishAllBook_validCommand_shouldReturnExpectedBookList() {
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);

        final Book expectedBook = new Book(EXISTING_BOOK_TITLE);
        final Book expectedBook2 = new Book(EXISTING_BOOK_TITLE_2);
        val expectedBookList = List.of(expectedBook, expectedBook2);

        when(bookServiceImpl.getAllBook()).thenReturn(expectedBookList);

        // Call
        val res = (String) shell.evaluate(() -> COMMAND_PUBLISH_ALL_BOOKS);

        // Verify
        assertThat(res).isEqualTo(expectedBookList.toString());
        verify(bookServiceImpl, times(1)).getAllBook();
    }

    @DisplayName("Should return count of books and call service method if the command is executed after logging in")
    @Test
    public void publishCountBooks_validCommand_shouldReturnBooksCount(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        when(bookServiceImpl.countAllBook()).thenReturn(EXPECTED_BOOKS_COUNT);

        // Call
        val actualCount = (int) shell.evaluate(()-> COMMAND_PUBLISH_COUNT_BOOKS);

        // Verify
        assertThat(actualCount).isEqualTo(EXPECTED_BOOKS_COUNT);
        verify(bookServiceImpl, times(1)).countAllBook();
    }

    @DisplayName("Should return book by id and call service method if the command is executed after logging in")
    @Test
    public void publishBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        val expectedBook = new Book(EXISTING_BOOK_TITLE);

        when(bookServiceImpl.getBookById(EXISTING_BOOK_ID)).thenReturn(expectedBook);

        // Call
        val actualBook = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_BOOK_BY_ID);

        // Verify
        assertThat(actualBook).isEqualTo(expectedBook.toString());
        verify(bookServiceImpl, times(1)).getBookById(EXISTING_BOOK_ID);
    }

    @DisplayName("Insert book in db and call service method if the command is executed after logging in")
    @Test
    public void insetBook_validCommandAndBook_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        Book expectedBook = new Book(NEW_BOOK_TITLE);

        doNothing().when(bookServiceImpl).insertBook(expectedBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);

        // Call
        val actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_INSERT_BOOK);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_INSERT_BOOK_EXPECTED_MESSAGE);
        verify(bookServiceImpl, times(1)).insertBook(expectedBook, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID);
    }

    @DisplayName("Delete book by id and call service method if the command is executed after logging in")
    @Test
    public void deleteBookByID_validCommandAndBookId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        doNothing().when(bookServiceImpl).deleteBookById(EXISTING_BOOK_ID);

        // Call
        final String actualMessage = (String) shell.evaluate(()-> COMMAND_PUBLISH_DELETE_BOOK_BY_ID);

        // Verify
        assertThat(actualMessage).isEqualTo(COMMAND_PUBLISH_DELETE_BOOK_BY_ID_EXPECTED_MESSAGE);
        verify(bookServiceImpl, times(1)).deleteBookById(EXISTING_BOOK_ID);
    }

    @DisplayName("Should return author by id and call service method if the command is executed after logging in")
    @Test
    public void publishAuthorByID_validCommandAndAuthorId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        val expectedAuthor = new Author(EXISTING_AUTHOR_NAME);

        when(authorServiceImpl.getAuthorById(EXISTING_AUTHOR_ID)).thenReturn(expectedAuthor);

        // Call
        val actualAuthor = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_AUTHOR_BY_ID);

        // Verify
        assertThat(actualAuthor).isEqualTo(expectedAuthor.toString());
        verify(authorServiceImpl, times(1)).getAuthorById(EXISTING_AUTHOR_ID);
    }

    @DisplayName("Should return genre by id and call service method if the command is executed after logging in")
    @Test
    public void publishGenreByID_validCommandAndGenreId_shouldReturnExpectedMessage(){
        // Config
        shell.evaluate(() -> COMMAND_LOGIN);
        val expectedGenre = new Genre(EXPECTED_GENRE_NAME);

        when(genreServiceImpl.getGenreById(EXISTING_GENRE_ID)).thenReturn(expectedGenre);

        // Call
        val actualGenre = (String) shell.evaluate(()-> COMMAND_PUBLISH_GET_GENRE_BY_ID);

        // Verify
        assertThat(actualGenre).isEqualTo(expectedGenre.toString());
        verify(genreServiceImpl, times(1)).getGenreById(EXISTING_GENRE_ID);
    }
}
