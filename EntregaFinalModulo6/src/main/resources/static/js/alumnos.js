// Variables globales
let currentAlumnoId = null;
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
const loadingSpinner = document.getElementById('loadingSpinner');

document.addEventListener('DOMContentLoaded', function() {
    // Botón nuevo alumno
    const btnNuevoAlumno = document.getElementById('btnNuevoAlumno');
    if (btnNuevoAlumno) {
        btnNuevoAlumno.addEventListener('click', () => {
            mostrarFormularioNuevo();
        });
    }

    // Inicializar tooltips de Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
});

// Funciones de gestión de alumnos
async function mostrarFormularioNuevo() {
    showSpinner();
    try {
        const response = await fetch('/admin/alumnos/nuevo');
        const html = await response.text();
        document.getElementById('modalContainer').innerHTML = html;
        const modal = new bootstrap.Modal(document.getElementById('alumnoModal'));
        modal.show();
        initRutValidation();
    } catch (error) {
        showError('Error al cargar el formulario');
    } finally {
        hideSpinner();
    }
}

async function editarAlumno(id) {
    showSpinner();
    try {
        const response = await fetch(`/admin/alumnos/${id}/edit`);
        const html = await response.text();
        document.getElementById('modalContainer').innerHTML = html;
        const modal = new bootstrap.Modal(document.getElementById('alumnoModal'));
        modal.show();
        initRutValidation();
    } catch (error) {
        showError('Error al cargar el formulario de edición');
    } finally {
        hideSpinner();
    }
}

async function eliminarAlumno(id) {
    currentAlumnoId = id;
    const confirmDeleteButton = document.getElementById('confirmDelete');

    confirmDeleteButton.onclick = async () => {
        toggleButtonLoading(confirmDeleteButton, true);
        try {
            const response = await fetch(`/admin/alumnos/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                deleteModal.hide();
                window.location.reload();
            } else {
                throw new Error('Error al eliminar el alumno');
            }
        } catch (error) {
            showError('Error al eliminar el alumno');
        } finally {
            toggleButtonLoading(confirmDeleteButton, false);
        }
    };

    deleteModal.show();
}

async function verMaterias(id) {
    showSpinner();
    try {
        const response = await fetch(`/admin/alumnos/${id}/materias`);
        const html = await response.text();
        document.getElementById('modalContainer').innerHTML = html;
        const modal = new bootstrap.Modal(document.getElementById('materiasModal'));
        modal.show();
    } catch (error) {
        showError('Error al cargar las materias');
    } finally {
        hideSpinner();
    }
}

// Funciones de utilidad
function showSpinner() {
    document.getElementById('loadingSpinner').classList.remove('d-none');
}

function hideSpinner() {
    document.getElementById('loadingSpinner').classList.add('d-none');
}

function toggleButtonLoading(button, loading) {
    const spinner = button.querySelector('.spinner-border');
    const text = button.querySelector('.button-text');
    button.disabled = loading;
    spinner.classList.toggle('d-none', !loading);
    text.textContent = loading ? 'Procesando...' : text.getAttribute('data-original-text');
}

function showError(message) {
    AlertManager.showAlert(message, 'danger');
}

function showSuccess(message) {
    AlertManager.showAlert(message, 'success');
}

// Validación de RUT
function initRutValidation() {
    const rutInput = document.getElementById('rut');
    if (rutInput) {
        rutInput.addEventListener('input', formatRut);
        rutInput.addEventListener('blur', validateRut);
    }
}

function formatRut(e) {
    let value = e.target.value.replace(/\./g, '').replace(/-/g, '');
    value = value.replace(/[^0-9kK]/g, '');

    if (value.length > 1) {
        value = value.slice(0, -1) + '-' + value.slice(-1);
    }
    if (value.length > 4) {
        value = value.slice(0, -2).replace(/\B(?=(\d{3})+(?!\d))/g, ".") + value.slice(-2);
    }
    e.target.value = value;
}

function validateRut(e) {
    const rut = e.target.value.replace(/\./g, '').replace(/-/g, '');
    if (!validarRutChileno(rut)) {
        e.target.setCustomValidity('RUT inválido');
    } else {
        e.target.setCustomValidity('');
    }
}

function validarRutChileno(rut) {
    if (!/^[0-9]{7,8}[0-9Kk]$/.test(rut)) return false;

    const dv = rut.slice(-1).toUpperCase();
    let numero = parseInt(rut.slice(0, -1), 10);
    let suma = 0;
    let multiplicador = 2;

    while (numero > 0) {
        suma += (numero % 10) * multiplicador;
        numero = Math.floor(numero / 10);
        multiplicador = multiplicador === 7 ? 2 : multiplicador + 1;
    }

    const dvEsperado = 11 - (suma % 11);
    const dvCalculado = dvEsperado === 11 ? '0' : dvEsperado === 10 ? 'K' : dvEsperado.toString();

    return dv === dvCalculado;
}

// Exponer funciones necesarias globalmente
window.editarAlumno = editarAlumno;
window.eliminarAlumno = eliminarAlumno;
window.verMaterias = verMaterias;