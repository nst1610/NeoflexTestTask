package com.github.nst1610.VacationCalculator.services;

import com.github.nst1610.VacationCalculator.models.VacationPayment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class VacationPaymentServiceImpl implements VacationPaymentService{
    private static final double AVG_DAYS_IN_MONTH = 29.3;
    private static final double SCALE = 100.0;

    private static final String url = "https://isdayoff.ru/";

    @Override
    public VacationPayment calculateVacationPayment(double averageSalary, int countOfDays) {
        double sumOfPayment = Math.round(averageSalary / AVG_DAYS_IN_MONTH * countOfDays * SCALE) / SCALE;
        return new VacationPayment(sumOfPayment);
    }

    @Override
    public VacationPayment calculateVacationPayment(double averageSalary, int countOfDays, LocalDate startDate) {

        int countOfPayedDays = countOfDays;

        for (int i = 0; i < countOfDays; i++){
            if(startDate.plusDays(i).getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    startDate.plusDays(i).getDayOfWeek().equals(DayOfWeek.SUNDAY) ||
                    isDayOff(startDate.plusDays(i)))
                countOfPayedDays--;
        }

        return calculateVacationPayment(averageSalary, countOfPayedDays);
    }

    private static boolean isDayOff(LocalDate date){
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url + date.toString(), String.class);
        return response.equals("1");
    }
}
