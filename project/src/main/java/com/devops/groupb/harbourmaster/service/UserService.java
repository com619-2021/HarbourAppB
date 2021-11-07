package com.devops.groupb.harbourmaster.service;


import com.devops.groupb.harbourmaster.dto.Work;
import com.devops.groupb.harbourmaster.dto.Role;
import com.devops.groupb.harbourmaster.dto.User;
import com.devops.groupb.harbourmaster.model.ChangePasswordForm;
import com.devops.groupb.harbourmaster.model.UserForm;

import java.util.Collection;
import java.util.List;

public interface UserService {
    /*
     * User
     * */
    boolean userExists(String userName);

    User getUserById(int userId);

    User getUserByUsername(String userName);

    List<User> getUsersByRoleName(String roleName);

    List<User> getAllUsers();

    void deleteUserById(int userId);

    void updateUserPassword(ChangePasswordForm passwordChangeForm);
}
