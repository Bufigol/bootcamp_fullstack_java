// Constantes
const API_URL = '/api/alumnos';
const API_MATERIAS_URL = '/api/materias';
const TOKEN_KEY = 'jwt_token';

// Variables globales
let currentAlumnoId = null;
const alumnoModal = new bootstrap.Modal(document.getElementById('alumnoModal'));
const materiasModal = new bootstrap.Modal(document.getElementById('materiasModal'));
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

function formatRut(rut) {
    // Limpiar el RUT de puntos y guión
    let valor = rut.replace(/\./g, '').replace(/-/g, '');

    // Validar largo mínimo
    if (valor.length < 2) return valor;

    // Obtener dígito verificador
    const dv = valor.slice(-1);
    let rutNumero = valor.slice(0, -1);

    // Formatear número
    rutNumero = rutNumero.toString().split('').reverse().join('');
    let rutFormateado = '';
    for (let i = 0; i < rutNumero.length; i++) {
        rutFormateado += rutNumero.charAt(i);
        if ((i + 1) % 3 === 0 && i < rutNumero.length - 1) {
            rutFormateado += '.';
        }
    }
    rutFormateado = rutFormateado.split('').reverse().join('');

    return `${rutFormateado}-${dv}`;
}

async function handleResponse(response) {
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Error en la operación');
    }
    return response.json();
}

// Funciones CRUD
async function createAlumno(alumnoData) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: getAuthHeader(),
            body: JSON.stringify(alumnoData)
        });
        await handleResponse(response);
        showSuccess('Alumno creado exitosamente');
        alumnoModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function updateAlumno(alumnoData) {
    try {
        const response = await fetch(`${API_URL}/${alumnoData.id}`, {
            method: 'PUT',
            headers: getAuthHeader(),
            body: JSON.stringify(alumnoData)
        });
        await handleResponse(response);
        showSuccess('Alumno actualizado exitosamente');
        alumnoModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

async function deleteAlumno(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: getAuthHeader()
        });
        if (response.ok) {
            showSuccess('Alumno eliminado exitosamente');
            reloadTable();
        } else {
            throw new Error('Error al eliminar el alumno');
        }
    } catch (error) {
        showError(error.message);
    }
}

async function getAlumnoById(id) {
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

// Funciones para manejo de materias
async function loadMaterias(alumnoId) {
    try {
        const response = await fetch(`${API_MATERIAS_URL}/alumno/${alumnoId}`, {
            headers: getAuthHeader()
        });
        return await handleResponse(response);
    } catch (error) {
        showError(error.message);
        return [];
    }
}

async function updateMaterias(alumnoId, materias) {
    try {
        const response = await fetch(`${API_URL}/${alumnoId}/materias`, {
            method: 'PUT',
            headers: getAuthHeader(),
            body: JSON.stringify(materias)
        });
        await handleResponse(response);
        showSuccess('Materias actualizadas exitosamente');
        materiasModal.hide();
        reloadTable();
    } catch (error) {
        showError(error.message);
    }
}

// Funciones de UI
function resetForm() {
    const form = document.getElementById('alumnoForm');
    form.reset();
    form.classList.remove('was-validated');
    currentAlumnoId = null;
    document.getElementById('alumnoModalLabel').textContent = 'Nuevo Alumno';
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
async function handleSaveAlumno(event) {
    event.preventDefault();
    const form = document.getElementById('alumnoForm');

    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }

    const saveButton = document.getElementById('saveAlumno');
    toggleButtonLoading(saveButton, true);

    const alumnoData = {
        rut: document.getElementById('rut').value,
        nombre: document.getElementById('nombre').value,
        direccion: document.getElementById('direccion').value
    };

    try {
        if (currentAlumnoId) {
            alumnoData.id = currentAlumnoId;
            await updateAlumno(alumnoData);
        } else {
            await createAlumno(alumnoData);
        }
    } finally {
        toggleButtonLoading(saveButton, false);
    }
}

async function handleEditAlumno(id) {
    showSpinner();
    try {
        const alumno = await getAlumnoById(id);
        if (alumno) {
            currentAlumnoId = id;
            document.getElementById('alumnoModalLabel').textContent = 'Editar Alumno';
            document.getElementById('rut').value = alumno.rut;
            document.getElementById('nombre').value = alumno.nombre;
            document.getElementById('direccion').value = alumno.direccion || '';
            alumnoModal.show();
        }
    } finally {
        hideSpinner();
    }
}

async function handleDeleteAlumno(id) {
    const confirmDeleteButton = document.getElementById('confirmDelete');

    confirmDeleteButton.onclick = async () => {
        toggleButtonLoading(confirmDeleteButton, true);
        try {
            await deleteAlumno(id);
            deleteModal.hide();
        } finally {
            toggleButtonLoading(confirmDeleteButton, false);
        }
    };

    deleteModal.show();
}

async function handleAsignarMaterias(id) {
    showSpinner();
    try {
        const materias = await loadMaterias(id);
        const materiasListElement = document.getElementById('materiasList');
        materiasListElement.innerHTML = '';

        const checkboxes = materias.map(materia => `
            <div class="form-check">
                <input class="form-check-input" type="checkbox" 
                       value="${materia.id}" id="materia${materia.id}"
                       ${materia.asignada ? 'checked' : ''}>
                <label class="form-check-label" for="materia${materia.id}">
                    ${materia.nombre}
                </label>
            </div>
        `).join('');

        materiasListElement.innerHTML = checkboxes;
        currentAlumnoId = id;
        materiasModal.show();
    } finally {
        hideSpinner();
    }
}

async function handleSearch(event) {
    event.preventDefault();
    const searchParams = new URLSearchParams();

    const rut = document.getElementById('searchRut').value;
    const nombre = document.getElementById('searchNombre').value;

    if (rut) searchParams.set('rut', rut);
    if (nombre) searchParams.set('nombre', nombre);

    const currentUrl = new URL(window.location.href);
    currentUrl.search = searchParams.toString();
    window.location.href = currentUrl;
}

// Input Handlers
function handleRutInput(event) {
    let rut = event.target.value;
    // Remover caracteres no permitidos
    rut = rut.replace(/[^0-9kK-]/g, '');
    // Formatear RUT
    event.target.value = formatRut(rut);
}

// Event Listeners
document.addEventListener('DOMContentLoaded', function() {
    // Save original button texts
    document.querySelectorAll('.button-text').forEach(text => {
        text.setAttribute('data-original-text', text.textContent);
    });

    // Form submissions
    document.getElementById('alumnoForm').addEventListener('submit', handleSaveAlumno);
    document.getElementById('searchForm').addEventListener('submit', handleSearch);

    // Modal events
    document.getElementById('alumnoModal').addEventListener('hidden.bs.modal', resetForm);

    // RUT input formatting
    document.getElementById('rut').addEventListener('input', handleRutInput);

    // Save button click
    document.getElementById('saveAlumno').addEventListener('click', handleSaveAlumno);

    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Expose functions to window for onclick handlers
window.editarAlumno = handleEditAlumno;
window.eliminarAlumno = handleDeleteAlumno;
window.asignarMaterias = handleAsignarMaterias;