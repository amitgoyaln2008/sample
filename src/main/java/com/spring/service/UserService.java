package com.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.model.User;

public interface UserService {
	public User findByEmail(String email);

	public void deleteUser(Long id);

	public void registerUser(HttpServletRequest request, User user);

	public User activate(String token);

	public void updateUser(Long id, User user);

	public User findByEmailAndPassword(String email, String password);

	public User login(User user, HttpServletResponse response);
	
	public void forgetPassword( User user);

}
