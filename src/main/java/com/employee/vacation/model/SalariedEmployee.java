package com.employee.vacation.model;

/**
 * Salaried Employee: accumulates 15 vacation days per 260-day work year.
 */
public class SalariedEmployee extends Employee {

    private static final double VACATION_DAYS_PER_YEAR = 15.0;

    public SalariedEmployee(String name) {
        super(name, EmployeeType.SALARIED);
    }

    @Override
    protected double getVacationDaysPerYear() {
        return VACATION_DAYS_PER_YEAR;
    }
}
