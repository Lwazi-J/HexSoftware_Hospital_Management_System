package com.example.demo.repository;

import com.example.demo.model.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrescriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO prescriptions (patient_id, doctor_id, prescription_date, medication, dosage, instructions, valid_until, status, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM prescriptions";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM prescriptions WHERE prescription_id = ?";
    private static final String UPDATE_SQL = "UPDATE prescriptions SET medication = ?, dosage = ?, instructions = ?, valid_until = ?, status = ?, notes = ? WHERE prescription_id = ?";
    private static final String DELETE_SQL = "DELETE FROM prescriptions WHERE prescription_id = ?";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM prescriptions WHERE patient_id = ?";
    private static final String FIND_BY_DOCTOR_SQL = "SELECT * FROM prescriptions WHERE doctor_id = ?";

    public Prescription save(Prescription prescription) {
        jdbcTemplate.update(INSERT_SQL,
                prescription.getPatientId(),
                prescription.getDoctorId(),
                prescription.getPrescriptionDate(),
                prescription.getMedication(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getValidUntil(),
                prescription.getStatus(),
                prescription.getNotes());
        return prescription;
    }

    public List<Prescription> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new PrescriptionRowMapper());
    }

    public Prescription findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new PrescriptionRowMapper());
    }

    public Prescription update(Prescription prescription) {
        jdbcTemplate.update(UPDATE_SQL,
                prescription.getMedication(),
                prescription.getDosage(),
                prescription.getInstructions(),
                prescription.getValidUntil(),
                prescription.getStatus(),
                prescription.getNotes(),
                prescription.getPrescriptionId());
        return prescription;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Prescription> findByPatientId(Long patientId) {
        return jdbcTemplate.query(FIND_BY_PATIENT_SQL,
                new Object[]{patientId},
                new PrescriptionRowMapper());
    }

    public List<Prescription> findByDoctorId(Long doctorId) {
        return jdbcTemplate.query(FIND_BY_DOCTOR_SQL,
                new Object[]{doctorId},
                new PrescriptionRowMapper());
    }

    public List<Prescription> findActiveByPatientId(Long patientId) {
        String sql = "SELECT * FROM prescriptions WHERE patient_id = ? AND status = 'ACTIVE'";
        return jdbcTemplate.query(sql, new Object[]{patientId}, new PrescriptionRowMapper());
    }

    public List<Prescription> searchPrescriptions(String medication, String status, Long patientId, Long doctorId, String date) {
        StringBuilder sql = new StringBuilder("SELECT * FROM prescriptions WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (medication != null && !medication.trim().isEmpty()) {
            sql.append(" AND LOWER(medication) LIKE LOWER(?)");
            params.add("%" + medication.trim() + "%");
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND LOWER(status) = LOWER(?)");
            params.add(status.trim());
        }

        if (patientId != null) {
            sql.append(" AND patient_id = ?");
            params.add(patientId);
        }

        if (doctorId != null) {
            sql.append(" AND doctor_id = ?");
            params.add(doctorId);
        }

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND DATE(prescription_date) = ?");
            params.add(date.trim());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new PrescriptionRowMapper());
    }

    public List<Prescription> findByMedication(String medication) {
        String sql = "SELECT * FROM prescriptions WHERE LOWER(medication) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{"%" + medication + "%"}, new PrescriptionRowMapper());
    }

    public List<Prescription> findByStatus(String status) {
        String sql = "SELECT * FROM prescriptions WHERE LOWER(status) = LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{status}, new PrescriptionRowMapper());
    }

    private static final class PrescriptionRowMapper implements RowMapper<Prescription> {
        @Override
        public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Prescription prescription = new Prescription();
            prescription.setPrescriptionId(rs.getLong("prescription_id"));
            prescription.setPatientId(rs.getLong("patient_id"));
            prescription.setDoctorId(rs.getLong("doctor_id"));
            prescription.setPrescriptionDate(rs.getDate("prescription_date"));
            prescription.setMedication(rs.getString("medication"));
            prescription.setDosage(rs.getString("dosage"));
            prescription.setInstructions(rs.getString("instructions"));
            prescription.setValidUntil(rs.getDate("valid_until"));
            prescription.setStatus(rs.getString("status"));
            prescription.setNotes(rs.getString("notes"));
            return prescription;
        }
    }
}