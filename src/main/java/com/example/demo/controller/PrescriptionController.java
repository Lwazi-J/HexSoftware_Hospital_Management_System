package com.example.demo.controller;

import com.example.demo.model.Prescription;
import com.example.demo.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return new ResponseEntity<>(prescription, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        Prescription createdPrescription = prescriptionService.addPrescription(prescription);
        return new ResponseEntity<>(createdPrescription, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @RequestBody @Valid Prescription prescription) {
        Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescription);
        return ResponseEntity.ok(updatedPrescription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }
}