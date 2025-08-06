package com.example.demo.service;

import com.example.demo.model.Prescription;
import com.example.demo.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription addPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long id, Prescription prescription) {
        prescription.setPrescriptionId(id);
        return prescriptionRepository.update(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    public List<Prescription> getActivePrescriptions(Long patientId) {
        return prescriptionRepository.findActiveByPatientId(patientId);
    }

    public List<Prescription> searchPrescriptions(String medication, String status, Long patientId, Long doctorId, String date) {
        return prescriptionRepository.searchPrescriptions(medication, status, patientId, doctorId, date);
    }

    public List<Prescription> getPrescriptionsByMedication(String medication) {
        return prescriptionRepository.findByMedication(medication);
    }

    public List<Prescription> getPrescriptionsByStatus(String status) {
        return prescriptionRepository.findByStatus(status);
    }
}