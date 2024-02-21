package com.pikabook.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//response Dto
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String isbn;
    private String imageUrlYes24;
    private String title;
    private String authors;
    private String description;
    private String itemUrlYes24;




}
