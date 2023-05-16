package com.github.nst1610.NeoflexVacationCalculator.models;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Component
public class WeekendsAndHolidaysDates {
    private static Map<Month, Set<Integer>> holidays;
    private static final String FILE_PATH = "src/main/resources/static/datesOfHolidays.txt";

    public static boolean isWeekend(LocalDate date){
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY) ?
                true : false;
    }

    public static boolean isHoliday(LocalDate date) throws IOException {
        if(holidays == null)
            initialize();

        if(holidays.containsKey(date.getMonth()) && holidays.get(date.getMonth()).contains(date.getDayOfMonth()))
            return true;

        return false;
    }

    private static void initialize() throws IOException {
        holidays = new HashMap<>();

        List<String> allDates = Files.readAllLines(Path.of(FILE_PATH));
        for (String d :allDates){
            String[] dateArr = d.split("-");

            if(holidays.containsKey(Month.of(Integer.parseInt(dateArr[0]))))
                holidays.get(Month.of(Integer.parseInt(dateArr[0]))).add(Integer.parseInt(dateArr[1]));

            else
                holidays.put(Month.of(Integer.parseInt(dateArr[0])), new HashSet<>(Arrays.asList(Integer.parseInt(dateArr[1]))));
        }
    }
}
