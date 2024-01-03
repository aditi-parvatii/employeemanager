package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	
	Employee findById(int theId);

	boolean existsById(int id);
	
	Employee saveEmployee(Employee theEmployee);
	
	void deleteById(int theId);

	List<Employee> findByLastNameContaining(String lastName);
	
}
