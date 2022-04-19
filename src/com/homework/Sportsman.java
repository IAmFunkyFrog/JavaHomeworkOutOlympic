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
    public static int MAX_AGE = 70; // максимальный возраст, который может быть у спортсмена
    public static int MAX_WEIGHT = 200; // максимальный вес, который может быть у спортсмена
    public static int MAX_HEIGHT = 250; // максимальный рост, который может быть у спортсмена
    private final int index;
    private final String name;
    private final Sex sex;
    private final int age; // 0 если рост неизвестен
    private final int height; // 0 если рост неизвестен
    private final double weight; // 0 если вес неизвестен
    private final String team;
    private final String nationalCode;
    private final Season season;
    private final int year;

    public Sportsman(int index, String name, Sex sex, int age, int height, double weight, String team, String nationalCode, Season season, int year) throws SportsmanException {
        if(index < 0) throw new SportsmanException("index must be positive integer, but given " + index);
        else this.index = index;
        this.name = name;
        this.sex = sex;
        if(age < 0 || age > MAX_AGE) throw new SportsmanException("age must be positive integer and less then " + MAX_AGE + ", but given " + age);
        else this.age = age;
        if(height < 0 || height > MAX_HEIGHT) throw new SportsmanException("height must be positive integer and less then " + MAX_HEIGHT + ", but given " + height);
        else this.height = height;
        if(weight < 0 || weight > MAX_WEIGHT) throw new SportsmanException("weight must be positive integer and less then " + MAX_WEIGHT + ", but given " + weight);
        else this.weight = weight;
        this.team = team;
        this.nationalCode = nationalCode;
        this.season = season;
        if(year < 0) throw new SportsmanException("year must be positive integer, but given " + year);
        else this.year = year;
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

    public static class SportsmanException extends RuntimeException {
        public SportsmanException(String message) {
            super(message);
        }
    }
}
