-- Clear existing data (optional - use with caution in production)
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE patients;
TRUNCATE TABLE doctors;
TRUNCATE TABLE appointments;
TRUNCATE TABLE departments;
TRUNCATE TABLE staff;
TRUNCATE TABLE prescriptions;
TRUNCATE TABLE medical_records;
TRUNCATE TABLE billing;
TRUNCATE TABLE rooms;
TRUNCATE TABLE admissions;
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Departments
INSERT INTO departments (name, description, location, head_doctor_id, staff_count, contact_number) VALUES
('Cardiology', 'Heart and cardiovascular system', 'Floor 1, West Wing', NULL, 12, 'x1201'),
('Neurology', 'Nervous system disorders', 'Floor 2, East Wing', NULL, 8, 'x2201'),
('Pediatrics', 'Child healthcare', 'Floor 1, East Wing', NULL, 15, 'x1101'),
('Orthopedics', 'Musculoskeletal system', 'Floor 3, West Wing', NULL, 10, 'x3201'),
('Emergency', 'Emergency care', 'Ground Floor', NULL, 20, 'x0001');

-- Insert Doctors
INSERT INTO doctors (first_name, last_name, specialization, phone_number, email, joining_date, qualification, department_id, license_number, years_of_experience) VALUES
('Sarah', 'Williams', 'Cardiologist', '1112223333', 'sarah.w@hospital.com', '2015-06-10', 'MD, Cardiology', 1, 'CARD12345', 12),
('Michael', 'Brown', 'Neurologist', '4445556666', 'michael.b@hospital.com', '2018-03-15', 'PhD, Neurology', 2, 'NEURO67890', 8),
('Emily', 'Davis', 'Pediatrician', '7778889999', 'emily.d@hospital.com', '2020-01-20', 'MD, Pediatrics', 3, 'PEDIA54321', 5),
('Robert', 'Johnson', 'Orthopedic Surgeon', '1234567890', 'robert.j@hospital.com', '2017-11-05', 'MD, Orthopedics', 4, 'ORTHO98765', 9),
('Jennifer', 'Lee', 'Emergency Physician', '0987654321', 'jennifer.l@hospital.com', '2019-05-15', 'MD, Emergency Medicine', 5, 'EMERG45678', 6);

-- Update Departments with head doctors
UPDATE departments SET head_doctor_id = 1 WHERE department_id = 1;
UPDATE departments SET head_doctor_id = 2 WHERE department_id = 2;
UPDATE departments SET head_doctor_id = 3 WHERE department_id = 3;
UPDATE departments SET head_doctor_id = 4 WHERE department_id = 4;
UPDATE departments SET head_doctor_id = 5 WHERE department_id = 5;

-- Insert Patients
INSERT INTO patients (first_name, last_name, date_of_birth, gender, address, phone_number, email, blood_type, registration_date, insurance_provider, insurance_policy_number) VALUES
('John', 'Doe', '1980-05-15', 'Male', '123 Main St, Anytown', '5551234567', 'john.doe@example.com', 'A+', CURDATE(), 'Blue Cross', 'BC123456789'),
('Jane', 'Smith', '1975-08-22', 'Female', '456 Oak Ave, Somewhere', '5552345678', 'jane.smith@example.com', 'B+', CURDATE(), 'Aetna', 'AET987654321'),
('Michael', 'Johnson', '1990-11-30', 'Male', '789 Pine Rd, Nowhere', '5553456789', 'michael.j@example.com', 'O-', CURDATE(), 'Medicare', 'MCARE456789123'),
('Emily', 'Wilson', '1985-03-10', 'Female', '321 Elm Blvd, Anywhere', '5554567890', 'emily.w@example.com', 'AB+', CURDATE(), 'United Health', 'UHG789123456'),
('David', 'Brown', '1972-12-25', 'Male', '654 Maple Ln, Everywhere', '5555678901', 'david.b@example.com', 'A-', CURDATE(), 'Cigna', 'CIGNA321654987');

-- Insert Staff
INSERT INTO staff (first_name, last_name, role, phone_number, email, joining_date, department_id, address, qualification, emergency_contact) VALUES
('Alice', 'Johnson', 'ADMIN', '5551112222', 'alice.johnson@hospital.com', '2020-01-15', NULL, '123 Admin Building', 'MBA Healthcare Management', '5550001111'),
('Maria', 'Rodriguez', 'NURSE', '5552223333', 'maria.rodriguez@hospital.com', '2021-02-15', 1, '456 Staff Quarters', 'RN, BSN', '5550002222'),
('James', 'Wilson', 'RECEPTIONIST', '5553334444', 'james.wilson@hospital.com', '2020-07-01', NULL, '789 Front Desk Area', 'Diploma in Healthcare Administration', '5550003333'),
('Lisa', 'Chen', 'LAB_TECH', '5554445555', 'lisa.chen@hospital.com', '2022-01-10', 2, '321 Laboratory Building', 'BSc Medical Technology', '5550004444'),
('Robert', 'Martinez', 'PHARMACIST', '5555556666', 'robert.martinez@hospital.com', '2021-09-05', 3, '654 Pharmacy Wing', 'PharmD', '5550005555');

-- Insert Rooms
INSERT INTO rooms (room_number, room_type, status, department_id, capacity, hourly_rate, floor, special_features) VALUES
('101A', 'Consultation', 'AVAILABLE', 1, 4, 50.00, '1', 'ECG Machine, Examination Table'),
('102B', 'Examination', 'OCCUPIED', 1, 2, 75.00, '1', 'Ultrasound, Blood Pressure Monitor'),
('201A', 'Treatment', 'AVAILABLE', 2, 3, 60.00, '2', 'Neurological Examination Tools'),
('202B', 'ICU', 'RESERVED', 2, 1, 200.00, '2', 'Ventilator, Cardiac Monitor'),
('301A', 'Operating', 'MAINTENANCE', 4, 5, 300.00, '3', 'Surgical Lights, Sterile Equipment');

-- Insert Appointments
INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, status, description, duration_minutes, appointment_type) VALUES
(1, 1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '09:00:00', 'SCHEDULED', 'Heart checkup for hypertension', 30, 'CHECKUP'),
(2, 2, DATE_ADD(CURDATE(), INTERVAL 2 DAY), '10:30:00', 'SCHEDULED', 'Migraine consultation', 45, 'CONSULTATION'),
(3, 3, DATE_ADD(CURDATE(), INTERVAL 3 DAY), '14:00:00', 'SCHEDULED', 'Ear infection follow-up', 15, 'FOLLOWUP'),
(4, 4, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '11:00:00', 'CONFIRMED', 'Orthopedic consultation', 30, 'CONSULTATION'),
(5, 1, DATE_ADD(CURDATE(), INTERVAL 5 DAY), '13:30:00', 'PENDING', 'Cardiac follow-up', 20, 'FOLLOWUP');

-- Insert Admissions
INSERT INTO admissions (patient_id, room_id, admission_date, discharge_date, reason, status, attending_doctor_id, notes, total_charges) VALUES
(1, 2, DATE_SUB(CURDATE(), INTERVAL 2 DAY), NULL, 'Heart monitoring', 'ADMITTED', 1, 'Stable condition, needs observation', 1500.00),
(3, 4, DATE_SUB(CURDATE(), INTERVAL 1 DAY), NULL, 'Severe migraine', 'ADMITTED', 2, 'Needs neurological evaluation', 2000.00),
(5, 3, DATE_SUB(CURDATE(), INTERVAL 3 DAY), CURDATE(), 'Broken arm', 'DISCHARGED', 4, 'Cast applied, follow-up in 2 weeks', 3200.00);

-- Insert Prescriptions
INSERT INTO prescriptions (patient_id, doctor_id, prescription_date, medication, dosage, instructions, valid_until, status, notes) VALUES
(1, 1, CURDATE(), 'Atorvastatin', '20mg', 'Take once daily at bedtime', DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'ACTIVE', 'For cholesterol and hypertension control'),
(2, 2, CURDATE(), 'Sumatriptan', '50mg', 'Take as needed for migraine', DATE_ADD(CURDATE(), INTERVAL 90 DAY), 'ACTIVE', 'Max 2 doses per day'),
(3, 3, CURDATE(), 'Amoxicillin', '250mg', 'Take 3 times daily for 7 days', DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'ACTIVE', 'For ear infection'),
(4, 4, CURDATE(), 'Ibuprofen', '400mg', 'Take twice daily with food', DATE_ADD(CURDATE(), INTERVAL 14 DAY), 'ACTIVE', 'For joint pain relief'),
(5, 1, CURDATE(), 'Lisinopril', '10mg', 'Take once daily in morning', DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'ACTIVE', 'Blood pressure medication');

-- Insert Medical Records
INSERT INTO medical_records (patient_id, doctor_id, record_date, diagnosis, treatment, notes, allergies, blood_pressure, temperature, heart_rate) VALUES
(1, 1, CURDATE(), 'Hypertension', 'Lifestyle changes and medication', 'Patient advised to reduce salt intake', 'Penicillin', '140/90', 98.6, 72),
(2, 2, CURDATE(), 'Chronic Migraine', 'Prescribed pain relief medication', 'Patient reports 3 episodes per week', 'None', '110/70', 98.4, 68),
(3, 3, CURDATE(), 'Otitis Media', 'Antibiotics prescribed', 'Right ear infection, improving', 'None', '115/75', 99.1, 80),
(4, 4, CURDATE(), 'Osteoarthritis', 'Anti-inflammatory medication', 'Knee joint pain, mild swelling', 'Aspirin', '125/85', 98.2, 75),
(5, 1, CURDATE(), 'Cardiac Risk Assessment', 'Preventive care plan', 'Family history of heart disease', 'None', '130/85', 98.5, 78);

-- Insert Billing
INSERT INTO billing (patient_id, appointment_id, amount, bill_date, status, payment_method, payment_date, insurance_claim_id, description) VALUES
(1, 1, 150.00, CURDATE(), 'PAID', 'CREDIT_CARD', CURDATE(), 'CLM12345', 'Cardiology consultation'),
(2, 2, 200.00, CURDATE(), 'PENDING', NULL, NULL, 'CLM23456', 'Neurology consultation'),
(3, 3, 75.00, CURDATE(), 'PAID', 'INSURANCE', CURDATE(), 'CLM34567', 'Pediatric follow-up'),
(4, 4, 180.00, CURDATE(), 'PAID', 'BANK_TRANSFER', CURDATE(), 'CLM45678', 'Orthopedic consultation'),
(5, 5, 120.00, CURDATE(), 'UNPAID', NULL, NULL, NULL, 'Cardiac follow-up visit');