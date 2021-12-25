package com.oshovskii.otus.dao;

import com.oshovskii.otus.dao.interfaces.BookDao;
import com.oshovskii.otus.domain.Book;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final ResultSetExtractor<List<Book>> mapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Book.class);
    private static final String SQL_INSERT_BOOK = "INSERT INTO books (title, author_id, genre_id) " +
            "                                      VALUES (:title, :authorId, :genreId)";
    private static final String SQL_GET_BOOK_BY_ID =
            "SELECT b.id as id, b.title,"
            + " a.id as author_id, a.name as author_name, "
            + " g.id as genre_id, g.type as genre_type"
            + " FROM books b"
            + " LEFT OUTER JOIN authors a ON (a.id = b.author_id)"
            + " LEFT OUTER JOIN genres g ON (g.id = b.genre_id)"
            + " WHERE b.id = :id";
    private static final String SQL_GET_ALL_BOOK =
            "SELECT b.id as id, b.title as title,"
            + " a.id as author_id, a.name as author_name, "
            + " g.id as genre_id, g.type as genre_type"
            + " FROM books b"
            + " LEFT JOIN authors a ON (b.author_id = a.id)"
            + " LEFT OUTER JOIN genres g ON (b.genre_id = g.id)";
    private static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM books WHERE id = :id";

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM books ";
        Integer count = namedParameterJdbcOperations.getJdbcOperations().queryForObject(sql, Integer.class);
        return count == null ? 0 : count;
    }

    @Transactional
    @Override
    public void insert(Book book, Long authorId, Long genreId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource bookParameters = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("authorId", authorId)
                .addValue("genreId", genreId);

        namedParameterJdbcOperations.update(SQL_INSERT_BOOK, bookParameters, keyHolder);
    }

    @Override
    public Book getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = namedParameterJdbcOperations.query(SQL_GET_BOOK_BY_ID, params, mapper);
        return Objects.requireNonNull(books).get(books.size() - 1);
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query(SQL_GET_ALL_BOOK, mapper);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(SQL_DELETE_BOOK_BY_ID, params);
    }
}
