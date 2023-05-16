package com.github.nst1610.VacationCalculator.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;


@SpringBootTest
public class VacationPaymentServiceImplTests {

    @Autowired
    private VacationPaymentServiceImpl vacationPaymentService;
    private static double averageSalary;
    private static LocalDate startDate;

    @BeforeAll
    public static void init(){
        averageSalary = 30000;
        startDate = LocalDate.of(2023, 4, 24);
    }

    @Test
    public void shouldCalculateVacationPaymentProperlyWithoutStartDate(){
        int countOfDays = 12;
        double expectedResult = 12286.69;

        Assertions.assertEquals(expectedResult,
                vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays).getSumOfPayment());
    }

    @Test
    public void shouldReturn0WhenCountOfDaysEquals0() throws IOException {
        Assertions.assertEquals(0,
                vacationPaymentService.calculateVacationPayment(averageSalary, 0).getSumOfPayment());

        Assertions.assertEquals(0,
                vacationPaymentService.calculateVacationPayment(averageSalary, 0, startDate).getSumOfPayment());
    }

    @Test
    public void shouldCalculateVacationPaymentProperlyWithStartDateWithoutHolidaysAndWeekends() throws IOException {
        int countOfDays = 5;
        double expectedResult = 5119.45;

        Assertions.assertEquals(expectedResult,
                vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays, startDate).getSumOfPayment());
    }

    @Test
    public void shouldCalculateVacationPaymentProperlyWithStartDateWithWeekendsAndHolidays() throws IOException {
        int countOfDays = 12;
        double expectedResult = 9215.02;

        Assertions.assertEquals(expectedResult,
                vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays, startDate).getSumOfPayment());
    }
}
