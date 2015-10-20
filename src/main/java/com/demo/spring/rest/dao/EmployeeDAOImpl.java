package com.demo.spring.rest.dao;

import org.springframework.stereotype.Repository;

import com.demo.spring.rest.entities.Employee;

@Repository("employeeDao")
public class EmployeeDAOImpl extends AbstractDAO implements EmployeeDAO {
	
	@Override
	public void addEmployee(Employee employee) {
		try {
			persist(employee);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
