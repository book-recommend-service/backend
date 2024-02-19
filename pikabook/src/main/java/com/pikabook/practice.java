package com.pikabook;

import lombok.ToString;

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

    }
}
