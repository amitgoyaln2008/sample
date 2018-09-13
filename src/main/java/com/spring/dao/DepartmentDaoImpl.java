package com.spring.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.model.Department;

@Repository
@Transactional
public class DepartmentDaoImpl implements DepartmentDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void addDepartment(Department department) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(department);
		
	}

}
