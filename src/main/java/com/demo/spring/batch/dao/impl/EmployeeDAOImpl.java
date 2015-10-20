package com.demo.spring.batch.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.demo.spring.batch.dao.entities.Employee;
import com.demo.spring.batch.dao.entities.EmployeeTarget;

@Repository("employeeDao")
public class EmployeeDAOImpl extends AbstractDAO implements EmployeeDAO {
	
	@Override
	public void addEmployee(EmployeeTarget employee) {
		try {
			persist(employee);
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public Employee getEmpoyeeDetails(String id) {
		Query query = getEntityManager().createQuery(" from Employee emp WHERE emp.employeeID = :employeeID");
		query.setParameter("employeeID", id);
		
		List<Employee> employees = query.getResultList();
		if(employees != null && !employees.isEmpty()){
			return employees.get(0);
		}
		return null;
	}
	
}
