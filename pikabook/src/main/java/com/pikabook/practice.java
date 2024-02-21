package com.pikabook;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
    }
}
