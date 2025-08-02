package com.example.demo.service;

import com.example.demo.exception.PatientAdmissionException;
import com.example.demo.model.Admission;
import com.example.demo.model.Room;
import com.example.demo.repository.AdmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private RoomService roomService;

    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    public Admission getAdmissionById(Long id) {
        return admissionRepository.findById(id);
    }

    public Admission admitPatient(Admission admission) {
        // Check if room is available
        Room room = roomService.getRoomById(admission.getRoomId());
        if (!"AVAILABLE".equals(room.getStatus())) {
            throw new PatientAdmissionException("Room is not available for admission");
        }

        // Mark room as occupied
        room.setStatus("OCCUPIED");
        roomService.updateRoom(room.getRoomId(), room);

        admission.setAdmissionDate(new Date());
        admission.setStatus("ADMITTED");
        return admissionRepository.save(admission);
    }

    public Admission updateAdmission(Long id, Admission admission) {
        admission.setAdmissionId(id);
        return admissionRepository.update(admission);
    }

    public void dischargePatient(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId);
        admission.setStatus("DISCHARGED");
        admission.setDischargeDate(new Date());
        admissionRepository.update(admission);

        // Mark room as available
        Room room = roomService.getRoomById(admission.getRoomId());
        room.setStatus("AVAILABLE");
        roomService.updateRoom(room.getRoomId(), room);
    }

    public List<Admission> getAdmissionsByPatient(Long patientId) {
        return admissionRepository.findByPatientId(patientId);
    }

    public List<Admission> getCurrentAdmissions() {
        return admissionRepository.findByStatus("ADMITTED");
    }

    public List<Admission> getAdmissionsByMonth(int year, int month) {
        return admissionRepository.findByMonth(year, month);
    }
}