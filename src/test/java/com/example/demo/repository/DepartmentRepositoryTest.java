package com.example.demo.repository;

import com.example.demo.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentRepositoryTest {

    private DepartmentRepository departmentRepository;

    private Department testDepartment;

    @BeforeEach
    void setUp() {
        testDepartment = new Department();
        testDepartment.setDepartmentId(1L);
        testDepartment.setName("Cardiology");
        testDepartment.setDescription("Heart care department");
        testDepartment.setLocation("Building A");
        testDepartment.setStaffCount(15);
        testDepartment.setContactNumber("555-0789");
    }

    @Test
    void departmentModel_ShouldHaveCorrectProperties() {
        assertEquals(1L, testDepartment.getDepartmentId());
        assertEquals("Cardiology", testDepartment.getName());
        assertEquals("Heart care department", testDepartment.getDescription());
        assertEquals("Building A", testDepartment.getLocation());
        assertEquals(15, testDepartment.getStaffCount());
        assertEquals("555-0789", testDepartment.getContactNumber());
    }
}