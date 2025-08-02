package com.example.demo.model;

import java.util.Date;

public class Staff {
    private Long staffId;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber;
    private String email;
    private String password;
    private Date joiningDate;
    private Long departmentId;
    private String address;
    private String qualification;
    private String emergencyContact;

    // Constructors
    public Staff() {
        this.joiningDate = new Date();
    }

    public Staff(Long staffId, String firstName, String lastName, String role,
                 String phoneNumber, String email) {
        this();
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) { this.role = role; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getJoiningDate() { return joiningDate; }
    public void setJoiningDate(Date joiningDate) { this.joiningDate = joiningDate; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
}