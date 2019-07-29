package com.home.search.Service;

import com.home.search.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
	
	boolean valdidateUserByUserName(String userName);
	
	boolean validateUserByEmail(String email);
	
	boolean saveUser(User user);
	
	boolean deleteUser(String userId);
	
	boolean deleteUser(User user);
	
	boolean editUser(User user);
	
	User getUserByUserName(String userName);
	
	User getUserByEmail(String email);

	
}
