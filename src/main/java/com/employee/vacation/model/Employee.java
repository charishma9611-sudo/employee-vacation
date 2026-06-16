package com.employee.vacation.model;

/**
 * Abstract base class representing an Employee.
 * Vacation days are read-only externally, initialized to 0, and cannot go negative.
 */
public abstract class Employee {

    private static int idCounter = 1;

    private final int id;
    private final String name;
    private final EmployeeType type;

    // Cannot be negative, not writable externally, starts at 0
    private double vacationDaysAccumulated;

    // Total days worked in this work year
    private int totalDaysWorked;

    // Constants
    public static final int WORK_YEAR_DAYS = 260;

    protected Employee(String name, EmployeeType type) {
        this.id = idCounter++;
        this.name = name;
        this.type = type;
        this.vacationDaysAccumulated = 0.0;
        this.totalDaysWorked = 0;
    }

    /**
     * Returns vacation days accumulated per work year for this employee type.
     */
    protected abstract double getVacationDaysPerYear();

    /**
     * Work the given number of days and accumulate vacation accordingly.
     *
     * @param daysWorked number of days to work (must be 0–260)
     * @throws IllegalArgumentException if daysWorked < 0, > 260, or exceeds remaining workdays
     */
    public void work(int daysWorked) {
        if (daysWorked < 0) {
            throw new IllegalArgumentException("Days worked cannot be negative.");
        }
        if (daysWorked > WORK_YEAR_DAYS) {
            throw new IllegalArgumentException(
                "Days worked cannot exceed the work year limit of " + WORK_YEAR_DAYS + " days.");
        }

        int remainingWorkdays = WORK_YEAR_DAYS - totalDaysWorked;
        if (daysWorked > remainingWorkdays) {
            throw new IllegalArgumentException(
                "Employee cannot work more than " + "260"
                + " days this work year (already worked " + totalDaysWorked + " days).");
        }

        // Accumulate vacation proportionally to days worked
        double vacationEarned = (daysWorked / (double) WORK_YEAR_DAYS) * getVacationDaysPerYear();
        this.vacationDaysAccumulated += vacationEarned;
        this.totalDaysWorked += daysWorked;
    }

    /**
     * Use the given number of vacation days.
     *
     * @param days number of vacation days to take (must be > 0 and <= accumulated days)
     * @throws IllegalArgumentException if days <= 0 or exceeds available vacation
     */
    public void takeVacation(double days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Vacation days to take must be greater than zero.");
        }
        if (days > vacationDaysAccumulated) {
            throw new IllegalArgumentException(
                "Cannot take " + days + " vacation days. Only "
                + String.format("%.4f", vacationDaysAccumulated) + " days available.");
        }
        this.vacationDaysAccumulated -= days;

        // Guard against floating-point underflow
        if (this.vacationDaysAccumulated < 0) {
            this.vacationDaysAccumulated = 0.0;
        }
    }

    // ---- Getters (vacationDaysAccumulated is read-only externally) ----

    public int getId() { return id; }

    public String getName() { return name; }

    public EmployeeType getType() { return type; }

    public double getVacationDaysAccumulated() { return vacationDaysAccumulated; }

    public int getTotalDaysWorked() { return totalDaysWorked; }

    public int getRemainingWorkdays() { return WORK_YEAR_DAYS - totalDaysWorked; }
}
