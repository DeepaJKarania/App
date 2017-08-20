package com.niit.AppBackEnd;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.AppBackEnd.DAO.UserDAO;
import com.niit.AppBackEnd.Model.User;

public class TestUser 
{
	Logger log = LoggerFactory.getLogger(TestUser.class);

	@Autowired
	UserDAO userDAO;

	@Autowired
	User user;

	@Autowired
	AnnotationConfigApplicationContext context;

	public TestUser()
	{

		context = new AnnotationConfigApplicationContext();
		context.scan("com.niit.AppBackEnd");
		context.refresh();

		userDAO = (UserDAO) context.getBean("userDAO");
		user = (User) context.getBean("user");

	}

	public void testAdd()
	{
		log.info("Add User Test started");

		user.setUsername("testuser");
		user.setFirst_name("Test");
		user.setLast_name("User");
		user.setGender('F');
		user.setMail_id("testuser@gmail.com");
		user.setPassword("deepa@123");


		userDAO.addUser(user);
		log.info("Add User Test end");
	}

	public void getUserDetails()
	{
		log.info("Get User Details Started");
		String userName = "testuser";
		user = userDAO.getUser(userName);
		System.out.println("Name - "+user.getFirst_name());
				log.info("Get User Ended");
	}

	public void validateUser()
	{
		log.info("Validate User Started");
		String userName = "testuser";
		String password = "deepa@123";
		boolean value = userDAO.validateUser(userName, password);
		if(value)
			System.out.println("Valid");
		else
			System.out.println("Invalid");
		log.info("Validate User Ended");
	}

	

	public static void main(String[] args) 
	{
		TestUser tuser = new TestUser();
		tuser.testAdd();
		tuser.getUserDetails();
		tuser.validateUser();


		System.out.println("Success");
	}
}