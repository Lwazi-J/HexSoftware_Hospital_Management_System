package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO departments (name, description, location, head_doctor_id, staff_count, established_date, contact_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM departments";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM departments WHERE department_id = ?";
    private static final String UPDATE_SQL = "UPDATE departments SET name = ?, description = ?, location = ?, head_doctor_id = ?, staff_count = ?, contact_number = ? WHERE department_id = ?";
    private static final String DELETE_SQL = "DELETE FROM departments WHERE department_id = ?";
    private static final String FIND_DOCTORS_BY_DEPARTMENT_SQL = "SELECT * FROM doctors WHERE department_id = ?";

    public Department save(Department department) {
        jdbcTemplate.update(INSERT_SQL,
                department.getName(),
                department.getDescription(),
                department.getLocation(),
                department.getHeadDoctorId(),
                department.getStaffCount(),
                department.getEstablishedDate(),
                department.getContactNumber());
        return department;
    }

    public List<Department> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new DepartmentRowMapper());
    }

    public Department findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new DepartmentRowMapper());
    }

    public Department update(Department department) {
        jdbcTemplate.update(UPDATE_SQL,
                department.getName(),
                department.getDescription(),
                department.getLocation(),
                department.getHeadDoctorId(),
                department.getStaffCount(),
                department.getContactNumber(),
                department.getDepartmentId());
        return department;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Doctor> findDoctorsByDepartmentId(Long departmentId) {
        return jdbcTemplate.query(FIND_DOCTORS_BY_DEPARTMENT_SQL,
                new Object[]{departmentId},
                new DoctorRepository.DoctorRowMapper());
    }

    private static final class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(rs.getLong("department_id"));
            department.setName(rs.getString("name"));
            department.setDescription(rs.getString("description"));
            department.setLocation(rs.getString("location"));
            department.setHeadDoctorId(String.valueOf(rs.getLong("head_doctor_id")));
            department.setStaffCount(rs.getInt("staff_count"));
            department.setEstablishedDate(rs.getDate("established_date"));
            department.setContactNumber(rs.getString("contact_number"));
            return department;
        }
    }
}