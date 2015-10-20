package com.demo.spring.batch.dao.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The persistent class for the employee database table.
 * 
 */
@Entity(name="EmployeeTarget")
public class EmployeeTarget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String employeeID;

	private String employeeDept;

	private String employeeName;

	public EmployeeTarget(String employeeID, String employeeName, String employeeDept) {
		super();
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeDept = employeeDept;
	}

	public EmployeeTarget() {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeID == null) ? 0 : employeeID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeTarget other = (EmployeeTarget) obj;
		if (employeeID == null) {
			if (other.employeeID != null)
				return false;
		} else if (!employeeID.equals(other.employeeID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeTarget [employeeID=" + employeeID + ", employeeDept="
				+ employeeDept + ", employeeName=" + employeeName + "]";
	}
}