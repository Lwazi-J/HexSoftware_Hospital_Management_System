package com.example.demo.service;

import com.example.demo.exception.AppointmentConflictException;
import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment addAppointment(Appointment appointment) {
        if (hasSchedulingConflict(appointment)) {
            throw new AppointmentConflictException("Doctor already has an appointment at this time");
        }
        return appointmentRepository.save(appointment);
    }

    // Fixed method with separate ID parameter
    public Appointment updateAppointment(Long id, Appointment appointment) {
        appointment.setAppointmentId(id);
        return appointmentRepository.update(appointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment updateAppointmentStatus(Long id, String status) {
        return appointmentRepository.updateStatus(id, status);
    }

    private boolean hasSchedulingConflict(Appointment newAppointment) {
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorId(newAppointment.getDoctorId());
        return existingAppointments.stream()
                .anyMatch(existing -> existing.getAppointmentDate().equals(newAppointment.getAppointmentDate())
                        && existing.getAppointmentTime().equals(newAppointment.getAppointmentTime())
                        && !existing.getAppointmentId().equals(newAppointment.getAppointmentId()));
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findByAppointmentDate(date);
    }

    public List<Appointment> getAppointmentsByMonth(int year, int month) {
        return appointmentRepository.findByMonth(year, month);
    }

    public List<Appointment> searchAppointments(String date, String status, String type, Long patientId, Long doctorId) {
        return appointmentRepository.searchAppointments(date, status, type, patientId, doctorId);
    }

    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentRepository.findByDateString(date);
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
}