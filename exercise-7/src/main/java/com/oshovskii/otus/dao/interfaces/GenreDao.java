package com.oshovskii.otus.dao.interfaces;

import com.oshovskii.otus.domain.Genre;

public interface GenreDao {
    Genre getById(Long id);
}
