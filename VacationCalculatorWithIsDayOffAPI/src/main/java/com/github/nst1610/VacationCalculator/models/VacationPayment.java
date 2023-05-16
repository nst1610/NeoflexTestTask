package com.github.nst1610.VacationCalculator.models;

public class VacationPayment {
    private Double sumOfPayment;

    public VacationPayment(Double sumOfPayment){
        this.sumOfPayment = sumOfPayment;
    }

    public Double getSumOfPayment() {
        return sumOfPayment;
    }

    public void setSumOfPayment(Double sumOfPayment) {
        this.sumOfPayment = sumOfPayment;
    }
}
