package com.pikabook;

import com.pikabook.entity.Book;
import com.pikabook.entity.BookDto;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@ToString
public class practice {
    public static void main(String[] args) {
//        System.out.println(SearchType.isbn);
//        System.out.println("isbn"==SearchType.isbn.toString());
//
//        String label = Genre.EXISTENTIAL.getLabel();
//        System.out.println("label = " + label);


        String a = "['사랑과 결핍', '인간 관계', '단절과 소통', '이민자의 시선', '자아 정체성', '담담한 서술']";
        String s1 = a.replaceAll("[\\[\\]']", "");
        String[] split = s1.split(", ");

        for (String s : split) {
            System.out.println("s = " + s);
        }

        Book book = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        book.setDescription("사랑");
        book2.setDescription("고독");
        book3.setDescription("히잉");
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book2);
        books.add(book3);

        List<BookDto> bookDtos = books.stream()
                .map(b -> b.toDto())
                .collect(Collectors.toList());

        System.out.println("bookDtos = " + bookDtos);


        HashMap<String, Integer> rank = new HashMap<>();
        rank.put("a", 5);
        rank.put("b", 8);
        rank.put("c", 3);
        rank.put("d", 5);

        Integer i1 = rank.get("ee");
        System.out.println("i1 = " + i1);
        if (i1 == null) {
            System.out.println("true");
        }
        Integer i = rank.get("a");
        System.out.println("i = " + i);


        String keyword = "사랑";
        keyword ="\'"+keyword+"\'";

        System.out.println("keyword = " + keyword);

        List<String> keySet = new ArrayList<>(rank.keySet());

        keySet.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return rank.get(o1).compareTo(rank.get(o2));
            }
        });

        for (String key : keySet) {
            System.out.print("Key : " + key);
            System.out.println(", Val : " + rank.get(key));
        }


        String oriKeyword = "사실주의";
        String keywords = "[스페인 내전, 사실주의, 정의와 평등, 개인적 경험, 역사적 사실, 실천적 소명]";
        keywords = keywords.replaceAll("[\\[\\]']", "");
        String[] keywordArray = keywords.split(",");

        int index=0;
        for (int j = 0; i < keywordArray.length; j++) {
            System.out.println("keywordArray[j].trim() = " + keywordArray[j].trim());
            System.out.println("oriKeyword = " + oriKeyword);
            if (keywordArray[j].trim().equals(oriKeyword)) {
                index=j;
                break;
            }

        }


        System.out.println("index = " + index);


    }
}
