package com.employee.vacation.controller;

import com.employee.vacation.dto.ApiResponse;
import com.employee.vacation.dto.EmployeeResponse;
import com.employee.vacation.dto.VacationRequest;
import com.employee.vacation.dto.WorkRequest;
import com.employee.vacation.model.EmployeeType;
import com.employee.vacation.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Employee operations.
 * Designed so a UI layer can easily consume these endpoints.
 *
 * Base URL: /api/employees
 */
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // Allow UI calls from any origin
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * GET /api/employees
     * Returns all employees across all types.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(ApiResponse.ok("Fetched all employees", employees));
    }

    /**
     * GET /api/employees/{id}
     * Returns a single employee by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable int id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.ok("Employee found", employee));
    }

    /**
     * GET /api/employees/type/{type}
     * Returns all employees of the given type: HOURLY, SALARIED, or MANAGER
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployeesByType(
            @PathVariable String type) {

        EmployeeType employeeType;
        try {
            employeeType = EmployeeType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid employee type: '" + type
                            + "'. Valid values: HOURLY, SALARIED, MANAGER"));
        }

        List<EmployeeResponse> employees = employeeService.getEmployeesByType(employeeType);
        return ResponseEntity.ok(ApiResponse.ok("Fetched employees of type " + employeeType, employees));
    }

    /**
     * POST /api/employees/{id}/work
     * Records days worked for an employee and updates vacation accumulation.
     *
     * Request Body: { "daysWorked": 130 }
     */
    @PostMapping("/{id}/work")
    public ResponseEntity<ApiResponse<EmployeeResponse>> work(
            @PathVariable int id,
            @Valid @RequestBody WorkRequest request) {

        EmployeeResponse updated = employeeService.work(id, request.getDaysWorked());
        return ResponseEntity.ok(ApiResponse.ok(
                "Worked " + request.getDaysWorked() + " days. Vacation updated.", updated));
    }

    /**
     * POST /api/employees/{id}/vacation/take
     * Deducts vacation days from an employee's balance.
     *
     * Request Body: { "days": 5.0 }
     */
    @PostMapping("/{id}/vacation/take")
    public ResponseEntity<ApiResponse<EmployeeResponse>> takeVacation(
            @PathVariable int id,
            @Valid @RequestBody VacationRequest request) {

        EmployeeResponse updated = employeeService.takeVacation(id, request.getDays());
        return ResponseEntity.ok(ApiResponse.ok(
                "Took " + request.getDays() + " vacation day(s). Balance updated.", updated));
    }
}
