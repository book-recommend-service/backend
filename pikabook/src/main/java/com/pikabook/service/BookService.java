package com.pikabook.service;

import com.pikabook.entity.Book;
import com.pikabook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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

    public List<Book> findByGenre(String genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        return books;
    }

    public List<Book> findByKeywordAndGenre(String keyword, String genre) {
        List<Book> books = bookRepository.findByKeywordAndGenre(keyword, genre);
        return books;
    }
}
