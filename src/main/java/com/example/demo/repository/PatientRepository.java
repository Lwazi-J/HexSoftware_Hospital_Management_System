package com.example.demo.repository;

import com.example.demo.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PatientRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, address, phone_number, email, blood_type, registration_date, insurance_provider, insurance_policy_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM patients";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM patients WHERE patient_id = ?";
    private static final String UPDATE_SQL = "UPDATE patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, address = ?, phone_number = ?, email = ?, blood_type = ?, insurance_provider = ?, insurance_policy_number = ? WHERE patient_id = ?";
    private static final String DELETE_SQL = "DELETE FROM patients WHERE patient_id = ?";
    private static final String SEARCH_SQL = "SELECT * FROM patients WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?) OR blood_type = ? OR phone_number = ?";

    public Patient save(Patient patient) {
        jdbcTemplate.update(INSERT_SQL,
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getAddress(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getBloodType(),
                patient.getRegistrationDate(),
                patient.getInsuranceProvider(),
                patient.getInsurancePolicyNumber());
        return patient;
    }

    public List<Patient> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new PatientRowMapper());
    }

    public Patient findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new PatientRowMapper());
    }

    public Patient update(Patient patient) {
        jdbcTemplate.update(UPDATE_SQL,
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getAddress(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getBloodType(),
                patient.getInsuranceProvider(),
                patient.getInsurancePolicyNumber(),
                patient.getPatientId());
        return patient;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Patient> searchPatients(String name, String bloodType, String phone) {
        String searchTerm = "%" + name + "%";
        return jdbcTemplate.query(SEARCH_SQL,
                new Object[]{searchTerm, searchTerm, bloodType, phone},
                new PatientRowMapper());
    }

    public List<Patient> findByRegistrationDate(LocalDate date) {
        String sql = "SELECT * FROM patients WHERE DATE(registration_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{date}, new PatientRowMapper());
    }

    public List<Patient> findByRegistrationMonth(int year, int month) {
        String sql = "SELECT * FROM patients " +
                "WHERE YEAR(registration_date) = ? AND MONTH(registration_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{year, month}, new PatientRowMapper());
    }

    private static final class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient patient = new Patient();
            patient.setPatientId(rs.getLong("patient_id"));
            patient.setFirstName(rs.getString("first_name"));
            patient.setLastName(rs.getString("last_name"));
            patient.setDateOfBirth(rs.getDate("date_of_birth"));
            patient.setGender(rs.getString("gender"));
            patient.setAddress(rs.getString("address"));
            patient.setPhoneNumber(rs.getString("phone_number"));
            patient.setEmail(rs.getString("email"));
            patient.setBloodType(rs.getString("blood_type"));
            patient.setRegistrationDate(rs.getDate("registration_date"));
            patient.setInsuranceProvider(rs.getString("insurance_provider"));
            patient.setInsurancePolicyNumber(rs.getString("insurance_policy_number"));
            return patient;
        }
    }
}