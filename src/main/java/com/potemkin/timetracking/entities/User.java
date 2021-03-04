package com.potemkin.timetracking.entities;

import java.io.Serializable;

/**
 * Description: This class describes all applications users.
 * <p>
 */
public class User extends BaseEntity implements Serializable {

    private Integer userId;
    private String firstName;
    private String surName;
    private String login;
    private String password;
    private UserType userType;
    private Boolean requestAdd;

    public User() {
    }

    public User(String firstName, String surName, String login, String password,
                UserType userType, Boolean requestAdd) {
        this.firstName = firstName;
        this.surName = surName;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.requestAdd = requestAdd;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getRequestAdd() {
        return requestAdd;
    }

    public void setRequestAdd(Boolean requestAdd) {
        this.requestAdd = requestAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (surName != null ? !surName.equals(user.surName) : user.surName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userType != null ? !userType.equals(user.userType) : user.userType != null) return false;
        return requestAdd != null ? requestAdd.equals(user.requestAdd) : user.requestAdd == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surName != null ? surName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (requestAdd != null ? requestAdd.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", requestAdd=" + requestAdd +
                '}';
    }
}
