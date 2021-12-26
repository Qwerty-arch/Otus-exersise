package com.oshovskii.otus.service;

import com.oshovskii.otus.dao.AuthorDaoImpl;
import com.oshovskii.otus.domain.Author;
import com.oshovskii.otus.service.interfaces.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDaoImpl authorDao;

    @Override
    public Author getAuthorById(Long id) {
        return authorDao.getById(id);
    }
}
