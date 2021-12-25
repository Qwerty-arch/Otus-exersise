package com.oshovskii.otus.dao;

import com.oshovskii.otus.dao.interfaces.GenreDao;
import com.oshovskii.otus.domain.Genre;
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
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final ResultSetExtractor<List<Genre>> mapper =
            JdbcTemplateMapperFactory
                    .newInstance()
                    .addKeys("id")
                    .newResultSetExtractor(Genre.class);

    private static final String SQL_GET_GENRE_BY_ID = "SELECT g.id as id, g.type,"
            + " b.id as book_id, b.title as book_title, "
            + " FROM genres g"
            + " LEFT OUTER JOIN books b ON g.id = b.genre_id"
            + " WHERE g.id = :id";

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Genre> genres = namedParameterJdbcOperations.query(SQL_GET_GENRE_BY_ID, params, mapper);
        return Objects.requireNonNull(genres).get(genres.size() - 1);
    }
}
