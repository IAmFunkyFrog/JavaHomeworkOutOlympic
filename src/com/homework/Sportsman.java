package com.homework;

enum Sex {
    MALE,
    FEMALE
}

enum Season {
    SUMMER,
    WINTER
}

public class Sportsman {
    private final int index;
    private final String name;
    private final Sex sex;
    private final int age; // 0 если рост неизвестен
    private final int height; // 0 если рост неизвестен
    private final double weight; // 0 если вес неизвестен
    private final String team;
    private final String nationalCode;
    private final Season season;
    private final int year; // непонятный параметр, зачем он нужен?

    public Sportsman(int index, String name, Sex sex, int age, int height, double weight, String team, String nationalCode, Season season, int year) {
        this.index = index;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.team = team;
        this.nationalCode = nationalCode;
        this.season = season;
        this.year = year;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getTeam() {
        return team;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public Season getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public double getWeight() {
        return weight;
    }

}
