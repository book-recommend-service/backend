package com.pikabook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pikabook.entity.Book;
import com.pikabook.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest

class BookServiceTest {

    @Autowired
    private BookService bookService;

//    @BeforeEach
    void setUp() {
        Book[] books = new Book[1000];
        try {
            // JSON 파일 경로
            String jsonFilePath = "pikabook/src/main/java/com/pikabook/jsonData.json";

            // ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 파일을 읽어와 Java 객체로 매핑
            books = objectMapper.readValue(new File(jsonFilePath), Book[].class);

            // 매핑된 객체 출력
            for (Book book : books) {
//                System.out.println("book = " + book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookService.saveAll(books);
    }

    @Test
    void save() {
    }

    @Test
    void saveAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByIsbn() {
    }

    @Test
    void findByGenre() {
    }

    @Test
    void findByKeyword() {

        //given
        String keyword = "신화";

        //when
//        List<Book> books = bookService.findByKeywordAndGenre(keyword);
//
//        System.out.println("books = " + books);

        //then


    }
}