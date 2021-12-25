package com.oshovskii.otus.shell.interfaces;

public interface ConsolePresentation {
    String login(String userName);
    int publishCountBooks();
    String publishBookByID(Long bookId);
    String publishAllBook();
    String insetBook(Long authorId, Long genreId, String title);
    String deleteBookByID(Long bookId);

    String publishAuthorByID(Long authorId);
    String publishGenreByID(Long genreId);
}
