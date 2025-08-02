package com.example.demo.model;

import java.util.Date;

public class Admission {
    private Long admissionId;
    private Long patientId;
    private Long roomId;
    private Date admissionDate;
    private Date dischargeDate;
    private String reason;
    private String status;
    private Long attendingDoctorId;
    private String notes;
    private Double totalCharges;

    // Constructors
    public Admission() {
        this.admissionDate = new Date();
        this.status = "ADMITTED";
    }

    public Admission(Long admissionId, Long patientId, Long roomId,
                     Date admissionDate, String reason) {
        this();
        this.admissionId = admissionId;
        this.patientId = patientId;
        this.roomId = roomId;
        this.admissionDate = admissionDate != null ? admissionDate : new Date();
        this.reason = reason;
    }

    // Getters and Setters
    public Long getAdmissionId() { return admissionId; }
    public void setAdmissionId(Long admissionId) { this.admissionId = admissionId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
    public Date getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(Date admissionDate) { this.admissionDate = admissionDate; }
    public Date getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(Date dischargeDate) { this.dischargeDate = dischargeDate; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getAttendingDoctorId() { return attendingDoctorId; }
    public void setAttendingDoctorId(Long attendingDoctorId) { this.attendingDoctorId = attendingDoctorId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Double getTotalCharges() { return totalCharges; }
    public void setTotalCharges(Double totalCharges) { this.totalCharges = totalCharges; }
}