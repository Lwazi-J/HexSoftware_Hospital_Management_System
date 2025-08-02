package com.example.demo.repository;

import com.example.demo.model.Admission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AdmissionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO admissions (patient_id, room_id, admission_date, discharge_date, reason, status, attending_doctor_id, notes, total_charges) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM admissions";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM admissions WHERE admission_id = ?";
    private static final String UPDATE_SQL = "UPDATE admissions SET room_id = ?, discharge_date = ?, reason = ?, status = ?, attending_doctor_id = ?, notes = ?, total_charges = ? WHERE admission_id = ?";
    private static final String DELETE_SQL = "DELETE FROM admissions WHERE admission_id = ?";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM admissions WHERE patient_id = ?";
    private static final String FIND_BY_STATUS_SQL = "SELECT * FROM admissions WHERE status = ?";

    public Admission save(Admission admission) {
        jdbcTemplate.update(INSERT_SQL,
                admission.getPatientId(),
                admission.getRoomId(),
                admission.getAdmissionDate(),
                admission.getDischargeDate(),
                admission.getReason(),
                admission.getStatus(),
                admission.getAttendingDoctorId(),
                admission.getNotes(),
                admission.getTotalCharges());
        return admission;
    }

    public List<Admission> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new AdmissionRowMapper());
    }

    public Admission findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new AdmissionRowMapper());
    }

    public Admission update(Admission admission) {
        jdbcTemplate.update(UPDATE_SQL,
                admission.getRoomId(),
                admission.getDischargeDate(),
                admission.getReason(),
                admission.getStatus(),
                admission.getAttendingDoctorId(),
                admission.getNotes(),
                admission.getTotalCharges(),
                admission.getAdmissionId());
        return admission;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Admission> findByPatientId(Long patientId) {
        return jdbcTemplate.query(FIND_BY_PATIENT_SQL,
                new Object[]{patientId},
                new AdmissionRowMapper());
    }

    public List<Admission> findByStatus(String status) {
        return jdbcTemplate.query(FIND_BY_STATUS_SQL,
                new Object[]{status},
                new AdmissionRowMapper());
    }

    public List<Admission> findByMonth(int year, int month) {
        String sql = "SELECT * FROM admissions " +
                "WHERE YEAR(admission_date) = ? AND MONTH(admission_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{year, month}, new AdmissionRowMapper());
    }

    private static final class AdmissionRowMapper implements RowMapper<Admission> {
        @Override
        public Admission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Admission admission = new Admission();
            admission.setAdmissionId(rs.getLong("admission_id"));
            admission.setPatientId(rs.getLong("patient_id"));
            admission.setRoomId(rs.getLong("room_id"));
            admission.setAdmissionDate(rs.getDate("admission_date"));
            admission.setDischargeDate(rs.getDate("discharge_date"));
            admission.setReason(rs.getString("reason"));
            admission.setStatus(rs.getString("status"));
            admission.setAttendingDoctorId(rs.getLong("attending_doctor_id"));
            admission.setNotes(rs.getString("notes"));
            admission.setTotalCharges(rs.getDouble("total_charges"));
            return admission;
        }
    }
}