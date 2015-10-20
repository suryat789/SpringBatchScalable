package com.demo.spring.batch.operations;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.spring.batch.dao.entities.EmployeeTarget;
import com.demo.spring.batch.dao.impl.EmployeeDAO;

/**
 * {@link ItemWriter} which only logs data it receives.
 * 
 * @author Surya Tiwari
 */
public class EmployeeItemWriter<Employee> implements ItemWriter<Employee> {

	@Autowired
	EmployeeDAO employeeDAO;
	
	private static final Log LOG = LogFactory.getLog(EmployeeItemWriter.class);

	/**
	 * @see ItemWriter#write(java.util.List)
	 */
	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		LOG.info(employees);
		for (Employee employee : employees) {
			employeeDAO.addEmployee(getTargetEmployee(employee));
		}
	}

	private EmployeeTarget getTargetEmployee(Employee employee) {
		EmployeeTarget employeeTarget = new EmployeeTarget();
		employeeTarget.setEmployeeID(employee.);
		employeeTarget.setEmployeeName(employeeName);
		employeeTarget.setEmployeeDept(employeeDept);
		return employeeTarget;
	}

}
