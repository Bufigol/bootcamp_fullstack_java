// Constantes
const API_URL = '/api/materias';
const TOKEN_KEY = 'jwt_token';

// Variables globales
let currentMateriaId = null;
const materiaModal = new bootstrap.Modal(document.getElementById('materiaModal'));
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
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

// Funciones CRUD
async function createMateria(materiaData) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(materiaData)
        });
        await handleResponse(response);
        showSuccess('Materia creada exitosamente');
        materiaModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function updateMateria(materiaData) {
    try {
        const response = await fetch(`${API_URL}/${materiaData.id}`, {
            method: 'PUT',
            headers: getAuthHeader(),
            body: JSON.stringify(materiaData)
        });
        await handleResponse(response);
        showSuccess('Materia actualizada exitosamente');
        materiaModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function deleteMateria(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeader()
        });
        if (response.ok) {
            showSuccess('Materia eliminada exitosamente');
            reloadTable();
        } else {
            throw new Error('Error al eliminar la materia');
        }
    } catch (error) {
        showError(error.message);
    }
}

async function getMateriaById(id) {
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

// Funciones de UI
function resetForm() {
    const form = document.getElementById('materiaForm');
    form.reset();
    form.classList.remove('was-validated');
    currentMateriaId = null;
    document.getElementById('materiaModalLabel').textContent = 'Nueva Materia';
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
async function handleSaveMateria(event) {
    event.preventDefault();
    const form = document.getElementById('materiaForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    const saveButton = document.getElementById('saveMateria');
    toggleButtonLoading(saveButton, true);

    const materiaData = {
        nombre: document.getElementById('nombre').value,
        alumnoId: parseInt(document.getElementById('alumnoId').value)
    };

    try {
        if (currentMateriaId) {
            materiaData.id = currentMateriaId;
            await updateMateria(materiaData);
        } else {
            await createMateria(materiaData);
        }
    } finally {
        toggleButtonLoading(saveButton, false);
    }
}

async function handleEditMateria(id) {
    showSpinner();
    try {
        const materia = await getMateriaById(id);
        if (materia) {
            currentMateriaId = id;
            document.getElementById('materiaModalLabel').textContent = 'Editar Materia';
            document.getElementById('nombre').value = materia.nombre;
            document.getElementById('alumnoId').value = materia.alumnoId;
            materiaModal.show();
        }
    } finally {
        hideSpinner();
    }
}

async function handleDeleteMateria(id) {
    const confirmDeleteButton = document.getElementById('confirmDelete');

    confirmDeleteButton.onclick = async () => {
        toggleButtonLoading(confirmDeleteButton, true);
        try {
            await deleteMateria(id);
            deleteModal.hide();
        } finally {
            toggleButtonLoading(confirmDeleteButton, false);
        }
    };

    deleteModal.show();
}

// Search functionality
async function handleSearch(event) {
    event.preventDefault();
    const searchParams = new URLSearchParams();

    const nombre = document.getElementById('searchNombre').value;
    const alumnoId = document.getElementById('searchAlumno').value;

    if (nombre) searchParams.set('nombre', nombre);
    if (alumnoId) searchParams.set('alumnoId', alumnoId);

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
    document.getElementById('materiaForm').addEventListener('submit', handleSaveMateria);
    document.getElementById('searchForm').addEventListener('submit', handleSearch);

    // Modal events
    document.getElementById('materiaModal').addEventListener('hidden.bs.modal', resetForm);

    // Save button click
    document.getElementById('saveMateria').addEventListener('click', handleSaveMateria);

    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Expose functions to window for onclick handlers
window.editarMateria = handleEditMateria;
window.eliminarMateria = handleDeleteMateria;