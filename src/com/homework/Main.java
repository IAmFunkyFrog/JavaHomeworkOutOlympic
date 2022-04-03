package com.homework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static String makeTable1(List<Sportsman> sportsmen) {
        TreeMap<String, TreeMap<Integer, Integer>> map = new TreeMap<>();
        for (Sportsman sportsman : sportsmen) {
            if (!map.containsKey(sportsman.getNationalCode())) map.put(sportsman.getNationalCode(), new TreeMap<>());
            TreeMap<Integer, Integer> innerMap = map.get(sportsman.getNationalCode());
            if (!innerMap.containsKey(sportsman.getYear())) innerMap.put(sportsman.getYear(), 1);
            else innerMap.put(sportsman.getYear(), innerMap.get(sportsman.getYear()) + 1);
        }

        TableBuilder tb = new TableBuilder(
                new String[]{"Национальный код", "Год", "Кол-во игроков"}
        );
        for (String nationalCode : map.keySet()) {
            TreeMap<Integer, Integer> innerMap = map.get(nationalCode);
            for (Integer year : innerMap.keySet()) {
                String[] row = new String[]{nationalCode, year.toString(), innerMap.get(year).toString()};
                tb.append(row);
            }
        }

        return tb.toString();
    }

    private static String makeTable2(List<Sportsman> sportsmen) {
        TableBuilder tb = new TableBuilder(
                new String[]{"Команда", "Год", "Средний возраст", "Средний рост", "Средний вес", "Доля мужчин", "Доля женщин"}
        );

        HashMap<String, Set<Integer>> usedTeams = new HashMap<>();
        for (Sportsman sportsman : sportsmen) {
            if (usedTeams.containsKey(sportsman.getTeam()) && usedTeams.get(sportsman.getTeam()).contains(sportsman.getYear()))
                continue;

            if (!usedTeams.containsKey(sportsman.getTeam())) usedTeams.put(sportsman.getTeam(), new HashSet<>());
            usedTeams.get(sportsman.getTeam()).add(sportsman.getYear());

            double ageSum = 0;
            int ageKnown = 0;
            double heightSum = 0;
            int heightKnown = 0;
            double weightSum = 0;
            int weightKnown = 0;
            double male = 0;
            double female = 0;

            for (Sportsman innerSportsman : sportsmen) {
                if (!Objects.equals(innerSportsman.getTeam(), sportsman.getTeam()) || innerSportsman.getYear() != sportsman.getYear())
                    continue;
                if (innerSportsman.getAge() != 0) {
                    ageSum += innerSportsman.getAge();
                    ageKnown++;
                }
                if (innerSportsman.getHeight() != 0) {
                    heightSum += innerSportsman.getHeight();
                    heightKnown++;
                }
                if (innerSportsman.getWeight() != 0) {
                    weightSum += innerSportsman.getWeight();
                    weightKnown++;
                }
                if (innerSportsman.getSex() == Sex.MALE) male++;
                else female++;
            }

            tb.append(
                    new String[]{sportsman.getTeam(), String.valueOf(sportsman.getYear()), String.valueOf(ageKnown == 0 ? "-" : ageSum / ageKnown), String.valueOf(heightKnown == 0 ? "-" : heightSum / heightKnown), String.valueOf(weightKnown == 0 ? "-" : weightSum / weightKnown), String.valueOf(male / (male + female)), String.valueOf(female / (male + female))}
            );
        }

        return tb.toString();
    }

    private static String makeTable3(List<Sportsman> sportsmen) {
        TableBuilder tb = new TableBuilder(
                new String[]{"Год", "Сезон", "Кол-во мужчин", "Кол-во женщин", "Кол-во команд"}
        );

        TreeMap<Integer, TreeMap<Season, Integer[]>> map = new TreeMap<>();
        for (Sportsman sportsman : sportsmen) {
            if (!map.containsKey(sportsman.getYear())) map.put(sportsman.getYear(), new TreeMap<>());
            TreeMap<Season, Integer[]> innerMap = map.get(sportsman.getYear());

            if (!innerMap.containsKey(sportsman.getSeason()))
                innerMap.put(sportsman.getSeason(), new Integer[]{0, 0, 0});
            else continue;

            HashSet<String> usedTeams = new HashSet<>();
            for (Sportsman innerSportsman : sportsmen) {
                if (!Objects.equals(innerSportsman.getSeason(), sportsman.getSeason()) || innerSportsman.getYear() != sportsman.getYear())
                    continue;

                if (innerSportsman.getSex() == Sex.MALE) innerMap.get(sportsman.getSeason())[0]++;
                else innerMap.get(sportsman.getSeason())[1]++;
                usedTeams.add(innerSportsman.getTeam());
            }
            innerMap.get(sportsman.getSeason())[2] = usedTeams.size();
        }

        for (Integer year : map.keySet()) {
            TreeMap<Season, Integer[]> innerMap = map.get(year);
            for (Season season : innerMap.keySet()) {
                tb.append(new String[]{String.valueOf(year), season == Season.SUMMER ? "Summer" : "Winter", String.valueOf(innerMap.get(season)[0]), String.valueOf(innerMap.get(season)[1]), String.valueOf(innerMap.get(season)[2])});
            }
        }

        return tb.toString();
    }

    public static void main(String[] args) {
        ArrayList<Sportsman> sportsmen = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of("data_olimp.txt"))) {
            String line = br.readLine();
            while (line != null) {
                int index = Integer.parseInt(line);
                String name = br.readLine();
                Sex sex = Objects.equals(br.readLine(), "M") ? Sex.MALE : Sex.FEMALE;
                line = br.readLine();
                int age = Objects.equals(line, "NA") ? 0 : Integer.parseInt(line);
                line = br.readLine();
                int height = Objects.equals(line, "NA") ? 0 : Integer.parseInt(line);
                line = br.readLine();
                double weight = Objects.equals(line, "NA") ? 0 : Double.parseDouble(line);
                String team = br.readLine();
                String nationalCode = br.readLine();
                String[] yearAndSeason = br.readLine().split(" ");
                Season season = Objects.equals(yearAndSeason[1], "Summer") ? Season.SUMMER : Season.WINTER;
                int year = Integer.parseInt(yearAndSeason[0]);
                br.readLine(); // пропускаю строку, которая дублирует год из предыдущей

                boolean flag = false;
                for (Sportsman sportsman : sportsmen) {
                    if (sportsman.getIndex() == index) {
                        flag = true;
                        break;
                    }
                }
                if (!flag)
                    sportsmen.add(new Sportsman(index, name, sex, age, height, weight, team, nationalCode, season, year));

                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println("Ошибка во время чтения файла (возможно, файл неправильно форматирован)");
            return;
        }

        String table1 = makeTable1(sportsmen);
        String table2 = makeTable2(sportsmen);
        String table3 = makeTable3(sportsmen);

        System.out.println("Таблица 1");
        System.out.println(table1);
        System.out.println("Таблица 2");
        System.out.println(table2);
        System.out.println("Таблица 3");
        System.out.println(table3);

        String paramName = "--filename=";
        if (args.length >= 1 && args[0].startsWith(paramName)) {
            try (BufferedWriter bw = Files.newBufferedWriter(Path.of(args[0].substring(paramName.length())))) {
                bw.append("Таблица 1\n");
                bw.append(table1);
                bw.append("Таблица 2\n");
                bw.append(table2);
                bw.append("Таблица 3\n");
                bw.append(table3);
            } catch (Exception e) {
                System.err.println("Ошибка во время чтения записи файла");
            }
        }
    }
}
