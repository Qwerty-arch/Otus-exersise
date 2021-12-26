package com.oshovskii.otus.dao;

import com.oshovskii.otus.dao.interfaces.AuthorDao;
import com.oshovskii.otus.domain.Author;
import lombok.RequiredArgsConstructor;
import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final ResultSetExtractor<List<Author>> mapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Author.class);

    private static final String SQL_GET_AUTHOR_BY_ID = "SELECT a.id as id, a.name,"
            + " b.id as book_id, b.title as book_title, "
            + " FROM authors a"
            + " LEFT OUTER JOIN books b ON a.id = b.author_id"
            + " WHERE a.id = :id";

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Author> authors = namedParameterJdbcOperations.query(SQL_GET_AUTHOR_BY_ID, params, mapper);
        return Objects.requireNonNull(authors).get(authors.size() - 1);
    }
}
