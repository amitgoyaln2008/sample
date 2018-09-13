package com.spring.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dao.UserDao;
import com.spring.model.User;
import com.spring.security.TokenAuthenticationService;

@Service
public class UserServiceImpl implements UserService {

	private TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private JavaMailSender javaMailService;

	@Transactional
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Transactional
	public void deleteUser(Long id) {
		userDao.deleteUser(id);

	}

	@Transactional
	public void registerUser(HttpServletRequest request, User user) {

		tokenAuthenticationService = new TokenAuthenticationService();

		String token = tokenAuthenticationService.addAuthentication(user.getEmail());

		String msg = "Hello " + user.getUsername()
				+ "\n Your registration is successfull.Please click on link below to activate your account" + "\n \n "
				+ request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ "/EmplyoeeRestSample/verify?code=" + token;
//		 sendEmail(user, msg,"Registration");

		userDao.registerUser(user);
	}

	public void forgetPassword(User user) {

		String msg = "Hello " + user.getUsername() + " Password   :  " + user.getPassword();
//		sendEmail(user, msg, "Password");

	}

	private void sendEmail(User user, String msg, String subject) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject(subject);
			mailMessage.setFrom("amitgoyaln200@gmail.com");
			mailMessage.setText(msg);
			javaMailService.send(mailMessage);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}

	public User activate(String token) {
		tokenAuthenticationService = new TokenAuthenticationService(this);
		User user = tokenAuthenticationService.getTokenHandler().parseUserFromToken(token);
		return user;
	}

	public void updateUser(Long id, User user) {

		userDao.updateUser(id, user);

	}

	public User findByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}

	public User login(User user, HttpServletResponse response) {
		tokenAuthenticationService = new TokenAuthenticationService(this);
		tokenAuthenticationService.addAuthentication(response, user.getEmail());
		response.addHeader("Access-Control-Expose-Headers", "X-AUTH-TOKEN");
		return user;
	}

}
