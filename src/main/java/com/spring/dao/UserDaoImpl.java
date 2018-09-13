package com.spring.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	
	public User findByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();

		User user = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email)).uniqueResult();

		return user;
	}

	
	public void deleteUser(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, new Long(id));
		if (null != user) {
			session.delete(user);
		}

	}

	
	public void registerUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(user);
	}

	
	public void updateUser(Long id, User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);

		
	}

	
	public User findByEmailAndPassword(String email, String password) {
		Session session = this.sessionFactory.getCurrentSession();

		User user = (User) sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("password", password))
				.uniqueResult();

		return user;
	}

	


}
