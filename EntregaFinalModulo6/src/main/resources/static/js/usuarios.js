// Constantes
const API_URL = '/api/admin/users';
const TOKEN_KEY = 'jwt_token';
const ROLES = {
    ADMIN: 'ROLE_ADMIN',
    CLIENT: 'ROLE_CLIENT'
};

// Variables globales
let currentUserId = null;
const usuarioModal = new bootstrap.Modal(document.getElementById('usuarioModal'));
const rolesModal = new bootstrap.Modal(document.getElementById('rolesModal'));
const loadingSpinner = document.getElementById('loadingSpinner');

// Funciones de utilidad
function getAuthHeader() {
    const token = localStorage.getItem(TOKEN_KEY);
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

function showSpinner() {
    loadingSpinner.classList.remove('d-none');
}

function hideSpinner() {
    loadingSpinner.classList.add('d-none');
}

function toggleButtonLoading(button, loading) {
    const spinner = button.querySelector('.spinner-border');
    const text = button.querySelector('.button-text');
    button.disabled = loading;
    spinner.classList.toggle('d-none', !loading);
    text.textContent = loading ? 'Procesando...' : text.getAttribute('data-original-text');
}

async function handleResponse(response) {
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Error en la operación');
    }
    return response.json();
}

function validateEmail(email) {
    const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return re.test(email);
}

function validateUsername(username) {
    const re = /^[a-zA-Z0-9._-]{3,}$/;
    return re.test(username);
}

// Funciones CRUD
async function createUsuario(usuarioData) {
    try {
        // Aseguramos que tenga al menos ROLE_CLIENT por defecto
        if (!usuarioData.roles || usuarioData.roles.length === 0) {
            usuarioData.roles = [ROLES.CLIENT];
        }

        const response = await fetch(`${API_URL}/signup`, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(usuarioData)
        });
        await handleResponse(response);
        showSuccess('Usuario creado exitosamente');
        usuarioModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function updateUsuario(usuarioData) {
    try {
        const response = await fetch(`${API_URL}/${usuarioData.id}`, {
            method: 'PUT',
            headers: getAuthHeader(),
            body: JSON.stringify(usuarioData)
        });
        await handleResponse(response);
        showSuccess('Usuario actualizado exitosamente');
        usuarioModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function getUsuarioById(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            headers: getAuthHeader()
        });
        return await handleResponse(response);
    } catch (error) {
        showError(error.message);
        return null;
    }
}

async function updateRoles(userId, roles) {
    try {
        // Validar que al menos tenga un rol
        if (!roles || roles.length === 0) {
            roles = [ROLES.CLIENT]; // Rol por defecto
        }

        const response = await fetch(`${API_URL}/${userId}/roles`, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(roles)
        });
        await handleResponse(response);
        showSuccess('Roles actualizados exitosamente');
        rolesModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function toggleEstado(userId) {
    try {
        const response = await fetch(`${API_URL}/${userId}/toggle-status`, {
            method: 'POST',
            headers: getAuthHeader()
        });
        await handleResponse(response);
        showSuccess('Estado del usuario actualizado exitosamente');
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

// Funciones de UI
function resetForm() {
    const form = document.getElementById('usuarioForm');
    form.reset();
    form.classList.remove('was-validated');
    currentUserId = null;
    document.getElementById('usuarioModalLabel').textContent = 'Nuevo Usuario';
    document.getElementById('passwordGroup').style.display = 'block';
}

function showError(message) {
    AlertManager.showAlert(message, 'danger');
}

function showSuccess(message) {
    AlertManager.showAlert(message, 'success');
}

async function reloadTable() {
    const currentUrl = new URL(window.location.href);
    window.location.href = currentUrl;
}

// Handlers
async function handleSaveUsuario(event) {
    event.preventDefault();
    const form = document.getElementById('usuarioForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    const saveButton = document.getElementById('saveUsuario');
    toggleButtonLoading(saveButton, true);

    const usuarioData = {
        name: document.getElementById('name').value,
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        roles: [ROLES.CLIENT] // Role por defecto
    };

    if (!currentUserId) {
        usuarioData.password = document.getElementById('password').value;
    }

    try {
        if (currentUserId) {
            usuarioData.id = currentUserId;
            await updateUsuario(usuarioData);
        } else {
            await createUsuario(usuarioData);
        }
    } finally {
        toggleButtonLoading(saveButton, false);
    }
}

async function handleGestionarRoles(id) {
    showSpinner();
    try {
        const usuario = await getUsuarioById(id);
        if (usuario) {
            currentUserId = id;
            document.getElementById('roleAdmin').checked = usuario.roles.includes(ROLES.ADMIN);
            document.getElementById('roleClient').checked = usuario.roles.includes(ROLES.CLIENT);
            rolesModal.show();
        }
    } finally {
        hideSpinner();
    }
}

async function handleSaveRoles() {
    const roles = [];
    if (document.getElementById('roleAdmin').checked) roles.push(ROLES.ADMIN);
    if (document.getElementById('roleClient').checked) roles.push(ROLES.CLIENT);

    if (roles.length === 0) {
        showError('Debe seleccionar al menos un rol');
        return;
    }

    const saveButton = document.getElementById('saveRoles');
    toggleButtonLoading(saveButton, true);

    try {
        await updateRoles(currentUserId, roles);
    } finally {
        toggleButtonLoading(saveButton, false);
    }
}

async function handleToggleEstado(id) {
    if (confirm('¿Está seguro de cambiar el estado de este usuario?')) {
        showSpinner();
        try {
            await toggleEstado(id);
        } finally {
            hideSpinner();
        }
    }
}

async function handleSearch(event) {
    event.preventDefault();
    const searchParams = new URLSearchParams();

    const username = document.getElementById('searchUsername').value;
    const email = document.getElementById('searchEmail').value;
    const role = document.getElementById('searchRole').value;

    if (username) searchParams.set('username', username);
    if (email) searchParams.set('email', email);
    if (role) searchParams.set('role', role);

    const currentUrl = new URL(window.location.href);
    currentUrl.search = searchParams.toString();
    window.location.href = currentUrl;
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Save original button texts
    document.querySelectorAll('.button-text').forEach(text => {
        text.setAttribute('data-original-text', text.textContent);
    });

    // Form submissions
    document.getElementById('usuarioForm').addEventListener('submit', handleSaveUsuario);
    document.getElementById('searchForm').addEventListener('submit', handleSearch);

    // Modal events
    document.getElementById('usuarioModal').addEventListener('hidden.bs.modal', resetForm);

    // Save buttons
    document.getElementById('saveUsuario').addEventListener('click', handleSaveUsuario);
    document.getElementById('saveRoles').addEventListener('click', handleSaveRoles);

    // Input validations
    document.getElementById('email').addEventListener('input', function(e) {
        if (!validateEmail(e.target.value)) {
            e.target.setCustomValidity('Email inválido');
        } else {
            e.target.setCustomValidity('');
        }
    });

    document.getElementById('username').addEventListener('input', function(e) {
        if (!validateUsername(e.target.value)) {
            e.target.setCustomValidity('Username inválido');
        } else {
            e.target.setCustomValidity('');
        }
    });

    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Expose functions to window for onclick handlers
window.editarUsuario = handleEditUsuario;
window.gestionarRoles = handleGestionarRoles;
window.toggleUsuarioEstado = handleToggleEstado;