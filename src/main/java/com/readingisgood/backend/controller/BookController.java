package com.readingisgood.backend.controller;

import com.readingisgood.backend.entity.Book;
import com.readingisgood.backend.entity.dto.BookDto;
import com.readingisgood.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody Book book, Authentication authentication) throws Exception {
        return ResponseEntity.ok().body(this.bookService.createBook(book, authentication));
    }
}
