package com.oshovskii.otus.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Data
public class Genre {
    private Long id;
    private String type;
    private Set<Book> books;

    public Genre(String type) {
        this.type = type;
    }
}
