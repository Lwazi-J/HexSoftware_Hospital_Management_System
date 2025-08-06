package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.addDoctor(doctor);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Integer minExperience) {
        List<Doctor> doctors = doctorService.searchDoctors(name, specialization, departmentId, minExperience);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartment(departmentId);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }
}