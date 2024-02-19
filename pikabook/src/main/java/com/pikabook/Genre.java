package com.pikabook;

public enum Genre {
    ROMANCE("로맨스"),
    FANTASY("판타지"),
    MOVIEDRAMA("영화드라마원작"),
    HUMAN_DRAMA("인간드라마"),
    EXISTENTIAL("실존주의"),
    SOCIAL_CRITICISM("사회비판"),
    NOBEL_PRIZE("노벨상수상자");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
