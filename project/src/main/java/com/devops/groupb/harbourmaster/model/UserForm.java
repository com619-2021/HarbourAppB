package com.devops.groupb.harbourmaster.model;

import com.devops.groupb.harbourmaster.dto.Work;
import com.devops.groupb.harbourmaster.dto.User;
import com.devops.groupb.harbourmaster.validation.FieldsMatches;
import com.devops.groupb.harbourmaster.validation.UniqueUsername;
import com.devops.groupb.harbourmaster.validation.groups.*;

import javax.validation.constraints.*;
import java.util.List;

@FieldsMatches(field = "password", matchingField = "matchingPassword", groups = {CreateUser.class})
public class UserForm {

    @NotNull(groups = {UpdateUser.class})
    @Min(value = 1, groups = {UpdateUser.class})
    private int id;

    @UniqueUsername(groups = {CreateUser.class})
    @Size(min = 5, max = 15, groups = {CreateUser.class}, message = "Username should have 5-15 letters")
    @NotBlank(groups = {CreateUser.class})
    private String userName;

    @Size(min = 5, max = 15, groups = {CreateUser.class}, message = "Password should have 5-15 letters")
    @NotBlank(groups = {CreateUser.class})
    private String password;

    @Size(min = 5, max = 15, groups = {CreateUser.class}, message = "Password should have 5-15 letters")
    @NotBlank(groups = {CreateUser.class})
    private String matchingPassword;

    @NotBlank(groups = {CreateUser.class, UpdateUser.class}, message = "First name cannot be empty")
    private String firstName;

    @NotBlank(groups = {CreateUser.class, UpdateUser.class}, message = "Last name cannot be empty")
    private String lastName;

    @Email(groups = {CreateUser.class, UpdateUser.class}, message = "Email not valid!")
    @NotBlank(groups = {CreateUser.class, UpdateUser.class}, message = "Email cannot be empty")
    private String email;


    public UserForm() {
    }

    public UserForm(User user) {
        this.setId(user.getId());
        this.setUserName(user.getUserName());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 
}
