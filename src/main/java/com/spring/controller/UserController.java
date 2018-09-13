package com.spring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.model.BaseResponse;
import com.spring.model.FileInfo;
import com.spring.model.User;
import com.spring.service.UserService;

@RestController
public class UserController {

	@Autowired
	ServletContext context;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(HttpServletRequest request, @RequestBody User user) {

		String email = user.getEmail();
		User userObj = userService.findByEmail(email);
		if (userObj != null) {
			return setResponse(false, 0, "User already exist", HttpStatus.OK, null);
		}

		if (user.getProfilepicture() != null || user.getProfilepicture() != "") {
			URLConnection imageUrl = null;
			String fileName = "";
			try {
				imageUrl = new URL(user.getProfilepicture()).openConnection();
				imageUrl.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				imageUrl.connect();
				InputStream imageReader = new BufferedInputStream(imageUrl.getInputStream());

				File uploadDir = new File(
						"/home/vvdntech/Documents/Spring_WorkSpcace/EmplyoeeRestSample/WebContent/WEB-INF/uploaded");

				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}
				fileName = uploadDir + File.separator + FilenameUtils.getName(user.getProfilepicture());
				OutputStream imageWriter = new BufferedOutputStream(new FileOutputStream(fileName));

				int readByte;

				while ((readByte = imageReader.read()) != -1) {
					imageWriter.write(readByte);
				}
				imageWriter.close();
				imageReader.close();
				user.setProfilepicture(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		userService.registerUser(request, user);

		User saveUser = userService.findByEmail(email);

		String msg = "You are successfully registered. Please activate your account by clicking on the link sent to the user "
				+ user.getEmail();

		return setResponse(true, 1, msg, HttpStatus.OK, saveUser);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
		String email = user.getEmail();
		String password = user.getPassword();

		user = userService.findByEmail(email);
		if (user == null) {
			return setResponse(false, 0, "Email not found", HttpStatus.OK, null);
		}
		if (user.getEnabled() == 0) {
			return setResponse(false, 0, "user not verified", HttpStatus.OK, user);
		}
		user = userService.findByEmailAndPassword(email, password);
		if (user == null) {
			return setResponse(false, 0, "Wrong password", HttpStatus.OK, user);
		}
		user = userService.login(user, response);

		return setResponse(true, 1, "Login successfully", HttpStatus.OK, user);

	}

	@RequestMapping(value = "/forget_passsword", method = RequestMethod.POST)
	public ResponseEntity<?> forgetPassword(@RequestBody User user) {
		String email = user.getEmail();

		if (null == email || email.equals(""))
			return setResponse(false, 0, "Invalid Request", HttpStatus.OK, null);

		user = userService.findByEmail(email);
		if (user == null) {
			return setResponse(false, 0, "Email not found", HttpStatus.OK, null);
		}

		if (user.getEnabled() == 0) {
			return setResponse(false, 0, "user not verified", HttpStatus.OK, user);
		}

		userService.forgetPassword(user);

		return setResponse(true, 1, "Mail send successfully", HttpStatus.OK, null);

	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verify(@RequestParam(required = false, value = "code") String token) {
		User user = userService.activate(token);
		if (user == null) {
			String verifyMessage = "<html><head><title>User Activation </title><body><p>please try again!!! </p></body></head></html>";
			return verifyMessage;
		}
		user.setEnabled(1);
		userService.updateUser(user.getUserId(), user);

		String verifyMessage = "<html><head><title>User Activation </title><body><p>Your account is verified. Please login into app with your email and password.</p></body></head></html>";
		return verifyMessage;
	}

	@RequestMapping(value = "/fileupload", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile) {
		FileInfo fileInfo = new FileInfo();
		HttpHeaders headers = new HttpHeaders();
		if (!inputFile.isEmpty()) {
			try {
				String originalFilename = inputFile.getOriginalFilename();
				File destinationFile = new File(
						"/home/vvdntech/Documents/Spring_WorkSpcace/EmplyoeeRestSample/WebContent/WEB-INF/uploaded"
								+ File.separator + originalFilename);
				inputFile.transferTo(destinationFile);
				fileInfo.setFileName(destinationFile.getPath());
				fileInfo.setFileSize(inputFile.getSize());
				headers.add("File Uploaded Successfully - ", originalFilename);
				return new ResponseEntity<FileInfo>(fileInfo, headers, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<FileInfo>(HttpStatus.BAD_REQUEST);
		}
	}

	private ResponseEntity<BaseResponse<Object>> setResponse(final boolean statusCode, final int resultCode,
			final String messageValue, final HttpStatus httpStatus, Object user) {

		BaseResponse<Object> userResponse = new BaseResponse<Object>();

		userResponse.setStatus(statusCode);
		userResponse.setResultCode(resultCode);
		userResponse.setMessage(messageValue);
		userResponse.setData(user);

		return new ResponseEntity<BaseResponse<Object>>(userResponse, httpStatus);
	}

}
