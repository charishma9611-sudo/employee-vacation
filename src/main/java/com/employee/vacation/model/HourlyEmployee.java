package com.employee.vacation.model;

/**
 * Hourly Employee: accumulates 10 vacation days per 260-day work year.
 */
public class HourlyEmployee extends Employee {

    private static final double VACATION_DAYS_PER_YEAR = 10.0;

    public HourlyEmployee(String name) {
        super(name, EmployeeType.HOURLY);
    }

    @Override
    protected double getVacationDaysPerYear() {
        return VACATION_DAYS_PER_YEAR;
    }
}
