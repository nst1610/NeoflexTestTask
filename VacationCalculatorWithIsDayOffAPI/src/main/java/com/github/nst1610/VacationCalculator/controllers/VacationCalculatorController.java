package com.github.nst1610.VacationCalculator.controllers;

import com.github.nst1610.VacationCalculator.dto.VacationPaymentDTO;
import com.github.nst1610.VacationCalculator.models.VacationPayment;
import com.github.nst1610.VacationCalculator.services.VacationPaymentServiceImpl;
import com.github.nst1610.VacationCalculator.utils.ErrorResponse;
import com.github.nst1610.VacationCalculator.utils.ParameterValuesException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;

@RestController
public class VacationCalculatorController {
    private final VacationPaymentServiceImpl vacationPaymentService;
    private final ModelMapper modelMapper;

    @Autowired
    public VacationCalculatorController(VacationPaymentServiceImpl vacationPaymentService, ModelMapper modelMapper) {
        this.vacationPaymentService = vacationPaymentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/calculate")
    public VacationPaymentDTO getSumOfVacationPayment(@RequestParam(name = "averageSalary") double averageSalary,
                                                      @RequestParam(name = "countOfDays") int countOfDays,
                                                      @RequestParam(name = "startDate", required = false) LocalDate startDate) throws IOException {
        if(averageSalary < 0 || countOfDays < 0)
            throw new ParameterValuesException("Количество дней отпуска и средняя зарплата не должны быть отрицательными!");

        if (startDate != null)
            return convertToDTO(vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays, startDate));

        return convertToDTO(vacationPaymentService.calculateVacationPayment(averageSalary, countOfDays));
    }

    private VacationPaymentDTO convertToDTO(VacationPayment vacationPayment){
        return modelMapper.map(vacationPayment, VacationPaymentDTO.class);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(ParameterValuesException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
