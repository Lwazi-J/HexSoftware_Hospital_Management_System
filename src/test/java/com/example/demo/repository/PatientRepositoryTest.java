package com.example.demo.repository;

import com.example.demo.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PatientRepositoryTest {

    private PatientRepository patientRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        testPatient = new Patient();
        testPatient.setPatientId(1L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setDateOfBirth(new Date());
        testPatient.setGender("Male");
        testPatient.setAddress("123 Main St");
        testPatient.setPhoneNumber("555-0123");
        testPatient.setEmail("john.doe@email.com");
        testPatient.setBloodType("O+");
        testPatient.setRegistrationDate(new Date());
        testPatient.setInsuranceProvider("HealthCare Inc");
        testPatient.setInsurancePolicyNumber("HC123456");
    }

    @Test
    void patientModel_ShouldHaveCorrectProperties() {
        assertEquals(1L, testPatient.getPatientId());
        assertEquals("John", testPatient.getFirstName());
        assertEquals("Doe", testPatient.getLastName());
        assertEquals("Male", testPatient.getGender());
        assertEquals("123 Main St", testPatient.getAddress());
        assertEquals("555-0123", testPatient.getPhoneNumber());
        assertEquals("john.doe@email.com", testPatient.getEmail());
        assertEquals("O+", testPatient.getBloodType());
        assertEquals("HealthCare Inc", testPatient.getInsuranceProvider());
        assertEquals("HC123456", testPatient.getInsurancePolicyNumber());
    }
}