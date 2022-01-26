package com.oshovskii.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class FileCsv {
    private final String qwestion;
    private final String rightAnswer;
    private final String incorrectAnswer;
}
