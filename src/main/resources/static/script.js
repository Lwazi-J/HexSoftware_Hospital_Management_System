const API_BASE = '/hospital/api';
let currentUser = null;

// Initialize app
document.addEventListener('DOMContentLoaded', function() {
    initializeTheme();
    showLoginScreen();
    setupEventListeners();
});

// Theme functionality
function initializeTheme() {
    const savedTheme = localStorage.getItem('theme') || 'light';
    document.body.setAttribute('data-theme', savedTheme);
    updateThemeToggle(savedTheme);
}

function toggleTheme() {
    const currentTheme = document.body.getAttribute('data-theme') || 'light';
    const newTheme = currentTheme === 'light' ? 'dark' : 'light';
    
    document.body.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme);
    updateThemeToggle(newTheme);
}

function updateThemeToggle(theme) {
    const icons = [
        'theme-icon', 'theme-icon-header', 'theme-icon-login',
        'theme-icon-patients', 'theme-icon-doctors', 'theme-icon-appointments',
        'theme-icon-departments', 'theme-icon-staff', 'theme-icon-prescriptions'
    ];
    const texts = [
        'theme-text', 'theme-text-header', 'theme-text-login',
        'theme-text-patients', 'theme-text-doctors', 'theme-text-appointments',
        'theme-text-departments', 'theme-text-staff', 'theme-text-prescriptions'
    ];
    
    icons.forEach(iconId => {
        const icon = document.getElementById(iconId);
        if (icon) {
            icon.className = theme === 'dark' ? 'fas fa-sun' : 'fas fa-moon';
        }
    });
    
    texts.forEach(textId => {
        const text = document.getElementById(textId);
        if (text) {
            text.textContent = theme === 'dark' ? 'Light' : 'Dark';
        }
    });
}

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
    document.getElementById('doctorForm').addEventListener('submit', handleDoctorSubmit);
    document.getElementById('appointmentForm').addEventListener('submit', handleAppointmentSubmit);
    document.getElementById('staffForm').addEventListener('submit', handleStaffSubmit);
    document.getElementById('prescriptionForm').addEventListener('submit', handlePrescriptionSubmit);
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
    document.getElementById('dashboard').classList.add('hidden');
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
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
    document.getElementById(modalId).style.display = 'flex';
    if (modalId === 'appointmentModal' || modalId === 'prescriptionModal') {
        loadPatientsAndDoctorsForDropdowns();
    }
    if (modalId === 'doctorModal' || modalId === 'staffModal') {
        loadDepartmentsForDropdown();
    }
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
    document.getElementById(modalId.replace('Modal', 'Form')).reset();
}

// Authentication check function
function checkAuthentication() {
    if (!currentUser) {
        alert('Please login first to access this feature!');
        showWelcomeScreen();
        return false;
    }
    return true;
}

// Data loading functions
async function loadAllData() {
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/patients`);
        const patients = await response.json();
        displayPatients(patients);
    } catch (error) {
        console.error('Error loading patients:', error);
    }
}

async function loadDepartments() {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/departments`);
        const departments = await response.json();
        displayDepartments(departments);
    } catch (error) {
        console.error('Error loading departments:', error);
    }
}

async function loadDoctors() {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/doctors`);
        const doctors = await response.json();
        displayDoctors(doctors);
    } catch (error) {
        console.error('Error loading doctors:', error);
    }
}

async function loadAppointments() {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/appointments`);
        const appointments = await response.json();
        displayAppointments(appointments);
    } catch (error) {
        console.error('Error loading appointments:', error);
    }
}

async function loadStaff() {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/staff`);
        const staff = await response.json();
        displayStaff(staff);
    } catch (error) {
        console.error('Error loading staff:', error);
    }
}

async function loadPrescriptions() {
    if (!checkAuthentication()) return;
    
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
    
    if (!checkAuthentication()) return;
    
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
    
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
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
    if (!checkAuthentication()) return;
    
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

// Form handlers for other entities
async function handleDoctorSubmit(e) {
    e.preventDefault();
    
    if (!checkAuthentication()) return;
    
    const doctorData = {
        firstName: document.getElementById('doctorFirstName').value,
        lastName: document.getElementById('doctorLastName').value,
        specialization: document.getElementById('doctorSpecialization').value,
        phoneNumber: document.getElementById('doctorPhone').value,
        email: document.getElementById('doctorEmail').value,
        joiningDate: document.getElementById('doctorJoiningDate').value,
        qualification: document.getElementById('doctorQualification').value,
        departmentId: document.getElementById('doctorDepartment').value,
        licenseNumber: document.getElementById('doctorLicense').value,
        yearsOfExperience: parseInt(document.getElementById('doctorExperience').value)
    };
    
    const doctorId = document.getElementById('doctorId').value;
    const method = doctorId ? 'PUT' : 'POST';
    const url = doctorId ? `${API_BASE}/doctors/${doctorId}` : `${API_BASE}/doctors`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(doctorData)
        });
        
        if (response.ok) {
            closeModal('doctorModal');
            loadDoctors();
            alert('Doctor saved successfully!');
        } else {
            alert('Error saving doctor');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

async function handleAppointmentSubmit(e) {
    e.preventDefault();
    
    if (!checkAuthentication()) return;
    
    const appointmentData = {
        patientId: document.getElementById('appointmentPatient').value,
        doctorId: document.getElementById('appointmentDoctor').value,
        appointmentDate: document.getElementById('appointmentDate').value,
        appointmentTime: document.getElementById('appointmentTime').value,
        status: document.getElementById('appointmentStatus').value,
        appointmentType: document.getElementById('appointmentType').value,
        durationMinutes: parseInt(document.getElementById('appointmentDuration').value),
        description: document.getElementById('appointmentDescription').value
    };
    
    const appointmentId = document.getElementById('appointmentId').value;
    const method = appointmentId ? 'PUT' : 'POST';
    const url = appointmentId ? `${API_BASE}/appointments/${appointmentId}` : `${API_BASE}/appointments`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(appointmentData)
        });
        
        if (response.ok) {
            closeModal('appointmentModal');
            loadAppointments();
            alert('Appointment saved successfully!');
        } else {
            alert('Error saving appointment');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

async function handleStaffSubmit(e) {
    e.preventDefault();
    
    if (!checkAuthentication()) return;
    
    const staffData = {
        firstName: document.getElementById('staffFirstName').value,
        lastName: document.getElementById('staffLastName').value,
        role: document.getElementById('staffRole').value,
        phoneNumber: document.getElementById('staffPhone').value,
        email: document.getElementById('staffEmail').value,
        joiningDate: document.getElementById('staffJoiningDate').value,
        departmentId: document.getElementById('staffDepartment').value || null,
        address: document.getElementById('staffAddress').value,
        qualification: document.getElementById('staffQualification').value,
        emergencyContact: document.getElementById('staffEmergencyContact').value
    };
    
    const staffId = document.getElementById('staffId').value;
    const method = staffId ? 'PUT' : 'POST';
    const url = staffId ? `${API_BASE}/staff/${staffId}` : `${API_BASE}/staff`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(staffData)
        });
        
        if (response.ok) {
            closeModal('staffModal');
            loadStaff();
            alert('Staff saved successfully!');
        } else {
            alert('Error saving staff');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

async function handlePrescriptionSubmit(e) {
    e.preventDefault();
    
    if (!checkAuthentication()) return;
    
    const prescriptionData = {
        patientId: document.getElementById('prescriptionPatient').value,
        doctorId: document.getElementById('prescriptionDoctor').value,
        prescriptionDate: document.getElementById('prescriptionDate').value,
        medication: document.getElementById('prescriptionMedication').value,
        dosage: document.getElementById('prescriptionDosage').value,
        instructions: document.getElementById('prescriptionInstructions').value,
        validUntil: document.getElementById('prescriptionValidUntil').value,
        status: document.getElementById('prescriptionStatus').value,
        notes: document.getElementById('prescriptionNotes').value
    };
    
    const prescriptionId = document.getElementById('prescriptionId').value;
    const method = prescriptionId ? 'PUT' : 'POST';
    const url = prescriptionId ? `${API_BASE}/prescriptions/${prescriptionId}` : `${API_BASE}/prescriptions`;
    
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(prescriptionData)
        });
        
        if (response.ok) {
            closeModal('prescriptionModal');
            loadPrescriptions();
            alert('Prescription saved successfully!');
        } else {
            alert('Error saving prescription');
        }
    } catch (error) {
        alert('Error: ' + error.message);
    }
}

// Delete functions
async function deleteDoctor(id) {
    if (!checkAuthentication()) return;
    
    if (confirm('Are you sure you want to delete this doctor?')) {
        try {
            const response = await fetch(`${API_BASE}/doctors/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadDoctors();
                alert('Doctor deleted successfully!');
            } else {
                alert('Error deleting doctor');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

async function deleteAppointment(id) {
    if (!checkAuthentication()) return;
    
    if (confirm('Are you sure you want to delete this appointment?')) {
        try {
            const response = await fetch(`${API_BASE}/appointments/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadAppointments();
                alert('Appointment deleted successfully!');
            } else {
                alert('Error deleting appointment');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

async function deleteStaff(id) {
    if (!checkAuthentication()) return;
    
    if (confirm('Are you sure you want to delete this staff member?')) {
        try {
            const response = await fetch(`${API_BASE}/staff/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadStaff();
                alert('Staff deleted successfully!');
            } else {
                alert('Error deleting staff');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

async function deletePrescription(id) {
    if (!checkAuthentication()) return;
    
    if (confirm('Are you sure you want to delete this prescription?')) {
        try {
            const response = await fetch(`${API_BASE}/prescriptions/${id}`, { method: 'DELETE' });
            if (response.ok) {
                loadPrescriptions();
                alert('Prescription deleted successfully!');
            } else {
                alert('Error deleting prescription');
            }
        } catch (error) {
            alert('Error: ' + error.message);
        }
    }
}

async function editDoctor(id) {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/doctors/${id}`);
        const doctor = await response.json();
        
        document.getElementById('doctorId').value = doctor.doctorId;
        document.getElementById('doctorFirstName').value = doctor.firstName;
        document.getElementById('doctorLastName').value = doctor.lastName;
        document.getElementById('doctorSpecialization').value = doctor.specialization;
        document.getElementById('doctorPhone').value = doctor.phoneNumber;
        document.getElementById('doctorEmail').value = doctor.email;
        document.getElementById('doctorJoiningDate').value = doctor.joiningDate;
        document.getElementById('doctorQualification').value = doctor.qualification;
        document.getElementById('doctorDepartment').value = doctor.departmentId || '';
        document.getElementById('doctorLicense').value = doctor.licenseNumber;
        document.getElementById('doctorExperience').value = doctor.yearsOfExperience;
        
        openModal('doctorModal');
    } catch (error) {
        alert('Error loading doctor data: ' + error.message);
    }
}

async function editAppointment(id) {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/appointments/${id}`);
        const appointment = await response.json();
        
        document.getElementById('appointmentId').value = appointment.appointmentId;
        document.getElementById('appointmentPatient').value = appointment.patientId;
        document.getElementById('appointmentDoctor').value = appointment.doctorId;
        document.getElementById('appointmentDate').value = appointment.appointmentDate;
        document.getElementById('appointmentTime').value = appointment.appointmentTime;
        document.getElementById('appointmentStatus').value = appointment.status;
        document.getElementById('appointmentType').value = appointment.appointmentType;
        document.getElementById('appointmentDuration').value = appointment.durationMinutes;
        document.getElementById('appointmentDescription').value = appointment.description || '';
        
        openModal('appointmentModal');
    } catch (error) {
        alert('Error loading appointment data: ' + error.message);
    }
}

async function editStaff(id) {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/staff/${id}`);
        const staff = await response.json();
        
        document.getElementById('staffId').value = staff.staffId;
        document.getElementById('staffFirstName').value = staff.firstName;
        document.getElementById('staffLastName').value = staff.lastName;
        document.getElementById('staffRole').value = staff.role;
        document.getElementById('staffPhone').value = staff.phoneNumber;
        document.getElementById('staffEmail').value = staff.email;
        document.getElementById('staffJoiningDate').value = staff.joiningDate;
        document.getElementById('staffDepartment').value = staff.departmentId || '';
        document.getElementById('staffAddress').value = staff.address || '';
        document.getElementById('staffQualification').value = staff.qualification || '';
        document.getElementById('staffEmergencyContact').value = staff.emergencyContact || '';
        
        openModal('staffModal');
    } catch (error) {
        alert('Error loading staff data: ' + error.message);
    }
}

async function editPrescription(id) {
    if (!checkAuthentication()) return;
    
    try {
        const response = await fetch(`${API_BASE}/prescriptions/${id}`);
        const prescription = await response.json();
        
        document.getElementById('prescriptionId').value = prescription.prescriptionId;
        document.getElementById('prescriptionPatient').value = prescription.patientId;
        document.getElementById('prescriptionDoctor').value = prescription.doctorId;
        document.getElementById('prescriptionDate').value = prescription.prescriptionDate;
        document.getElementById('prescriptionMedication').value = prescription.medication;
        document.getElementById('prescriptionDosage').value = prescription.dosage;
        document.getElementById('prescriptionInstructions').value = prescription.instructions;
        document.getElementById('prescriptionValidUntil').value = prescription.validUntil;
        document.getElementById('prescriptionStatus').value = prescription.status;
        document.getElementById('prescriptionNotes').value = prescription.notes || '';
        
        openModal('prescriptionModal');
    } catch (error) {
        alert('Error loading prescription data: ' + error.message);
    }
}

async function loadPatientsAndDoctorsForDropdowns() {
    try {
        const [patientsResponse, doctorsResponse] = await Promise.all([
            fetch(`${API_BASE}/patients`),
            fetch(`${API_BASE}/doctors`)
        ]);
        
        const patients = await patientsResponse.json();
        const doctors = await doctorsResponse.json();
        
        // Populate appointment dropdowns
        const appointmentPatientSelect = document.getElementById('appointmentPatient');
        const appointmentDoctorSelect = document.getElementById('appointmentDoctor');
        
        // Populate prescription dropdowns
        const prescriptionPatientSelect = document.getElementById('prescriptionPatient');
        const prescriptionDoctorSelect = document.getElementById('prescriptionDoctor');
        
        // Clear existing options
        [appointmentPatientSelect, appointmentDoctorSelect, prescriptionPatientSelect, prescriptionDoctorSelect].forEach(select => {
            if (select) {
                select.innerHTML = select.innerHTML.split('</option>')[0] + '</option>';
            }
        });
        
        // Add patients
        patients.forEach(patient => {
            const option = `<option value="${patient.patientId}">${patient.firstName} ${patient.lastName}</option>`;
            if (appointmentPatientSelect) appointmentPatientSelect.innerHTML += option;
            if (prescriptionPatientSelect) prescriptionPatientSelect.innerHTML += option;
        });
        
        // Add doctors
        doctors.forEach(doctor => {
            const option = `<option value="${doctor.doctorId}">${doctor.firstName} ${doctor.lastName} - ${doctor.specialization}</option>`;
            if (appointmentDoctorSelect) appointmentDoctorSelect.innerHTML += option;
            if (prescriptionDoctorSelect) prescriptionDoctorSelect.innerHTML += option;
        });
    } catch (error) {
        console.error('Error loading dropdowns:', error);
    }
}

async function loadDepartmentsForDropdown() {
    try {
        const response = await fetch(`${API_BASE}/departments`);
        const departments = await response.json();
        
        const doctorDeptSelect = document.getElementById('doctorDepartment');
        const staffDeptSelect = document.getElementById('staffDepartment');
        
        // Clear existing options
        [doctorDeptSelect, staffDeptSelect].forEach(select => {
            if (select) {
                select.innerHTML = select.innerHTML.split('</option>')[0] + '</option>';
            }
        });
        
        // Add departments
        departments.forEach(dept => {
            const option = `<option value="${dept.departmentId}">${dept.name}</option>`;
            if (doctorDeptSelect) doctorDeptSelect.innerHTML += option;
            if (staffDeptSelect) staffDeptSelect.innerHTML += option;
        });
    } catch (error) {
        console.error('Error loading departments:', error);
    }
}