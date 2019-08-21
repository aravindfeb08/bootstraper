package com.home.search.Dao;

import com.home.search.Model.Roles;
import com.home.search.Model.User;

public interface UserDao {

    boolean valdidateUserByUserName(String userName);

    boolean validateUserByEmail(String email);

    boolean saveUser(User user);

    boolean deleteUser(String userId);

    boolean deleteUser(User user);

    boolean editUser(User user);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    /*
     * String getUsernameByEmail(String email);
     */

    String getUsernameByEmail(String email);

    User getUserByUserId(String userId);

    Roles findByRole(String roles);

    boolean saveRoles(Roles role);


}
