package com.example.demo.repository;

import com.example.demo.model.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BillingRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_SQL = "INSERT INTO billing (patient_id, appointment_id, amount, bill_date, status, payment_method, payment_date, insurance_claim_id, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM billing";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM billing WHERE bill_id = ?";
    private static final String UPDATE_SQL = "UPDATE billing SET amount = ?, status = ?, payment_method = ?, payment_date = ?, insurance_claim_id = ?, description = ? WHERE bill_id = ?";
    private static final String DELETE_SQL = "DELETE FROM billing WHERE bill_id = ?";
    private static final String FIND_BY_PATIENT_SQL = "SELECT * FROM billing WHERE patient_id = ?";
    private static final String FIND_BY_STATUS_SQL = "SELECT * FROM billing WHERE status = ?";

    public Billing save(Billing billing) {
        jdbcTemplate.update(INSERT_SQL,
                billing.getPatientId(),
                billing.getAppointmentId(),
                billing.getAmount(),
                billing.getBillDate(),
                billing.getStatus(),
                billing.getPaymentMethod(),
                billing.getPaymentDate(),
                billing.getInsuranceClaimId(),
                billing.getDescription());
        return billing;
    }

    public List<Billing> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, new BillingRowMapper());
    }

    public Billing findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_SQL, new Object[]{id}, new BillingRowMapper());
    }

    public Billing update(Billing billing) {
        jdbcTemplate.update(UPDATE_SQL,
                billing.getAmount(),
                billing.getStatus(),
                billing.getPaymentMethod(),
                billing.getPaymentDate(),
                billing.getInsuranceClaimId(),
                billing.getDescription(),
                billing.getBillId());
        return billing;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    public List<Billing> findByPatientId(Long patientId) {
        return jdbcTemplate.query(FIND_BY_PATIENT_SQL,
                new Object[]{patientId},
                new BillingRowMapper());
    }

    public List<Billing> findByStatus(String status) {
        return jdbcTemplate.query(FIND_BY_STATUS_SQL,
                new Object[]{status},
                new BillingRowMapper());
    }

    public double getMonthlyRevenue(int year, int month) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM billing " +
                "WHERE YEAR(bill_date) = ? AND MONTH(bill_date) = ? " +
                "AND status = 'PAID'";
        return jdbcTemplate.queryForObject(sql, Double.class, year, month);
    }

    public Map<String, Double> getRevenueByDepartment(int year, int month) {
        String sql = "SELECT d.name AS department, COALESCE(SUM(b.amount), 0) AS revenue " +
                "FROM billing b " +
                "JOIN admissions a ON b.admission_id = a.admission_id " +
                "JOIN departments d ON a.department_id = d.department_id " +
                "WHERE YEAR(b.bill_date) = ? AND MONTH(b.bill_date) = ? " +
                "AND b.status = 'PAID' " +
                "GROUP BY d.name";

        return jdbcTemplate.query(sql, new Object[]{year, month},
                rs -> {
                    Map<String, Double> result = new HashMap<>();
                    while (rs.next()) {
                        result.put(rs.getString("department"), rs.getDouble("revenue"));
                    }
                    return result;
                });
    }

    private static final class BillingRowMapper implements RowMapper<Billing> {
        @Override
        public Billing mapRow(ResultSet rs, int rowNum) throws SQLException {
            Billing billing = new Billing();
            billing.setBillId(rs.getLong("bill_id"));
            billing.setPatientId(rs.getLong("patient_id"));
            billing.setAppointmentId(rs.getLong("appointment_id"));
            billing.setAmount(rs.getDouble("amount"));
            billing.setBillDate(rs.getDate("bill_date"));
            billing.setStatus(rs.getString("status"));
            billing.setPaymentMethod(rs.getString("payment_method"));
            billing.setPaymentDate(rs.getDate("payment_date"));
            billing.setInsuranceClaimId(rs.getString("insurance_claim_id"));
            billing.setDescription(rs.getString("description"));
            return billing;
        }
    }
}