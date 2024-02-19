package com.pikabook;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        try {
            // JSON 파일 경로
            String jsonFilePath = "pikabook/src/main/java/com/pikabook/jsonData.json";

            // ObjectMapper 생성
            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 파일을 읽어와 Java 객체로 매핑
            Book[] books = objectMapper.readValue(new File(jsonFilePath), Book[].class);

            // 매핑된 객체 출력
            for (Book book : books) {
                System.out.println("book = " + book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
