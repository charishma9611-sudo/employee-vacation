package com.employee.vacation;

import com.employee.vacation.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private HourlyEmployee hourly;
    private SalariedEmployee salaried;
    private Manager manager;

    @BeforeEach
    void setup() {
        hourly   = new HourlyEmployee("Test Hourly");
        salaried = new SalariedEmployee("Test Salaried");
        manager  = new Manager("Test Manager");
    }

    // ═══════════════════════════════════════════
    // Initial State
    // ═══════════════════════════════════════════

    @Test
    void vacationStartsAtZero() {
        assertEquals(0.0, hourly.getVacationDaysAccumulated());
        assertEquals(0.0, salaried.getVacationDaysAccumulated());
        assertEquals(0.0, manager.getVacationDaysAccumulated());
    }

    // ═══════════════════════════════════════════
    // Vacation Accumulation — Full Year
    // ═══════════════════════════════════════════

    @Test
    void hourlyAccumulates10DaysForFullYear() {
        hourly.work(260);
        assertEquals(10.0, hourly.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void salariedAccumulates15DaysForFullYear() {
        salaried.work(260);
        assertEquals(15.0, salaried.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void managerAccumulates30DaysForFullYear() {
        manager.work(260);
        assertEquals(30.0, manager.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void partialWorkAccumulatesProportionally() {
        hourly.work(130); // half a year = 5 days
        assertEquals(5.0, hourly.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void workingZeroDaysAccumulatesZeroVacation() {
        hourly.work(0);
        assertEquals(0.0, hourly.getVacationDaysAccumulated());
    }

    // ═══════════════════════════════════════════
    // Work() — Edge Cases
    // ═══════════════════════════════════════════

    @Test
    void cannotWorkNegativeDays() {
        assertThrows(IllegalArgumentException.class, () -> hourly.work(-1));
    }

    @Test
    void cannotWorkMoreThan260Days() {
        assertThrows(IllegalArgumentException.class, () -> hourly.work(261));
    }

    @Test
    void cannotExceedWorkYearTotalAcrossMultipleCalls() {
        hourly.work(200);
        assertThrows(IllegalArgumentException.class, () -> hourly.work(61)); // only 60 remain
    }

    @Test
    void canWorkExactlyRemainingDays() {
        hourly.work(200);
        assertDoesNotThrow(() -> hourly.work(60)); // exactly 60 remain
        assertEquals(260, hourly.getTotalDaysWorked());
    }

    @Test
    void cumulativeWorkUpdatesCorrectly() {
        hourly.work(100);
        hourly.work(100);
        assertEquals(200, hourly.getTotalDaysWorked());
        assertEquals(60, hourly.getRemainingWorkdays());
    }

    // ═══════════════════════════════════════════
    // TakeVacation() — Edge Cases
    // ═══════════════════════════════════════════

    @Test
    void takeVacationDeductsCorrectly() {
        salaried.work(260);             // 15 days accumulated
        salaried.takeVacation(5.0);     // use 5
        assertEquals(10.0, salaried.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void cannotTakeMoreVacationThanAccumulated() {
        hourly.work(130);               // 5 days accumulated
        assertThrows(IllegalArgumentException.class, () -> hourly.takeVacation(6.0));
    }

    @Test
    void cannotTakeZeroVacationDays() {
        hourly.work(260);
        assertThrows(IllegalArgumentException.class, () -> hourly.takeVacation(0));
    }

    @Test
    void cannotTakeNegativeVacationDays() {
        hourly.work(260);
        assertThrows(IllegalArgumentException.class, () -> hourly.takeVacation(-1.0));
    }

    @Test
    void cannotTakeVacationWhenNoneAccumulated() {
        assertThrows(IllegalArgumentException.class, () -> hourly.takeVacation(1.0));
    }

    @Test
    void canTakeExactlyAccumulatedVacation() {
        hourly.work(260);               // 10 days accumulated
        assertDoesNotThrow(() -> hourly.takeVacation(10.0));
        assertEquals(0.0, hourly.getVacationDaysAccumulated(), 0.0001);
    }

    @Test
    void vacationNeverGoesNegative() {
        hourly.work(260);
        hourly.takeVacation(10.0);
        assertTrue(hourly.getVacationDaysAccumulated() >= 0.0);
    }

    // ═══════════════════════════════════════════
    // Employee Type Checks
    // ═══════════════════════════════════════════

    @Test
    void employeeTypesAreCorrect() {
        assertEquals(EmployeeType.HOURLY,   hourly.getType());
        assertEquals(EmployeeType.SALARIED, salaried.getType());
        assertEquals(EmployeeType.MANAGER,  manager.getType());
    }
}
