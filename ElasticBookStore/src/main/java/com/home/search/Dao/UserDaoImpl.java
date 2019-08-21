package com.home.search.Dao;

import java.util.List;

import com.home.search.Model.Roles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.home.search.Model.User;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {

	private static final Log LOG = LogFactory.getLog(UserDaoImpl.class);

	// MongoTemplate mongotemplate;

	MongoOperations operations;
	private static final String USER = "user";
	private static final String ROLE = "roles";




	public UserDaoImpl(MongoOperations operations) {
		this.operations = operations;
	}

	@Override
	public boolean valdidateUserByUserName(String userName) {
		boolean isValidate = false;
		try {
			Query query = new Query(Criteria.where("userName").is(userName));
			isValidate = operations.exists(query, User.class, USER);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in valdidateUserByUsername method : " + exp.getMessage());
		}
		return isValidate;
	}

	@Override
	public boolean validateUserByEmail(String email) {
		boolean isValidate = false;
		try {
			Query query = new Query(Criteria.where("email").is(email));
			isValidate = operations.exists(query, User.class, USER);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in valdidateUserByUsername method : " + exp.getMessage());
		}
		return isValidate;
	}

	@Override
	public boolean saveUser(User user) {
		boolean isSaved = false;
		try {
			operations.save(user, USER);
			isSaved = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in saveUser method : " + exp.getMessage());
		}
		return isSaved;
	}

	@Override
	public boolean deleteUser(String userId) {
		boolean isDeleted = false;
		try {
			Query query = new Query(Criteria.where("userId").is(userId));
			operations.remove(query, User.class, USER);
			isDeleted = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in deleteUser method : " + exp.getMessage());
		}
		return isDeleted;
	}

	@Override
	public boolean deleteUser(User user) {
		boolean isDeleted = false;
		try {
			operations.remove(user, USER);
			isDeleted = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in deleteUser method : " + exp.getMessage());
		}
		return isDeleted;
	}

	@Override
	public boolean editUser(User user) {
		boolean isEdited = false;
		try {
			operations.save(user, USER);
			isEdited = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in editUser method : " + exp.getMessage());
		}
		return isEdited;
	}

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		try {
			Query query = new Query(Criteria.where("userName").is(userName));
			user = operations.findOne(query, User.class, USER);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in getUserByUsername method : " + exp.getMessage());
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			Query query = new Query(Criteria.where("email").is(email));
			user = operations.findOne(query, User.class, USER);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in getUserByEmail method : " + exp.getMessage());
		}
		return user;
	}

	/*
	 * @Override public String getUsernameByEmail(String email) { List<User>
	 * userName = null; try { Query query = new
	 * Query(Criteria.where("email").is(email)); userName =
	 * operations.findDistinct(query,"userName", USER, User.class);
	 * 
	 * }catch(Exception exp) { exp.printStackTrace();
	 * LOG.error("Exception in getUserNameByEmail method : " + exp.getMessage()); }
	 * return userName; }
	 */

	@Override
	public String getUsernameByEmail(String email) {
		String userName = null;
		try {
			Query query = new Query(Criteria.where("email").is(email));
			query.fields().include("userName");
			userName = operations.findOne(query, String.class);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in getUsernameByEmail method : " + exp.getMessage());
		}
		return userName;
	}

	@Override
	public User getUserByUserId(String userId) {
		User user = null;
		try {
			Query query = new Query(Criteria.where("userId").is(userId));
			user = operations.findOne(query, User.class, USER);
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in getUserByEmail method : " + exp.getMessage());
		}
		return user;
	}

	@Override
	public Roles findByRole(String roles) {
		Roles role = null;
		try{
			Query query = new Query(Criteria.where("role").is(roles));
			role = operations.findOne(query,Roles.class,ROLE);
		}catch (Exception exp){
			exp.printStackTrace();
		}
		return role;
	}

	@Override
	public boolean saveRoles(Roles role) {
		boolean isSaved = false;
		try {
			operations.save(role, ROLE);
			isSaved = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			LOG.error("Exception in saveUser method : " + exp.getMessage());
		}
		return isSaved;
	}

}
