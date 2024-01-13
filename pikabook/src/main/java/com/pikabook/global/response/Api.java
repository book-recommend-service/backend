package com.pikabook.global.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Api<T> {
    private T data;
    private String resultCode;
    private String resultMessage;
    private String[] Error;
}
