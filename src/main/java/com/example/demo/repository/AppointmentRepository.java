package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AppointmentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, description, duration_minutes, appointment_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM appointments";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM appointments WHERE appointment_id = ?";
    private static final String UPDATE_SQL = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, status = ?, description = ?, duration_minutes = ?, appointment_type = ? WHERE appointment_id = ?";
    private static final String DELETE_SQL = "DELETE FROM appointments WHERE appointment_id = ?";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM appointments WHERE patient_id = ?";
    private static final String FIND_BY_DOCTOR_SQL = "SELECT * FROM appointments WHERE doctor_id = ?";
    private static final String UPDATE_STATUS_SQL = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

    public Appointment save(Appointment appointment) {
        jdbcTemplate.update(INSERT_SQL,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getDescription(),
                appointment.getDurationMinutes(),
                appointment.getAppointmentType());
        return appointment;
    }

    public List<Appointment> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new AppointmentRowMapper());
    }

    public Appointment findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new AppointmentRowMapper());
    }

    public Appointment update(Appointment appointment) {
        jdbcTemplate.update(UPDATE_SQL,
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus(),
                appointment.getDescription(),
                appointment.getDurationMinutes(),
                appointment.getAppointmentType(),
                appointment.getAppointmentId());
        return appointment;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Appointment> findByPatientId(Long patientId) {
        return jdbcTemplate.query(FIND_BY_PATIENT_SQL,
                new Object[]{patientId},
                new AppointmentRowMapper());
    }

    public List<Appointment> findByDoctorId(Long doctorId) {
        return jdbcTemplate.query(FIND_BY_DOCTOR_SQL,
                new Object[]{doctorId},
                new AppointmentRowMapper());
    }

    public Appointment updateStatus(Long id, String status) {
        jdbcTemplate.update(UPDATE_STATUS_SQL, status, id);
        return findById(id);
    }

    public List<Appointment> findByAppointmentDate(LocalDate date) {
        String sql = "SELECT * FROM appointments WHERE appointment_date = ?";
        return jdbcTemplate.query(sql, new Object[]{date}, new AppointmentRowMapper());
    }

    public List<Appointment> findByMonth(int year, int month) {
        String sql = "SELECT a.* FROM appointments a " +
                "WHERE YEAR(a.appointment_date) = ? AND MONTH(a.appointment_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{year, month}, new AppointmentRowMapper());
    }

    public List<Appointment> searchAppointments(String date, String status, String type, Long patientId, Long doctorId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM appointments WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND DATE(appointment_date) = ?");
            params.add(date.trim());
        }

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND LOWER(status) = LOWER(?)");
            params.add(status.trim());
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND LOWER(appointment_type) LIKE LOWER(?)");
            params.add("%" + type.trim() + "%");
        }

        if (patientId != null) {
            sql.append(" AND patient_id = ?");
            params.add(patientId);
        }

        if (doctorId != null) {
            sql.append(" AND doctor_id = ?");
            params.add(doctorId);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), new AppointmentRowMapper());
    }

    public List<Appointment> findByDateString(String date) {
        String sql = "SELECT * FROM appointments WHERE DATE(appointment_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{date}, new AppointmentRowMapper());
    }

    public List<Appointment> findByStatus(String status) {
        String sql = "SELECT * FROM appointments WHERE LOWER(status) = LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{status}, new AppointmentRowMapper());
    }

    private static final class AppointmentRowMapper implements RowMapper<Appointment> {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAppointmentId(rs.getLong("appointment_id"));
            appointment.setPatientId(rs.getLong("patient_id"));
            appointment.setDoctorId(rs.getLong("doctor_id"));
            appointment.setAppointmentDate(rs.getDate("appointment_date"));
            appointment.setAppointmentTime(rs.getString("appointment_time"));
            appointment.setStatus(rs.getString("status"));
            appointment.setDescription(rs.getString("description"));
            appointment.setDurationMinutes(rs.getInt("duration_minutes"));
            appointment.setAppointmentType(rs.getString("appointment_type"));
            return appointment;
        }
    }
}