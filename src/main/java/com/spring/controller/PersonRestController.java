package com.spring.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.model.BaseResponse;
import com.spring.model.Department;
import com.spring.model.Person;
import com.spring.service.DepartmentService;
import com.spring.service.PersonService;
//Adding new comment to the file.
//Adding comment for the test purpose by Rajeev
@RestController
public class PersonRestController {

	@Autowired
	ServletContext context;

	@Autowired
	private PersonService personService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/persons_list", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse<Object>> personList() {

		List<Person> persons = this.personService.listPersons();

		if (persons != null && persons.size() > 0)
			return setResponse(true, 1, "Suceess", HttpStatus.OK, persons);
		else
			return setResponse(false, 0, "No data found", HttpStatus.OK, null);

	}

	@RequestMapping(value = "/persons/{id}", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse<Object>> findPersonById(@PathVariable int id) {

		Person pesons = this.personService.getPersonById(id);

		if (pesons != null)
			return setResponse(true, 1, "Suceess", HttpStatus.OK, pesons);
		else
			return setResponse(false, 0, "No data found", HttpStatus.OK, null);

	}

	@RequestMapping(value = "/addPerson/{name}/{gender}", method = RequestMethod.GET)
	public ResponseEntity<BaseResponse<Object>> save(@PathVariable String name, @PathVariable String gender) {

		Person pesons = new Person();
		pesons.setName(name);
		pesons.setGender(gender);
		Date d = new Date();
		pesons.setDob(d);
		pesons.setHire_date(d);

		this.personService.addPerson(pesons);
		return setResponse(true, 1, "Suceess", HttpStatus.OK, pesons);

	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse<Object>> saveperson(@RequestBody Person person) {

		Date d = new Date();
		person.setDob(d);
		person.setHire_date(d);
		this.personService.addPerson(person);

		return setResponse(true, 1, "Suceess", HttpStatus.OK, person);

	}

	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse<Object>> saveDept(@RequestBody Department department) {
		this.departmentService.addDepartment(department);

		return setResponse(true, 1, "Suceess", HttpStatus.OK, department);

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
