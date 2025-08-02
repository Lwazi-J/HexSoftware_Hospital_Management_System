package com.example.demo.service;

import com.example.demo.model.Billing;
import com.example.demo.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    public Billing getBillById(Long id) {
        return billingRepository.findById(id);
    }

    public Billing addBill(Billing bill) {
        return billingRepository.save(bill);
    }

    public Billing updateBill(Long id, Billing bill) {
        bill.setBillId(id);
        return billingRepository.update(bill);
    }

    public void deleteBill(Long id) {
        billingRepository.deleteById(id);
    }

    public List<Billing> getBillsByPatient(Long patientId) {
        return billingRepository.findByPatientId(patientId);
    }

    public List<Billing> getBillsByStatus(String status) {
        return billingRepository.findByStatus(status);
    }

    public Billing processPayment(Long billId, String paymentMethod) {
        Billing bill = billingRepository.findById(billId);
        bill.setStatus("PAID");
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentDate(new Date());
        return billingRepository.update(bill);
    }

    public double getMonthlyRevenue(int year, int month) {
        return billingRepository.getMonthlyRevenue(year, month);
    }

    public Map<String, Double> getRevenueByDepartment(int year, int month) {
        return billingRepository.getRevenueByDepartment(year, month);
    }
}