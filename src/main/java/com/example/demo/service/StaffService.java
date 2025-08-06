package com.example.demo.service;

import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Long id, Staff staff) {
        staff.setStaffId(id);
        return staffRepository.update(staff);
    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }

    public List<Staff> getStaffByRole(String role) {
        return staffRepository.findByRole(role);
    }

    public List<Staff> getStaffByDepartment(Long departmentId) {
        return staffRepository.findByDepartmentId(departmentId);
    }

    public List<Staff> searchStaff(String name, String role, Long departmentId, String qualification) {
        return staffRepository.searchStaff(name, role, departmentId, qualification);
    }
}