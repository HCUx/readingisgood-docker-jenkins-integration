package com.readingisgood.backend.service.Impl;

import com.readingisgood.backend.entity.Book;
import com.readingisgood.backend.entity.dto.BookDto;
import com.readingisgood.backend.repository.BookRepository;
import com.readingisgood.backend.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDto createBook(Book book, Authentication authentication) throws Exception {
        Book newBook = null;
        try {
            newBook = bookRepository.save(book);
        }catch (Exception e){
            throw new Exception("Book Adding Error");
        }
        return modelMapper.map(newBook, BookDto.class);

    }
}
