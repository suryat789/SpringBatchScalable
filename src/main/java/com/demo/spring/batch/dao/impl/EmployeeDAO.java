package com.demo.spring.batch.dao.impl;

import com.demo.spring.batch.dao.entities.Employee;
import com.demo.spring.batch.dao.entities.EmployeeTarget;


public interface EmployeeDAO {
	
	public void addEmployee(EmployeeTarget employee);

	Employee getEmpoyeeDetails(String id);
		
}