package com.pikabook.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "Book")
@Data
@Entity
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본값

    private String category;
    private String seriesNumber;
    private String title;
    private String authors;
    private String keywords;  //키워드
    private String tag;   //장르
    private String itemNumberYes24;
    private String imageUrlYes24;
    private String itemUrlYes24;
    private String publisher;
    private String publicationDate;
    private String price;
    private String reviewCnt;
    private String star;

    @Column(length = 1000)
    private String description;
    private String type;
    private String translators;
    private String isbnYes24;
    private String isbnMinumsa;
    private String tagOriginal;
    private String descOriginal;
    private String tagPrize;
    private String descPrize;
    private String tagGenre;


    public BookDto toDto() {
        return new BookDto(
                this.isbnYes24,
                this.imageUrlYes24,
                this.title,
                this.authors,
                this.description,
                this.itemUrlYes24
        );
    }

}
