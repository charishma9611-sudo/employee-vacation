package com.employee.vacation.dto;

import jakarta.validation.constraints.Positive;

public class VacationRequest {

    @Positive(message = "Vacation days must be greater than zero")
    private double days;

    public VacationRequest() {}

    public VacationRequest(double days) {
        this.days = days;
    }

    public double getDays() { return days; }
    public void setDays(double days) { this.days = days; }
}
