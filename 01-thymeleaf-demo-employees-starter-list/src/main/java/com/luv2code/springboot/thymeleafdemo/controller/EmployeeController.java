package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService theEmployeeService) {
		this.employeeService=theEmployeeService;
	}

	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<List<Employee>> listEmployees() {
		List<Employee> employees = employeeService.findAll();
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
		Employee employee= employeeService.findById(id);
		return ResponseEntity.ok(employee);
	}

	@PutMapping("/employeeInfo")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee updatedEmployee) {
		try {
			// Check if the ID exists before trying to retrieve the employee
			if (employeeService.existsById(updatedEmployee.getId())) {
				Employee theEmployee = employeeService.findById(updatedEmployee.getId());
				theEmployee.setFirstName(updatedEmployee.getFirstName());
				theEmployee.setLastName(updatedEmployee.getLastName());
				theEmployee.setEmail(updatedEmployee.getEmail());
				employeeService.saveEmployee(theEmployee);
				return ResponseEntity.ok().build();
			} else {
				// Handle the case when the employee with the given ID does not exist
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID " + updatedEmployee.getId() + " not found");
			}
		} catch (Exception e) {
			// Log the exception
			// logger.error("An unexpected error occurred during employee update", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteSchool(@PathVariable int id) {
		Employee theemployee = employeeService.findById(id);
		employeeService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/employeedetails")
	public ResponseEntity<Employee> getEmployeeDetails(@RequestBody Employee employeeDetails){
		employeeService.saveEmployee(employeeDetails);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<Employee>> searchEmployeesByLastName(@RequestParam String lastName) {
		List<Employee> employees = employeeService.findByLastNameContaining(lastName);

		if (!employees.isEmpty()) {
			return ResponseEntity.ok(employees);
		} else {
			// Handle the case when no employees with the given lastName are found
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
	}


}









