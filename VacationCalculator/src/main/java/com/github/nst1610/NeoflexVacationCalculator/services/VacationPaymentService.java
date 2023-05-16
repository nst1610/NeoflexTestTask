package com.github.nst1610.NeoflexVacationCalculator.services;

import com.github.nst1610.NeoflexVacationCalculator.models.VacationPayment;

import java.io.IOException;
import java.time.LocalDate;

public interface VacationPaymentService {
    VacationPayment calculateVacationPayment(double averageSalary, int countOfDays);

    VacationPayment calculateVacationPayment(double averageSalary, int countOfDays, LocalDate startDate) throws IOException;
}
