package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.DepartmentDao;
import com.spring.model.Department;

@Service
public class DepartmentServiceImpl implements  DepartmentService{

	@Autowired
	DepartmentDao departmentDao;


	
	public void addDepartment(Department department) {
		departmentDao.addDepartment(department);
		
	}
 
}
