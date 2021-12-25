package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.GenreDaoImpl;
import com.oshovskii.otus.domain.Genre;
import com.oshovskii.otus.service.interfaces.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDaoImpl genreDao;

    @Override
    public Genre getGenreById(Long id) {
        return genreDao.getById(id);
    }
}
