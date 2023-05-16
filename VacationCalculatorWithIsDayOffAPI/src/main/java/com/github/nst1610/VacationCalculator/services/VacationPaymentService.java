package com.github.nst1610.VacationCalculator.services;


import com.github.nst1610.VacationCalculator.models.VacationPayment;

import java.time.LocalDate;

public interface VacationPaymentService {
    VacationPayment calculateVacationPayment(double averageSalary, int countOfDays);

    VacationPayment calculateVacationPayment(double averageSalary, int countOfDays, LocalDate startDate);
}
