package com.pikabook.repository;

import com.pikabook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnYes24(String isbn);

    @Query("select b from Book b where b.tag LIKE %:genre%")
    List<Book> findByTag(@Param("genre") String genre);

    @Query("select b from Book b where b.keywords LIKE %:keyword%")
    List<Book> findByKeyword(@Param("keyword") String keyword);
}