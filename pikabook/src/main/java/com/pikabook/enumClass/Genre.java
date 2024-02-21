package com.pikabook.enumClass;

public enum Genre {
    romance("로맨스"),
    fantasy("판타지"),
    original("영화드라마원작"),
    insight("인간드라마"),
    universe("실존주의"),
    history("사회비판"),
    nobelprize("노벨상수상자"),
    korean("한국문학");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
