package com.github.nst1610.NeoflexVacationCalculator.controllers;

import com.github.nst1610.NeoflexVacationCalculator.models.VacationPayment;
import com.github.nst1610.NeoflexVacationCalculator.services.VacationPaymentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class VacationCalculatorControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  VacationPaymentServiceImpl vacationPaymentService;

    private static VacationPayment vacationPayment;
    private static double averageSalary;
    private static int countOfDays;
    private static LocalDate startDate;

    @BeforeAll
    public static void init(){
        averageSalary = 30000;
        countOfDays = 12;
        startDate = LocalDate.of(2023, 4, 24);
        vacationPayment = new VacationPayment(12286.69);
    }

    @Test
    public void getSumOfVacationWithoutStartDateShouldReturnVacationPayment() throws Exception{
        when(vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays)).thenReturn(vacationPayment);
        mockMvc.perform(get("/calculate")
                .param("averageSalary", String.valueOf(averageSalary))
                .param("countOfDays", String.valueOf(countOfDays)))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.sumOfPayment").value(vacationPayment.getSumOfPayment())
                );
    }

    @Test
    public void getSumOfVacationWithStartDateShouldReturnVacationPayment() throws Exception{
        when(vacationPaymentService.calculateVacationPayment(averageSalary,
                countOfDays, startDate)).thenReturn(vacationPayment);

        mockMvc.perform(get("/calculate")
                        .param("averageSalary", String.valueOf(averageSalary))
                        .param("countOfDays", String.valueOf(countOfDays))
                        .param("startDate", startDate.toString()))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.sumOfPayment").value(vacationPayment.getSumOfPayment())
                );
    }

    @Test
    public void getSumOfVacationShouldReturnZeroIfParametersValueInvalid() throws Exception{
        mockMvc.perform(get("/calculate")
                        .param("averageSalary", String.valueOf(-5))
                        .param("countOfDays", String.valueOf(-5))
                        .param("startDate", startDate.toString()))
                .andExpectAll(
                        status().is4xxClientError()
                );
    }
}
