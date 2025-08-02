package com.example.demo.model;

import java.util.Date;

public class Billing {
    private Long billId;
    private Long patientId;
    private Long appointmentId;
    private Double amount;
    private Date billDate;
    private String status;
    private String paymentMethod;
    private Date paymentDate;
    private String insuranceClaimId;
    private String description;

    // Constructors
    public Billing() {
        this.billDate = new Date();
        this.status = "PENDING";
    }

    public Billing(Long billId, Long patientId, Double amount, String status) {
        this();
        this.billId = billId;
        this.patientId = patientId;
        this.amount = amount;
        this.status = status;
    }

    // Getters and Setters
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Long appointmentId) { this.appointmentId = appointmentId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public String getInsuranceClaimId() { return insuranceClaimId; }
    public void setInsuranceClaimId(String insuranceClaimId) { this.insuranceClaimId = insuranceClaimId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}