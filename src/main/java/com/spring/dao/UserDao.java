package com.spring.dao;

import com.spring.model.User;

public interface UserDao {
	public User findByEmail(String email);
	public void deleteUser(Long id);
	public void registerUser(User user);
	public void updateUser(Long id, User user);
	public User findByEmailAndPassword(String email, String password);
}
