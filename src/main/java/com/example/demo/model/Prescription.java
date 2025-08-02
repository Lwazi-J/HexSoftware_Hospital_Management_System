package com.example.demo.model;

import java.util.Date;

public class Prescription {
    private Long prescriptionId;
    private Long patientId;
    private Long doctorId;
    private Date prescriptionDate;
    private String medication;
    private String dosage;
    private String instructions;
    private Date validUntil;
    private String status;
    private String notes;

    // Constructors
    public Prescription() {
        this.prescriptionDate = new Date();
        this.status = "ACTIVE";
    }

    public Prescription(Long prescriptionId, Long patientId, Long doctorId,
                        String medication, String dosage, String instructions) {
        this();
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    // Getters and Setters
    public Long getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(Long prescriptionId) { this.prescriptionId = prescriptionId; }
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public Date getPrescriptionDate() { return prescriptionDate; }
    public void setPrescriptionDate(Date prescriptionDate) { this.prescriptionDate = prescriptionDate; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public Date getValidUntil() { return validUntil; }
    public void setValidUntil(Date validUntil) { this.validUntil = validUntil; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}