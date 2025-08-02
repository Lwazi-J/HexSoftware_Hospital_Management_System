package com.example.demo.service;

import com.example.demo.model.MedicalRecord;
import com.example.demo.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }

    public MedicalRecord addMedicalRecord(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }

    public MedicalRecord updateMedicalRecord(Long id, MedicalRecord record) {
        record.setRecordId(id);
        return medicalRecordRepository.update(record);
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }

    public List<MedicalRecord> getMedicalRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    public List<MedicalRecord> getRecentMedicalRecords(Long patientId, int count) {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patientId);
        return records.stream()
                .sorted((r1, r2) -> r2.getRecordDate().compareTo(r1.getRecordDate()))
                .limit(count)
                .toList();
    }
}