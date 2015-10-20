package com.demo.spring.rest.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String employeeID;

	private String employeeDept;

	private String employeeName;

	public Employee(String employeeID, String employeeName, String employeeDept) {
		super();
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeDept = employeeDept;
	}

	public Employee() {
	}

	public String getEmployeeID() {
		return this.employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeDept() {
		return this.employeeDept;
	}

	public void setEmployeeDept(String employeeDept) {
		this.employeeDept = employeeDept;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", employeeDept="
				+ employeeDept + ", employeeName=" + employeeName + "]";
	}
}