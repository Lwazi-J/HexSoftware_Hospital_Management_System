package com.example.demo.repository;

import com.example.demo.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO medical_records (patient_id, doctor_id, record_date, diagnosis, treatment, notes, allergies, blood_pressure, temperature, heart_rate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM medical_records";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM medical_records WHERE record_id = ?";
    private static final String UPDATE_SQL = "UPDATE medical_records SET diagnosis = ?, treatment = ?, notes = ?, allergies = ?, blood_pressure = ?, temperature = ?, heart_rate = ? WHERE record_id = ?";
    private static final String DELETE_SQL = "DELETE FROM medical_records WHERE record_id = ?";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM medical_records WHERE patient_id = ?";

    public MedicalRecord save(MedicalRecord record) {
        jdbcTemplate.update(INSERT_SQL,
                record.getPatientId(),
                record.getDoctorId(),
                record.getRecordDate(),
                record.getDiagnosis(),
                record.getTreatment(),
                record.getNotes(),
                record.getAllergies(),
                record.getBloodPressure(),
                record.getTemperature(),
                record.getHeartRate());
        return record;
    }

    public List<MedicalRecord> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new MedicalRecordRowMapper());
    }

    public MedicalRecord findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new MedicalRecordRowMapper());
    }

    public MedicalRecord update(MedicalRecord record) {
        jdbcTemplate.update(UPDATE_SQL,
                record.getDiagnosis(),
                record.getTreatment(),
                record.getNotes(),
                record.getAllergies(),
                record.getBloodPressure(),
                record.getTemperature(),
                record.getHeartRate(),
                record.getRecordId());
        return record;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<MedicalRecord> findByPatientId(Long patientId) {
        return jdbcTemplate.query(FIND_BY_PATIENT_SQL,
                new Object[]{patientId},
                new MedicalRecordRowMapper());
    }

    private static final class MedicalRecordRowMapper implements RowMapper<MedicalRecord> {
        @Override
        public MedicalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            MedicalRecord record = new MedicalRecord();
            record.setRecordId(rs.getLong("record_id"));
            record.setPatientId(rs.getLong("patient_id"));
            record.setDoctorId(rs.getLong("doctor_id"));
            record.setRecordDate(rs.getDate("record_date"));
            record.setDiagnosis(rs.getString("diagnosis"));
            record.setTreatment(rs.getString("treatment"));
            record.setNotes(rs.getString("notes"));
            record.setAllergies(rs.getString("allergies"));
            record.setBloodPressure(rs.getString("blood_pressure"));
            record.setTemperature(rs.getDouble("temperature"));
            record.setHeartRate(rs.getInt("heart_rate"));
            return record;
        }
    }
}