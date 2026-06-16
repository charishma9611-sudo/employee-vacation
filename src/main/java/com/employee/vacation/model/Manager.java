package com.employee.vacation.model;

/**
 * Manager: also a salaried employee, but accumulates 30 vacation days per 260-day work year.
 */
public class Manager extends Employee {

    private static final double VACATION_DAYS_PER_YEAR = 30.0;

    public Manager(String name) {
        super(name, EmployeeType.MANAGER);
    }

    @Override
    protected double getVacationDaysPerYear() {
        return VACATION_DAYS_PER_YEAR;
    }
}
