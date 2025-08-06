package com.example.demo.repository;

import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO departments (name, description, location, staff_count, contact_number) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM departments";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM departments WHERE department_id = ?";
    private static final String UPDATE_SQL = "UPDATE departments SET name = ?, description = ?, location = ?, staff_count = ?, contact_number = ? WHERE department_id = ?";
    private static final String DELETE_SQL = "DELETE FROM departments WHERE department_id = ?";
    private static final String FIND_DOCTORS_BY_DEPARTMENT_SQL = "SELECT * FROM doctors WHERE department_id = ?";

    public Department save(Department department) {
        jdbcTemplate.update(INSERT_SQL,
                department.getName(),
                department.getDescription(),
                department.getLocation(),
                department.getStaffCount(),
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
                new DoctorRowMapper());
    }

    public List<Department> searchDepartments(String name, String location, Integer minStaffCount) {
        StringBuilder sql = new StringBuilder("SELECT * FROM departments WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND LOWER(name) LIKE LOWER(?)");
            params.add("%" + name.trim() + "%");
        }

        if (location != null && !location.trim().isEmpty()) {
            sql.append(" AND LOWER(location) LIKE LOWER(?)");
            params.add("%" + location.trim() + "%");
        }

        if (minStaffCount != null) {
            sql.append(" AND staff_count >= ?");
            params.add(minStaffCount);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new DepartmentRowMapper());
    }

    public List<Department> findByLocation(String location) {
        String sql = "SELECT * FROM departments WHERE LOWER(location) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{"%" + location + "%"}, new DepartmentRowMapper());
    }

    private static final class DoctorRowMapper implements RowMapper<Doctor> {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(rs.getLong("doctor_id"));
            doctor.setFirstName(rs.getString("first_name"));
            doctor.setLastName(rs.getString("last_name"));
            doctor.setSpecialization(rs.getString("specialization"));
            doctor.setPhoneNumber(rs.getString("phone_number"));
            doctor.setEmail(rs.getString("email"));
            doctor.setJoiningDate(rs.getDate("joining_date"));
            doctor.setQualification(rs.getString("qualification"));
            doctor.setDepartmentId(rs.getLong("department_id"));
            doctor.setLicenseNumber(rs.getString("license_number"));
            doctor.setYearsOfExperience(rs.getInt("years_of_experience"));
            return doctor;
        }
    }

    private static final class DepartmentRowMapper implements RowMapper<Department> {
        @Override
        public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(rs.getLong("department_id"));
            department.setName(rs.getString("name"));
            department.setDescription(rs.getString("description"));
            department.setLocation(rs.getString("location"));
            department.setHeadDoctorId(rs.getObject("head_doctor_id") != null ? String.valueOf(rs.getLong("head_doctor_id")) : null);
            department.setStaffCount(rs.getInt("staff_count"));
            department.setContactNumber(rs.getString("contact_number"));
            return department;
        }
    }
}