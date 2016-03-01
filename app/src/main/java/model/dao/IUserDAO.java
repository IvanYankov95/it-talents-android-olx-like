package model.dao;

import java.util.List;

import model.UserAcc;

/**
 * Created by owner on 01/03/2016.
 */
public interface IUserDAO {

    long addUser(UserAcc user);
    UserAcc getUser(String username);
    UserAcc getUser(long id);
    List<UserAcc> getAllUsers();
    void deleteUser(UserAcc user);
    long updateUser(UserAcc user);
    boolean checkUsername(String username);
    boolean checkUserEmail(String email);
    UserAcc checkLogin (String email, String password);

}
