package com.employee.vacation.dto;

import com.employee.vacation.model.EmployeeType;

/**
 * Response DTO for Employee data.
 */
public class EmployeeResponse {

    private int id;
    private String name;
    private EmployeeType type;
    private double vacationDaysAccumulated;
    private int totalDaysWorked;
    private int remainingWorkdays;
    private double vacationDaysPerYear;

    public EmployeeResponse() {}

    public EmployeeResponse(int id, String name, EmployeeType type,
                            double vacationDaysAccumulated, int totalDaysWorked,
                            int remainingWorkdays, double vacationDaysPerYear) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.vacationDaysAccumulated = vacationDaysAccumulated;
        this.totalDaysWorked = totalDaysWorked;
        this.remainingWorkdays = remainingWorkdays;
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public EmployeeType getType() { return type; }
    public void setType(EmployeeType type) { this.type = type; }

    public double getVacationDaysAccumulated() { return vacationDaysAccumulated; }
    public void setVacationDaysAccumulated(double vacationDaysAccumulated) {
        this.vacationDaysAccumulated = vacationDaysAccumulated;
    }

    public int getTotalDaysWorked() { return totalDaysWorked; }
    public void setTotalDaysWorked(int totalDaysWorked) { this.totalDaysWorked = totalDaysWorked; }

    public int getRemainingWorkdays() { return remainingWorkdays; }
    public void setRemainingWorkdays(int remainingWorkdays) { this.remainingWorkdays = remainingWorkdays; }

    public double getVacationDaysPerYear() { return vacationDaysPerYear; }
    public void setVacationDaysPerYear(double vacationDaysPerYear) { this.vacationDaysPerYear = vacationDaysPerYear; }
}
