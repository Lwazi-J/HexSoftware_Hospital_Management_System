package com.example.demo.model;

import java.util.Date;

public class Department {
    private Long departmentId;
    private String name;
    private String description;
    private String location;
    private String headDoctorId;
    private Integer staffCount;

    private String contactNumber;

    // Constructors
    public Department() {}

    public Department(Long departmentId, String name, String description, String location) {
        this.departmentId = departmentId;
        this.name = name;
        this.description = description;
        this.location = location;
    }

    // Getters and Setters
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getHeadDoctorId() { return headDoctorId; }
    public void setHeadDoctorId(String headDoctorId) { this.headDoctorId = headDoctorId; }
    public Integer getStaffCount() { return staffCount; }
    public void setStaffCount(Integer staffCount) { this.staffCount = staffCount; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}