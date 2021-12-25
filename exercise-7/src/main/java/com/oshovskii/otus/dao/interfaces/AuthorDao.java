package com.oshovskii.otus.dao.interfaces;

import com.oshovskii.otus.domain.Author;

public interface AuthorDao {
    Author getById(Long id);
}
