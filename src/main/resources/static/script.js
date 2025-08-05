const API_BASE = '/hospital/api';
let currentUser = null;

// Initialize app
document.addEventListener('DOMContentLoaded', function() {
    showWelcomeScreen();
    setupEventListeners();
});

function setupEventListeners() {
    // Login form
    document.getElementById('loginForm').addEventListener('submit', handleLogin);
    
    // Logout button
    document.getElementById('logoutBtn').addEventListener('click', logout);
    
    // Navigation
    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const section = this.dataset.section;
            showSection(section);
            setActiveNav(this);
        });
    });
    
    // Forms
    document.getElementById('patientForm').addEventListener('submit', handlePatientSubmit);
    document.getElementById('departmentForm').addEventListener('submit', handleDepartmentSubmit);
}

// Authentication
async function handleLogin(e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    try {
        const response = await fetch(`${API_BASE}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });
        
        if (response.ok) {
            const data = await response.json();
            currentUser = data;
            document.getElementById('userWelcome').textContent = `Welcome, ${data.fullName}`;
            showDashboard();
            loadAllData();
        } else {
            alert('Invalid credentials');
        }
    } catch (error) {
        alert('Login failed: ' + error.message);
    }
}

function logout() {
    currentUser = null;
    showWelcomeScreen();
}

function showWelcomeScreen() {
    document.getElementById('welcomeScreen').style.display = 'flex';
    document.getElementById('loginModal').classList.add('hidden');
    document.getElementById('dashboard').classList.add('hidden');
}

function showLoginScreen() {
    document.getElementById('welcomeScreen').style.display = 'none';
    document.getElementById('loginModal').classList.remove('hidden');
    document.getElementById('loginModal').style.display = 'flex';
}

function showDashboard() {
    document.getElementById('welcomeScreen').style.display = 'none';
    document.getElementById('loginModal').style.display = 'none';
    document.getElementById('dashboard').classList.remove('hidden');
}

// Navigation
function showSection(sectionName) {
    document.querySelectorAll('.content-section').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionName).classList.add('active');
    
    // Load data for the section
    switch(sectionName) {
        case 'patients': loadPatients(); break;
        case 'doctors': loadDoctors(); break;
        case 'appointments': loadAppointments(); break;
        case 'departments': loadDepartments(); break;
        case 'staff': loadStaff(); break;
        case 'prescriptions': loadPrescriptions(); break;
    }
}

function setActiveNav(activeLink) {
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
    });
    activeLink.classList.add('active');
}

// Modal functions
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'flex';
    if (modalId === 'appointmentModal' || modalId === 'prescriptionModal') {
        loadPatientsAndDoctorsForDropdowns();
    }
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
    document.getElementById(modalId.replace('Modal', 'Form')).reset();
}

// Data loading functions
async function loadAllData() {
    await Promise.all([
        loadPatients(),
        loadDepartments(),
        loadDoctors(),
        loadAppointments(),
        loadStaff(),
        loadPrescriptions()
    ]);
}

async function loadPatients() {
    try {
        const response = await fetch(`${API_BASE}/patients`);
        const patients = await response.json();
        displayPatients(patients);
    } catch (error) {
        console.error('Error loading patients:', error);
    }
}

async function loadDepartments() {
    try {
        const response = await fetch(`${API_BASE}/departments`);
        const departments = await response.json();
        displayDepartments(departments);
    } catch (error) {
        console.error('Error loading departments:', error);
    }
}

async function loadDoctors() {
    try {
        const response = await fetch(`${API_BASE}/doctors`);
        const doctors = await response.json();
        displayDoctors(doctors);
    } catch (error) {
        console.error('Error loading doctors:', error);
    }
}

async function loadAppointments() {
    try {
        const response = await fetch(`${API_BASE}/appointments`);
        const appointments = await response.json();
        displayAppointments(appointments);
    } catch (error) {
        console.error('Error loading appointments:', error);
    }
}

async function loadStaff() {
    try {
        const response = await fetch(`${API_BASE}/staff`);
        const staff = await response.json();
        displayStaff(staff);
    } catch (error) {
        console.error('Error loading staff:', error);
    }
}

async function loadPrescriptions() {
    try {
        const response = await fetch(`${API_BASE}/prescriptions`);
        const prescriptions = await response.json();
        displayPrescriptions(prescriptions);
    } catch (error) {
        console.error('Error loading prescriptions:', error);
    }
}

// Display functions
function displayPatients(patients) {
    const tbody = document.querySelector('#patientsTable tbody');
    tbody.innerHTML = '';
    
    patients.forEach(patient => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${patient.patientId}</td>
            <td>${patient.firstName} ${patient.lastName}</td>
            <td>${patient.phoneNumber}</td>
            <td>${patient.email || 'N/A'}</td>
            <td>${patient.bloodType || 'N/A'}</td>
            <td>
                <button class="btn-edit" onclick="editPatient(${patient.patientId})">Edit</button>
                <button class="btn-danger" onclick="deletePatient(${patient.patientId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayDepartments(departments) {
    const tbody = document.querySelector('#departmentsTable tbody');
    tbody.innerHTML = '';
    
    departments.forEach(dept => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${dept.departmentId}</td>
            <td>${dept.name}</td>
            <td>${dept.description || 'N/A'}</td>
            <td>${dept.location || 'N/A'}</td>
            <td>${dept.staffCount || 0}</td>
            <td>
                <button class="btn-edit" onclick="editDepartment(${dept.departmentId})">Edit</button>
                <button class="btn-danger" onclick="deleteDepartment(${dept.departmentId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayDoctors(doctors) {
    const tbody = document.querySelector('#doctorsTable tbody');
    tbody.innerHTML = '';
    
    doctors.forEach(doctor => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${doctor.doctorId}</td>
            <td>${doctor.firstName} ${doctor.lastName}</td>
            <td>${doctor.specialization}</td>
            <td>${doctor.phoneNumber}</td>
            <td>${doctor.email}</td>
            <td>
                <button class="btn-edit" onclick="editDoctor(${doctor.doctorId})">Edit</button>
                <button class="btn-danger" onclick="deleteDoctor(${doctor.doctorId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayAppointments(appointments) {
    const tbody = document.querySelector('#appointmentsTable tbody');
    tbody.innerHTML = '';
    
    appointments.forEach(apt => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${apt.appointmentId}</td>
            <td>Patient ${apt.patientId}</td>
            <td>Doctor ${apt.doctorId}</td>
            <td>${apt.appointmentDate}</td>
            <td>${apt.appointmentTime}</td>
            <td><span class="status-badge status-${apt.status.toLowerCase()}">${apt.status}</span></td>
            <td>
                <button class="btn-edit" onclick="editAppointment(${apt.appointmentId})">Edit</button>
                <button class="btn-danger" onclick="deleteAppointment(${apt.appointmentId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayStaff(staff) {
    const tbody = document.querySelector('#staffTable tbody');
    tbody.innerHTML = '';
    
    staff.forEach(member => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${member.staffId}</td>
            <td>${member.firstName} ${member.lastName}</td>
            <td>${member.role}</td>
            <td>${member.phoneNumber}</td>
            <td>${member.email}</td>
            <td>
                <button class="btn-edit" onclick="editStaff(${member.staffId})">Edit</button>
                <button class="btn-danger" onclick="deleteStaff(${member.staffId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function displayPrescriptions(prescriptions) {
    const tbody = document.querySelector('#prescriptionsTable tbody');
    tbody.innerHTML = '';
    
    prescriptions.forEach(prescription => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${prescription.prescriptionId}</td>
            <td>Patient ${prescription.patientId}</td>
            <td>Doctor ${prescription.doctorId}</td>
            <td>${prescription.medication}</td>
            <td>${prescription.dosage}</td>
            <td><span class="status-badge status-${prescription.status.toLowerCase()}">${prescription.status}</span></td>
            <td>
                <button class="btn-edit" onclick="editPrescription(${prescription.prescriptionId})">Edit</button>
                <button class="btn-danger" onclick="deletePrescription(${prescription.prescriptionId})">Delete</button>
            </td>
        `;
        tbody.appendChild(row);
    });
}

// Form handlers
async function handlePatientSubmit(e) {
    e.preventDefault();
    
    const patientData = {
        firstName: document.getElementById('patientFirstName').value,
        lastName: document.getElementById('patientLastName').value,
        dateOfBirth: document.getElementById('patientDOB').value,
        gender: document.getElementById('patientGender').value,
        address: document.getElementById('patientAddress').value,
        phoneNumber: document.getElementById('patientPhone').value,
        email: document.getElementById('patientEmail').value,
        bloodType: document.getElementById('patientBloodType').value,
        registrationDate: document.getElementById('patientRegDate').value,
        insuranceProvider: document.getElementById('patientInsurance').value,
        insurancePolicyNumber: document.getElementById('patientPolicy').value
    };
    
    const patientId = document.getElementById('patientId').value;
    const method = patientId ? 'PUT' : 'POST';
    const url = patientId ? `${API_BASE}/patients/${patientId}` : `${API_BASE}/patients`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(patientData)
        });
        
        if (response.ok) {
            closeModal('patientModal');
            loadPatients();
            alert('Patient saved successfully!');
        } else {
            alert('Error saving patient');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

async function handleDepartmentSubmit(e) {
    e.preventDefault();
    
    const departmentData = {
        name: document.getElementById('departmentName').value,
        description: document.getElementById('departmentDescription').value,
        location: document.getElementById('departmentLocation').value,
        staffCount: parseInt(document.getElementById('departmentStaffCount').value) || 0,
        contactNumber: document.getElementById('departmentContact').value
    };
    
    const departmentId = document.getElementById('departmentId').value;
    const method = departmentId ? 'PUT' : 'POST';
    const url = departmentId ? `${API_BASE}/departments/${departmentId}` : `${API_BASE}/departments`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(departmentData)
        });
        
        if (response.ok) {
            closeModal('departmentModal');
            loadDepartments();
            alert('Department saved successfully!');
        } else {
            alert('Error saving department');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

// Delete functions
async function deletePatient(id) {
    if (confirm('Are you sure you want to delete this patient?')) {
        try {
            const response = await fetch(`${API_BASE}/patients/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadPatients();
                alert('Patient deleted successfully!');
            } else {
                alert('Error deleting patient');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

async function deleteDepartment(id) {
    if (confirm('Are you sure you want to delete this department?')) {
        try {
            const response = await fetch(`${API_BASE}/departments/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadDepartments();
                alert('Department deleted successfully!');
            } else {
                alert('Error deleting department');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

// Edit functions (simplified - just open modal with data)
async function editPatient(id) {
    try {
        const response = await fetch(`${API_BASE}/patients/${id}`);
        const patient = await response.json();
        
        document.getElementById('patientId').value = patient.patientId;
        document.getElementById('patientFirstName').value = patient.firstName;
        document.getElementById('patientLastName').value = patient.lastName;
        document.getElementById('patientDOB').value = patient.dateOfBirth;
        document.getElementById('patientGender').value = patient.gender;
        document.getElementById('patientAddress').value = patient.address;
        document.getElementById('patientPhone').value = patient.phoneNumber;
        document.getElementById('patientEmail').value = patient.email || '';
        document.getElementById('patientBloodType').value = patient.bloodType || '';
        document.getElementById('patientRegDate').value = patient.registrationDate;
        document.getElementById('patientInsurance').value = patient.insuranceProvider || '';
        document.getElementById('patientPolicy').value = patient.insurancePolicyNumber || '';
        
        openModal('patientModal');
    } catch (error) {
        alert('Error loading patient data: ' + error.message);
    }
}

async function editDepartment(id) {
    try {
        const response = await fetch(`${API_BASE}/departments/${id}`);
        const department = await response.json();
        
        document.getElementById('departmentId').value = department.departmentId;
        document.getElementById('departmentName').value = department.name;
        document.getElementById('departmentDescription').value = department.description || '';
        document.getElementById('departmentLocation').value = department.location || '';
        document.getElementById('departmentStaffCount').value = department.staffCount || '';
        document.getElementById('departmentContact').value = department.contactNumber || '';
        
        openModal('departmentModal');
    } catch (error) {
        alert('Error loading department data: ' + error.message);
    }
}

// Placeholder functions for other entities
function editDoctor(id) { alert('Doctor edit functionality - to be implemented'); }
function deleteDoctor(id) { alert('Doctor delete functionality - to be implemented'); }
function editAppointment(id) { alert('Appointment edit functionality - to be implemented'); }
function deleteAppointment(id) { alert('Appointment delete functionality - to be implemented'); }
function editStaff(id) { alert('Staff edit functionality - to be implemented'); }
function deleteStaff(id) { alert('Staff delete functionality - to be implemented'); }
function editPrescription(id) { alert('Prescription edit functionality - to be implemented'); }
function deletePrescription(id) { alert('Prescription delete functionality - to be implemented'); }

function loadPatientsAndDoctorsForDropdowns() {
    // Load patients and doctors for appointment/prescription forms
    // Implementation would populate select dropdowns
}