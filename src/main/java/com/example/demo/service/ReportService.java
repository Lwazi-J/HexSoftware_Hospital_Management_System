package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private AdmissionService admissionService;

    public Map<String, Object> generateMonthlyReport(int year, int month) {
        Map<String, Object> report = new HashMap<>();

        // Appointments
        List<Appointment> appointments = appointmentService.getAppointmentsByMonth(year, month);
        report.put("appointmentsCount", appointments.size());

        // New patients
        List<Patient> newPatients = patientService.getPatientsRegisteredInMonth(year, month);
        report.put("newPatientsCount", newPatients.size());

        // Revenue
        double monthlyRevenue = billingService.getMonthlyRevenue(year, month);
        report.put("monthlyRevenue", monthlyRevenue);
        report.put("revenueByDepartment", billingService.getRevenueByDepartment(year, month));

        // Admissions
        List<Admission> admissions = admissionService.getAdmissionsByMonth(year, month);
        report.put("admissionsCount", admissions.size());

        return report;
    }
}