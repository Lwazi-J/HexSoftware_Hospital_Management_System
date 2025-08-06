// Search Functions
async function searchPatients() {
    if (!checkAuthentication()) return;
    
    const name = document.getElementById('patientSearchName').value;
    const bloodType = document.getElementById('patientSearchBloodType').value;
    const phone = document.getElementById('patientSearchPhone').value;
    
    if (!name && !bloodType && !phone) {
        loadPatients();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (name) params.append('name', name);
        if (bloodType) params.append('bloodType', bloodType);
        if (phone) params.append('phone', phone);
        
        const response = await fetch(`${API_BASE}/patients/search?${params}`);
        const patients = await response.json();
        displayPatients(patients);
    } catch (error) {
        console.error('Error searching patients:', error);
    }
}

async function searchDoctors() {
    if (!checkAuthentication()) return;
    
    const name = document.getElementById('doctorSearchName').value;
    const specialization = document.getElementById('doctorSearchSpecialization').value;
    const departmentId = document.getElementById('doctorSearchDepartment').value;
    const minExperience = document.getElementById('doctorSearchExperience').value;
    
    if (!name && !specialization && !departmentId && !minExperience) {
        loadDoctors();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (name) params.append('name', name);
        if (specialization) params.append('specialization', specialization);
        if (departmentId) params.append('departmentId', departmentId);
        if (minExperience) params.append('minExperience', minExperience);
        
        const response = await fetch(`${API_BASE}/doctors/search?${params}`);
        const doctors = await response.json();
        displayDoctors(doctors);
    } catch (error) {
        console.error('Error searching doctors:', error);
    }
}

async function searchAppointments() {
    if (!checkAuthentication()) return;
    
    const date = document.getElementById('appointmentSearchDate').value;
    const status = document.getElementById('appointmentSearchStatus').value;
    const type = document.getElementById('appointmentSearchType').value;
    
    if (!date && !status && !type) {
        loadAppointments();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (date) params.append('date', date);
        if (status) params.append('status', status);
        if (type) params.append('type', type);
        
        const response = await fetch(`${API_BASE}/appointments/search?${params}`);
        const appointments = await response.json();
        displayAppointments(appointments);
    } catch (error) {
        console.error('Error searching appointments:', error);
    }
}

async function searchDepartments() {
    if (!checkAuthentication()) return;
    
    const name = document.getElementById('departmentSearchName').value;
    const location = document.getElementById('departmentSearchLocation').value;
    const minStaffCount = document.getElementById('departmentSearchStaffCount').value;
    
    if (!name && !location && !minStaffCount) {
        loadDepartments();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (name) params.append('name', name);
        if (location) params.append('location', location);
        if (minStaffCount) params.append('minStaffCount', minStaffCount);
        
        const response = await fetch(`${API_BASE}/departments/search?${params}`);
        const departments = await response.json();
        displayDepartments(departments);
    } catch (error) {
        console.error('Error searching departments:', error);
    }
}

async function searchStaff() {
    if (!checkAuthentication()) return;
    
    const name = document.getElementById('staffSearchName').value;
    const role = document.getElementById('staffSearchRole').value;
    const departmentId = document.getElementById('staffSearchDepartment').value;
    
    if (!name && !role && !departmentId) {
        loadStaff();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (name) params.append('name', name);
        if (role) params.append('role', role);
        if (departmentId) params.append('departmentId', departmentId);
        
        const response = await fetch(`${API_BASE}/staff/search?${params}`);
        const staff = await response.json();
        displayStaff(staff);
    } catch (error) {
        console.error('Error searching staff:', error);
    }
}

async function searchPrescriptions() {
    if (!checkAuthentication()) return;
    
    const medication = document.getElementById('prescriptionSearchMedication').value;
    const status = document.getElementById('prescriptionSearchStatus').value;
    const date = document.getElementById('prescriptionSearchDate').value;
    
    if (!medication && !status && !date) {
        loadPrescriptions();
        return;
    }
    
    try {
        const params = new URLSearchParams();
        if (medication) params.append('medication', medication);
        if (status) params.append('status', status);
        if (date) params.append('date', date);
        
        const response = await fetch(`${API_BASE}/prescriptions/search?${params}`);
        const prescriptions = await response.json();
        displayPrescriptions(prescriptions);
    } catch (error) {
        console.error('Error searching prescriptions:', error);
    }
}

// Clear search functions
function clearPatientSearch() {
    document.getElementById('patientSearchName').value = '';
    document.getElementById('patientSearchBloodType').value = '';
    document.getElementById('patientSearchPhone').value = '';
    loadPatients();
}

function clearDoctorSearch() {
    document.getElementById('doctorSearchName').value = '';
    document.getElementById('doctorSearchSpecialization').value = '';
    document.getElementById('doctorSearchDepartment').value = '';
    document.getElementById('doctorSearchExperience').value = '';
    loadDoctors();
}

function clearAppointmentSearch() {
    document.getElementById('appointmentSearchDate').value = '';
    document.getElementById('appointmentSearchStatus').value = '';
    document.getElementById('appointmentSearchType').value = '';
    loadAppointments();
}

function clearDepartmentSearch() {
    document.getElementById('departmentSearchName').value = '';
    document.getElementById('departmentSearchLocation').value = '';
    document.getElementById('departmentSearchStaffCount').value = '';
    loadDepartments();
}

function clearStaffSearch() {
    document.getElementById('staffSearchName').value = '';
    document.getElementById('staffSearchRole').value = '';
    document.getElementById('staffSearchDepartment').value = '';
    loadStaff();
}

function clearPrescriptionSearch() {
    document.getElementById('prescriptionSearchMedication').value = '';
    document.getElementById('prescriptionSearchStatus').value = '';
    document.getElementById('prescriptionSearchDate').value = '';
    loadPrescriptions();
}

// Load departments for search dropdowns
async function loadDepartmentsForSearch() {
    try {
        const response = await fetch(`${API_BASE}/departments`);
        const departments = await response.json();
        
        const doctorSearchDept = document.getElementById('doctorSearchDepartment');
        const staffSearchDept = document.getElementById('staffSearchDepartment');
        
        if (doctorSearchDept) {
            doctorSearchDept.innerHTML = '<option value="">All Departments</option>';
            departments.forEach(dept => {
                doctorSearchDept.innerHTML += `<option value="${dept.departmentId}">${dept.name}</option>`;
            });
        }
        
        if (staffSearchDept) {
            staffSearchDept.innerHTML = '<option value="">All Departments</option>';
            departments.forEach(dept => {
                staffSearchDept.innerHTML += `<option value="${dept.departmentId}">${dept.name}</option>`;
            });
        }
    } catch (error) {
        console.error('Error loading departments for search:', error);
    }
}