package com.pikabook.service;

import com.pikabook.entity.Book;
import com.pikabook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }


    @Transactional
    public  void saveAll(Book[] books) {
        bookRepository.saveAll(Arrays.asList(books));
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }


    public Book findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnYes24(isbn).orElse(null);
        return book;
    }

    public List<Book> findByGenre(String tag) {
        List<Book> books = bookRepository.findByTag(tag);
        return books;
    }

    public List<Book> findByKeyword(String keyword) {
        List<Book> books = bookRepository.findByKeyword(keyword);
        return books;
    }
}
