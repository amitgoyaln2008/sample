package com.spring.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Employee")
public class Person {

	@Id
	@Column(name="emp_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private Date dob;
	
	private Date hire_date;
	
	private String gender;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
    private Department department;
	

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dob=" + dob + ", hire_date=" + hire_date + ", gender="
				+ gender + ", department=" + department + "]";
	}

	


	
}