package com.example.demo.repository;

import com.example.demo.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StaffRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO staff (first_name, last_name, role, phone_number, email, joining_date, department_id, address, qualification, emergency_contact) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM staff";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM staff WHERE staff_id = ?";
    private static final String UPDATE_SQL = "UPDATE staff SET first_name = ?, last_name = ?, role = ?, phone_number = ?, email = ?, department_id = ?, address = ?, qualification = ?, emergency_contact = ? WHERE staff_id = ?";
    private static final String DELETE_SQL = "DELETE FROM staff WHERE staff_id = ?";
    private static final String FIND_BY_ROLE_SQL = "SELECT * FROM staff WHERE role = ?";
    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM staff WHERE email = ?";
    private static final String FIND_BY_DEPARTMENT_SQL = "SELECT * FROM staff WHERE department_id = ?";

    public Staff save(Staff staff) {
        jdbcTemplate.update(INSERT_SQL,
                staff.getFirstName(),
                staff.getLastName(),
                staff.getRole(),
                staff.getPhoneNumber(),
                staff.getEmail(),
                staff.getJoiningDate(),
                staff.getDepartmentId(),
                staff.getAddress(),
                staff.getQualification(),
                staff.getEmergencyContact());
        return staff;
    }

    public List<Staff> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new StaffRowMapper());
    }

    public Staff findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new StaffRowMapper());
    }

    public Staff update(Staff staff) {
        jdbcTemplate.update(UPDATE_SQL,
                staff.getFirstName(),
                staff.getLastName(),
                staff.getRole(),
                staff.getPhoneNumber(),
                staff.getEmail(),
                staff.getDepartmentId(),
                staff.getAddress(),
                staff.getQualification(),
                staff.getEmergencyContact(),
                staff.getStaffId());
        return staff;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public Staff findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_EMAIL_SQL,
                    new Object[]{email},
                    new StaffRowMapper());
        } catch (Exception e) {
            return null; // or handle the exception as needed
        }
    }

    public List<Staff> findByDepartmentId(Long departmentId) {
        return jdbcTemplate.query(FIND_BY_DEPARTMENT_SQL,
                new Object[]{departmentId},
                new StaffRowMapper());
    }


    public List<Staff> findByRole(String role) {
        return jdbcTemplate.query(FIND_BY_ROLE_SQL,
                new Object[]{role},
                new StaffRowMapper());
    }

    private static final class StaffRowMapper implements RowMapper<Staff> {
        @Override
        public Staff mapRow(ResultSet rs, int rowNum) throws SQLException {
            Staff staff = new Staff();
            staff.setStaffId(rs.getLong("staff_id"));
            staff.setFirstName(rs.getString("first_name"));
            staff.setLastName(rs.getString("last_name"));
            staff.setRole(rs.getString("role"));
            staff.setPhoneNumber(rs.getString("phone_number"));
            staff.setEmail(rs.getString("email"));
            staff.setJoiningDate(rs.getDate("joining_date"));
            staff.setDepartmentId(rs.getLong("department_id"));
            staff.setAddress(rs.getString("address"));
            staff.setQualification(rs.getString("qualification"));
            staff.setEmergencyContact(rs.getString("emergency_contact"));
            return staff;
        }
    }
}