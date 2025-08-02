package com.example.demo.service;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    public Department addDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        department.setDepartmentId(id);
        return departmentRepository.update(department);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return departmentRepository.findDoctorsByDepartmentId(departmentId);
    }

    public int getDepartmentStaffCount(Long departmentId) {
        Department department = departmentRepository.findById(departmentId);
        return department != null ? department.getStaffCount() : 0;
    }
}