package com.readingisgood.backend.service;

import com.readingisgood.backend.entity.Book;
import com.readingisgood.backend.entity.dto.BookDto;
import org.springframework.security.core.Authentication;

public interface BookService {
    BookDto createBook(Book book, Authentication authentication) throws Exception;
}
