package com.demo.spring.batch.beans;

public class Person {

	private String firstName;
	private String familyName;
	private Integer year;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", familyName=" + familyName + ", year=" + year + "]";
	}

}
