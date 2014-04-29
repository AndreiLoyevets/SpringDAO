package com.epam.andrii_loievets.springdao.domain;

/**
 *
 * @author Andrii_Loievets
 */
public class Department {
    private int departmentId;
    private String location;
    private User manager;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
