package com.pikabook;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BookService bookService;

        public void dbInit1() {
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
    }
}


