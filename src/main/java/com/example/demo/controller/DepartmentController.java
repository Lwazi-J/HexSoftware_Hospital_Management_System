package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.addDepartment(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable Long id,
            @RequestBody @Valid Department department) {
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable Long id) {
        List<Doctor> doctors = departmentService.getDoctorsByDepartment(id);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Department>> searchDepartments(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer minStaffCount) {
        List<Department> departments = departmentService.searchDepartments(name, location, minStaffCount);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Department>> getDepartmentsByLocation(@PathVariable String location) {
        List<Department> departments = departmentService.getDepartmentsByLocation(location);
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}