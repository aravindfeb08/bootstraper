package com.home.search.Service;

import com.home.search.Model.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.home.search.Dao.UserDao;
import com.home.search.Model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public boolean valdidateUserByUserName(String userName) {
		boolean isValidated = false;
		try {
			isValidated = userDao.valdidateUserByUserName(userName);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in valdidateUserByUserName method : " + exp);
		}
		return isValidated;
	}

	@Override
	public boolean validateUserByEmail(String email) {
		boolean isValidated = false;
		try {
			isValidated = userDao.validateUserByEmail(email);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in validateUserByEmail method : " + exp);
		}
		return isValidated;
	}

	@Override
	public boolean saveUser(User user) {
		boolean isSaved = false;
		try {
			isSaved = userDao.saveUser(user);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in saveUser method : " + exp);
		}
		return isSaved;
	}

	@Override
	public boolean deleteUser(String userId) {
		boolean isDeleted = false;
		try {
			isDeleted = userDao.deleteUser(userId);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in deleteUser method : " + exp);
		}
		return isDeleted;
	}

	@Override
	public boolean deleteUser(User user) {
		boolean isDeleted = false;
		try {
			isDeleted = userDao.deleteUser(user);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in deleteUser method : " + exp);
		}
		return isDeleted;
	}

	@Override
	public boolean editUser(User user) {
		boolean isEdited = false;
		try {
			isEdited = userDao.editUser(user);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in editUser method : " + exp);
		}
		return isEdited;
	}

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		try {
			user = userDao.getUserByUserName(userName);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in getUserByUserName method : " + exp);
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = userDao.getUserByEmail(email);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in getUserByEmail method : " + exp);
		}
		return user;
	}

	@Override
	public User getUserByUserId(String userId) {
		User user = null;
		try {
			user = userDao.getUserByUserId(userId);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.equals("Exception in getUserByEmail method : " + exp);
		}
		return user;
	}

	@Override
	public Role findByRole(String roles) {
		Role role = null;
		try{
			role = userDao.findByRole(roles);
		}catch (Exception exp){
			exp.printStackTrace();
		}
		return role;
	}
}
