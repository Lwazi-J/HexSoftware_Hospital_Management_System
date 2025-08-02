package com.example.demo.service;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient patient) {
        patient.setPatientId(id);
        return patientRepository.update(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> searchPatients(String name, String bloodType, String phone) {
        return patientRepository.searchPatients(name, bloodType, phone);
    }

    public List<Patient> getPatientsByBloodType(String bloodType) {
        return patientRepository.searchPatients(null, bloodType, null);
    }

    public List<Patient> getPatientsRegisteredOn(LocalDate date) {
        return patientRepository.findByRegistrationDate(date);
    }

    public List<Patient> getPatientsRegisteredInMonth(int year, int month) {
        return patientRepository.findByRegistrationMonth(year, month);
    }
}