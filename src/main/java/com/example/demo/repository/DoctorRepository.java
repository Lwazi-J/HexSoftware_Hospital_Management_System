package com.example.demo.repository;

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
public class DoctorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO doctors (first_name, last_name, specialization, phone_number, email, joining_date, qualification, department_id, license_number, years_of_experience) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM doctors";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM doctors WHERE doctor_id = ?";
    private static final String UPDATE_SQL = "UPDATE doctors SET first_name = ?, last_name = ?, specialization = ?, phone_number = ?, email = ?, qualification = ?, department_id = ?, license_number = ?, years_of_experience = ? WHERE doctor_id = ?";
    private static final String DELETE_SQL = "DELETE FROM doctors WHERE doctor_id = ?";
    private static final String FIND_BY_SPECIALIZATION_SQL = "SELECT * FROM doctors WHERE specialization = ?";

    public Doctor save(Doctor doctor) {
        jdbcTemplate.update(INSERT_SQL,
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialization(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getJoiningDate(),
                doctor.getQualification(),
                doctor.getDepartmentId(),
                doctor.getLicenseNumber(),
                doctor.getYearsOfExperience());
        return doctor;
    }

    public List<Doctor> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new DoctorRowMapper());
    }

    public Doctor findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new DoctorRowMapper());
    }

    public List<Doctor> findByDepartment(Long departmentId) {
        String sql = "SELECT * FROM doctors WHERE department_id = ?";
        return jdbcTemplate.query(sql, new Object[]{departmentId}, new DoctorRowMapper());
    }

    public Doctor update(Doctor doctor) {
        jdbcTemplate.update(UPDATE_SQL,
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getSpecialization(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getQualification(),
                doctor.getDepartmentId(),
                doctor.getLicenseNumber(),
                doctor.getYearsOfExperience(),
                doctor.getDoctorId());
        return doctor;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Doctor> findBySpecialization(String specialization) {
        return jdbcTemplate.query(FIND_BY_SPECIALIZATION_SQL,
                new Object[]{specialization},
                new DoctorRowMapper());
    }

    public List<Doctor> searchDoctors(String name, String specialization, Long departmentId, Integer minExperience) {
        StringBuilder sql = new StringBuilder("SELECT * FROM doctors WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND (LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?))");
            String searchTerm = "%" + name.trim() + "%";
            params.add(searchTerm);
            params.add(searchTerm);
        }

        if (specialization != null && !specialization.trim().isEmpty()) {
            sql.append(" AND LOWER(specialization) LIKE LOWER(?)");
            params.add("%" + specialization.trim() + "%");
        }

        if (departmentId != null) {
            sql.append(" AND department_id = ?");
            params.add(departmentId);
        }

        if (minExperience != null) {
            sql.append(" AND years_of_experience >= ?");
            params.add(minExperience);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new DoctorRowMapper());
    }

    static final class DoctorRowMapper implements RowMapper<Doctor> {
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
}