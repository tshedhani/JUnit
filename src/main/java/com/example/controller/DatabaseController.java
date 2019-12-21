package com.example.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.db.dto.LoginInfo;
import com.example.db.repositories.LoginInfoRepository;
import com.example.utils.HashUtility;
import com.example.vo.PersonInfo;

@RestController
@CrossOrigin
public class DatabaseController {

	@Autowired
	LoginInfoRepository loginInfoRepo;

	@Autowired
	HashUtility hash;

	@RequestMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody PersonInfo person) {

		if (person != null) {
			System.out.println("Input " + person.toString());
			if (StringUtils.isEmpty(person.getName()) && StringUtils.isEmpty(person.getPassword())) {
				throw new IllegalArgumentException("Request parameter username or password cannot be null");
			} else {
				LoginInfo loginInfo = new LoginInfo();

				loginInfo.setLoginId(new Random().nextInt());

				loginInfo.setLoginName(person.getName());
				loginInfo.setLoginPassword(hash.generateHash(person.getPassword()));

				long millis = System.currentTimeMillis();
				java.sql.Date date = new java.sql.Date(millis);
				loginInfo.setLoginUpdatedDate(date);

				loginInfoRepo.save(loginInfo);
			}
		} else {
			throw new IllegalArgumentException("Payload cannot be null");
		}

		return person.toString();
	}

	@RequestMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public String updateUser(@RequestBody PersonInfo person) {

		if (person != null) {
			System.out.println("Input " + person.toString());
			if (StringUtils.isEmpty(person.getName()) && StringUtils.isEmpty(person.getPassword())) {
				throw new IllegalArgumentException("Request parameter username or password cannot be null");
			} else {
				LoginInfo loginInfo = new LoginInfo();

				loginInfo.setLoginId(new Random().nextInt());

				loginInfo.setLoginName(person.getName());
				loginInfo.setLoginPassword(hash.generateHash(person.getPassword()));

				long millis = System.currentTimeMillis();
				java.sql.Date date = new java.sql.Date(millis);
				loginInfo.setLoginUpdatedDate(date);

				loginInfoRepo.save(loginInfo);
			}
		} else {
			throw new IllegalArgumentException("Payload cannot be null");
		}

		return person.toString();
	}

	@RequestMapping(value = "/getUser/{name}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public String getUserByName(@PathVariable String name) {

		if (!StringUtils.isEmpty(name)) {
			Optional<LoginInfo> optional= loginInfoRepo.findById(name);
			if (optional.isPresent()) {
			
				return optional.get().getLoginName();
			}else {
				return "Nothing returned";
			}			
		} else {
			return "Name parameter cannot be null";
		}
	}

//	@RequestMapping(value = "/getAllUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
//	@ResponseBody
//	public String getAllUsers() {
//		Iterable<LoginInfo> iterable = loginInfoRepo.findAll();
//		 while (iterable.iterator().hasNext()) { 
//			  System.out.println(iterable.iterator().next().getLoginName());
//	        }
//		return "All User";
//	}
}