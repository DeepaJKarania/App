package com.niit.AppBackEnd.Controller;


import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.AppBackEnd.DAO.UserDAO;
import com.niit.AppBackEnd.Model.User;




@RestController
public class UserController 
{
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDAO userDAO;
	

	@Autowired
	private User user;

	@Autowired
	HttpSession session;
	
	@GetMapping("/getUserList")
	public ResponseEntity<List<User>> getUserList() throws NullPointerException
	{
			List<User> list = userDAO.getUserList();
			if (list.isEmpty()) 
			{
				user.setErrorCode("100");
				user.setErrorMsg("Users are not available");
			}
			else
			{
				
				for(User user : list)
				{
					user.setErrorCode("200");
					user.setErrorMsg("Success");
					}
			}
			return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> validateUser(@RequestBody User user) 
	{
		System.out.println("Name - "+user.getUsername());
		boolean value = userDAO.validateUser(user.getUsername(), user.getPassword());
		System.out.println(value);
		if (value == false) 
		{
			user = new User();
			user.setErrorCode("404");
			user.setErrorMsg("Wrong username or password.");
		}
		else
					
			{
				user = userDAO.getUser(user.getUsername());
				userDAO.addUser(user);
				session.setAttribute("username", user.getUsername());
				session.setAttribute("isLoggedIn", "true");
				
				
				user.setErrorCode("200");
				user.setErrorMsg("Success");
				System.out.println("Name = "+session.getAttribute("username").toString());
			}
		

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@PostMapping("/add_User")
	public ResponseEntity<User> addUser(@RequestBody User user) 
	{	    
	  
		boolean value = userDAO.addUser(user);
		if (value == true) 
		{
			user.setErrorCode("200");
			user.setErrorMsg("User added Successfully");
		} 
		else 
		{
			user.setErrorCode("100");
			user.setErrorMsg("Add User Failed");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	
	@GetMapping("/logout")
	public ResponseEntity<User> logout()
	{
		log.info("isLoggedIN - "+session.getAttribute("isLoggedIn"));
		if(session.getAttribute("isLoggedIn") != null)
		{
			user = userDAO.getUser(session.getAttribute("username").toString());
			userDAO.addUser(user);
			user = new User();
			user.setErrorCode("200");
			user.setErrorMsg("You havOe logged out.");
			session.invalidate();
		}
		else
		{
			user = new User();
			user.setErrorCode("500");
			user.setErrorMsg("You have not logged in");
			log.info(user.getErrorMsg());
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}