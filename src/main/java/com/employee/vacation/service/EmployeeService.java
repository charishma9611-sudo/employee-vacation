package com.employee.vacation.service;

import com.employee.vacation.dto.EmployeeResponse;
import com.employee.vacation.exception.EmployeeNotFoundException;
import com.employee.vacation.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    // In-memory store (no database required per spec)
    private final Map<Integer, Employee> employeeStore = new LinkedHashMap<>();

    /**
     * Initialize 10 instances of each employee type on startup.
     */
    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            Employee hourly = new HourlyEmployee("HourlyEmployee_" + i);
            Employee salaried = new SalariedEmployee("SalariedEmployee_" + i);
            Employee manager = new Manager("Manager_" + i);

            employeeStore.put(hourly.getId(), hourly);
            employeeStore.put(salaried.getId(), salaried);
            employeeStore.put(manager.getId(), manager);
        }
    }

    // ---- Query methods ----

    public List<EmployeeResponse> getAllEmployees() {
        return employeeStore.values().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<EmployeeResponse> getEmployeesByType(EmployeeType type) {
        return employeeStore.values().stream()
                .filter(e -> {
                    if (type == EmployeeType.SALARIED) {
                        // SALARIED filter returns both salaried AND managers
                        return e.getType() == EmployeeType.SALARIED
                                || e.getType() == EmployeeType.MANAGER;
                    }
                    return e.getType() == type;
                })
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(int id) {
        return toResponse(findOrThrow(id));
    }

    // ---- Work method ----

    public EmployeeResponse work(int id, int daysWorked) {
        Employee employee = findOrThrow(id);
        employee.work(daysWorked);  // throws IllegalArgumentException on invalid input
        return toResponse(employee);
    }

    // ---- Take Vacation method ----

    public EmployeeResponse takeVacation(int id, double days) {
        Employee employee = findOrThrow(id);
        employee.takeVacation(days);  // throws IllegalArgumentException on invalid input
        return toResponse(employee);
    }

    // ---- Private helpers ----

    private Employee findOrThrow(int id) {
        Employee employee = employeeStore.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException(id);
        }
        return employee;
    }

    private EmployeeResponse toResponse(Employee e) {
        double vacationPerYear = switch (e.getType()) {
            case HOURLY -> 10.0;
            case SALARIED -> 15.0;
            case MANAGER -> 30.0;
        };

        return new EmployeeResponse(
                e.getId(),
                e.getName(),
                e.getType(),
                e.getVacationDaysAccumulated(),
                e.getTotalDaysWorked(),
                e.getRemainingWorkdays(),
                vacationPerYear
        );
    }
}
