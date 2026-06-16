package com.employee.vacation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class WorkRequest {

    @Min(value = 0, message = "Days worked must be between 0 and 260")
    @Max(value = 260, message = "Days worked must be between 0 and 260")
    private int daysWorked;

    public WorkRequest() {}

    public WorkRequest(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    public int getDaysWorked() { return daysWorked; }
    public void setDaysWorked(int daysWorked) { this.daysWorked = daysWorked; }
}
