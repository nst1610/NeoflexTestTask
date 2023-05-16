package com.github.nst1610.NeoflexVacationCalculator.services;

import com.github.nst1610.NeoflexVacationCalculator.models.VacationPayment;
import com.github.nst1610.NeoflexVacationCalculator.models.WeekendsAndHolidaysDates;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class VacationPaymentServiceImpl implements VacationPaymentService {
    private static final double AVG_DAYS_IN_MONTH = 29.3;
    private static final double SCALE = 100.0;
    private final WeekendsAndHolidaysDates unpaidDates;

    public VacationPaymentServiceImpl(WeekendsAndHolidaysDates unpaidDates) {
        this.unpaidDates = unpaidDates;
    }

    @Override
    public VacationPayment calculateVacationPayment(double averageSalary, int countOfDays) {
        double sumOfPayment = Math.round(averageSalary / AVG_DAYS_IN_MONTH * countOfDays * SCALE) / SCALE;
        return new VacationPayment(sumOfPayment);
    }

    @Override
    public VacationPayment calculateVacationPayment(double averageSalary, int countOfDays, LocalDate startDate) throws IOException {
        int countOfPayedDays = countOfDays;
        for (int i = 0; i < countOfDays; i++){
            if(WeekendsAndHolidaysDates.isWeekend(startDate.plusDays(i)) ||
                    WeekendsAndHolidaysDates.isHoliday(startDate.plusDays(i)))
                countOfPayedDays--;
        }

        return calculateVacationPayment(averageSalary, countOfPayedDays);
    }
}
