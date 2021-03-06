package com.niit.AppBackEnd.DAO;


import java.util.List;

import com.niit.AppBackEnd.Model.User;


public interface UserDAO
{

	public boolean addUser(User user);
	
	public boolean validateUser(String userName, String password);
	
	public User getUser(String userName);
	
	public List<User> getUserList();
	
	
}
