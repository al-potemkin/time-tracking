package com.potemkin.timetracking.entities;

/**
 * Description: This class contains constants which describe different types of users.
 * <p>
 */
public class UserType extends BaseEntity {

    private Integer userTypeId;
    private String userType;

    public UserType() {
    }

    public UserType(Integer userTypeId, String userType) {
        this.userTypeId = userTypeId;
        this.userType = userType;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType1 = (UserType) o;

        if (userTypeId != null ? !userTypeId.equals(userType1.userTypeId) : userType1.userTypeId != null) return false;
        return userType != null ? userType.equals(userType1.userType) : userType1.userType == null;
    }

    @Override
    public int hashCode() {
        int result = userTypeId != null ? userTypeId.hashCode() : 0;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userType='" + userType + '\'' +
                '}';
    }
}