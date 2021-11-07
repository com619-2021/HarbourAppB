package com.devops.groupb.harbourmaster.dto;

import com.devops.groupb.harbourmaster.entity.EntityModel; //Used to generate model
import com.devops.groupb.harbourmaster.model.UserForm;
import com.devops.groupb.harbourmaster.model.Notification;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)

public class User extends EntityModel {
    
    @Column(name = "username")
    private String userName;
   
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(UserForm newForm, String encryptedPassword, Collection<Role> roles) {
        this.setUserName(newForm.getUserName());
        this.setFirstName(newForm.getFirstName());
        this.setLastName(newForm.getLastName());
        this.setEmail(newForm.getEmail());
        this.password = encryptedPassword;
        this.roles = roles;
    }

    public void update(UserForm updateData) {
        this.setEmail(updateData.getEmail());
        this.setFirstName(updateData.getFirstName());
        this.setLastName(updateData.getLastName());
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
