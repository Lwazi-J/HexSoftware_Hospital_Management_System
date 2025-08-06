package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment createdAppointment = appointmentService.addAppointment(appointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointment) {
        Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Appointment appointment = appointmentService.updateAppointmentStatus(id, status);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Appointment>> searchAppointments(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long doctorId) {
        List<Appointment> appointments = appointmentService.searchAppointments(date, status, type, patientId, doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@PathVariable String date) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDate(date);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}