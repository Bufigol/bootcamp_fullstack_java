// perfil.js
document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const profileForm = document.getElementById('profileForm');
    const saveButton = document.getElementById('saveProfile');
    const spinner = saveButton.querySelector('.spinner-border');
    const buttonText = saveButton.querySelector('.button-text');

    // Función para toggle del estado del botón
    function toggleButtonLoading(loading) {
        saveButton.disabled = loading;
        spinner.classList.toggle('d-none', !loading);
        buttonText.textContent = loading ? 'Guardando...' : 'Guardar Cambios';
    }

    // Función para validar el email
    function isValidEmail(email) {
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return re.test(email);
    }

    // Función para validar el formulario
    function validateForm() {
        const name = document.getElementById('editName').value.trim();
        const email = document.getElementById('editEmail').value.trim();

        if (name.length < 3 || name.length > 100) {
            AlertManager.showAlert('El nombre debe tener entre 3 y 100 caracteres', 'danger');
            return false;
        }

        if (!isValidEmail(email)) {
            AlertManager.showAlert('Por favor ingrese un email válido', 'danger');
            return false;
        }

        return true;
    }

    // Manejador del envío del formulario
    async function handleSubmit() {
        if (!validateForm()) {
            return;
        }

        toggleButtonLoading(true);

        const formData = {
            name: document.getElementById('editName').value.trim(),
            email: document.getElementById('editEmail').value.trim()
        };

        // Agregar dirección si existe el campo
        const direccionInput = document.getElementById('editDireccion');
        if (direccionInput) {
            formData.direccion = direccionInput.value.trim();
        }

        try {
            const response = await fetch('/api/users/profile', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('jwt_token')}`
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Error al actualizar el perfil');
            }

            AlertManager.showAlert('Perfil actualizado exitosamente', 'success');
            setTimeout(() => window.location.reload(), 1500);

        } catch (error) {
            AlertManager.showAlert('Error: ' + error.message, 'danger');
            toggleButtonLoading(false);
        }
    }

    // Event Listeners
    saveButton.addEventListener('click', handleSubmit);

    // Validación en tiempo real
    document.getElementById('editEmail').addEventListener('input', function(e) {
        if (!isValidEmail(e.target.value)) {
            e.setCustomValidity('Email inválido');
        } else {
            e.setCustomValidity('');
        }
    });

    document.getElementById('editName').addEventListener('input', function(e) {
        if (e.target.value.length < 3 || e.target.value.length > 100) {
            e.setCustomValidity('El nombre debe tener entre 3 y 100 caracteres');
        } else {
            e.setCustomValidity('');
        }
    });
});