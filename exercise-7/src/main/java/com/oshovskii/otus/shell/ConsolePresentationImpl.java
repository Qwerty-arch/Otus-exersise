package com.oshovskii.otus.shell;

import com.oshovskii.otus.domain.Book;
import com.oshovskii.otus.service.AuthorServiceImpl;
import com.oshovskii.otus.service.BookServiceImpl;
import com.oshovskii.otus.service.GenreServiceImpl;
import com.oshovskii.otus.shell.interfaces.ConsolePresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ConsolePresentationImpl implements ConsolePresentation {
    private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorService;
    private final GenreServiceImpl genreService;
    private String userName;

    @Override
    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @Override
    @ShellMethod(value = "Publish count books command", key = {"count", "c"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public int publishCountBooks() {
        return bookService.countAllBook();
    }

    @Override
    @ShellMethod(value = "Publish book by id", key = {"getById", "get"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishBookByID(Long bookId) {
        return bookService.getBookById(bookId).toString();
    }

    @Override
    @ShellMethod(value = "Publish all books", key = {"allBooks", "all"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAllBook() {
        return bookService.getAllBook().toString();
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"insertBook", "insert", "ins"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String insetBook(Long authorId, Long genreId, String title) {
        Book book = new Book(title);
        bookService.insertBook(book, authorId, genreId);
        String completedCommandInsertBook = "insert book <"+ book.getTitle() + "> completed";
        return completedCommandInsertBook;
    }

    @Override
    @ShellMethod(value = "Delete book by id", key = {"deleteBook", "delete", "del"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteBookByID(Long bookId) {
        bookService.deleteBookById(bookId);
        String completedDeleteByIdInfo = "book with id " + bookId + " deleted";
        return completedDeleteByIdInfo;
    }

    @Override
    @ShellMethod(value = "Publish author by id", key = {"getAuthorById", "getA"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishAuthorByID(Long authorId) {
        return authorService.getAuthorById(authorId).toString();
    }

    @Override
    @ShellMethod(value = "Publish genre by id", key = {"getGenreById", "getG"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String publishGenreByID(Long genreId) {
        return genreService.getGenreById(genreId).toString();
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Сначала залогиньтесь"): Availability.available();
    }
}
