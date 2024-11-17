// Constantes
const ROLES = {
    DEFAULT: ['ROLE_CLIENT']  // Cambiado de ROLE_USER a ROLE_CLIENT
};

const PASSWORD_PATTERNS = {
    strong: new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})'),
    medium: new RegExp('^(?=.*[a-zA-Z])(?=.*[0-9])(?=.{6,})')
};

document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const form = document.getElementById('registroForm');
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');
    const submitButton = document.getElementById('submitButton');
    const spinner = submitButton.querySelector('.spinner-border');
    const buttonText = submitButton.querySelector('.button-text');

    // Función principal de envío del formulario
    async function handleSubmit(event) {
        event.preventDefault();

        if (!validateForm()) return;

        // Preparar datos del formulario
        const formData = {
            nombre: document.getElementById('nombre').value,
            username: document.getElementById('username').value,
            email: document.getElementById('email').value,
            password: password.value,
            confirmPassword: confirmPassword.value,
            roles: ROLES.DEFAULT
        };
        // Iniciar proceso de envío
        toggleSubmitButton(true);

        try {
            const response = await sendRegistrationData(formData);
            handleRegistrationResponse(response);
        } catch (error) {
            handleRegistrationError(error);
        } finally {
            toggleSubmitButton(false);
        }
    }

    // Validación del formulario
    function validateForm() {
        if (!form.checkValidity()) {
            form.classList.add('was-validated');
            return false;
        }

        if (password.value !== confirmPassword.value) {
            confirmPassword.setCustomValidity('Las contraseñas no coinciden');
            form.classList.add('was-validated');
            return false;
        }

        return true;
    }

    // Envío de datos al servidor
    async function sendRegistrationData(formData) {
        const response = await fetch('/api/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || 'Error en el registro');
        }

        return data;
    }

    // Manejo de respuesta exitosa
    function handleRegistrationResponse(data) {
        // Guardar el token si es necesario
        if (data.token) {
            localStorage.setItem('jwt_token', data.token);
        }

        // Redirigir al login con mensaje de éxito
        window.location.href = '/login?registered=true';
    }

    // Manejo de errores
    function handleRegistrationError(error) {
        console.error('Error:', error);
        let errorMessage = 'Error en el registro. Por favor, intente nuevamente.';

        // Mensajes de error específicos
        if (error.message.includes('ya existe con username')) {
            errorMessage = 'El nombre de usuario ya está en uso.';
        } else if (error.message.includes('ya existe con email')) {
            errorMessage = 'El correo electrónico ya está registrado.';
        } else if (error.message.includes('Las contraseñas no coinciden')) {
            errorMessage = 'Las contraseñas ingresadas no coinciden.';
        }

        AlertManager.showAlert(errorMessage, 'danger');
    }

    // Toggle del botón de submit
    function toggleSubmitButton(isLoading) {
        submitButton.disabled = isLoading;
        spinner.classList.toggle('d-none', !isLoading);
        buttonText.textContent = isLoading ? 'Procesando...' : 'Registrarse';
    }

    // Validación de fortaleza de contraseña
    function updatePasswordStrength(value) {
        let strength = '';

        if (PASSWORD_PATTERNS.strong.test(value)) {
            strength = 'strong';
        } else if (PASSWORD_PATTERNS.medium.test(value)) {
            strength = 'medium';
        } else if (value.length > 0) {
            strength = 'weak';
        }

        const strengthIndicator = document.querySelector('.password-strength');
        if (strengthIndicator) {
            strengthIndicator.className = `password-strength strength-${strength}`;
        }
    }

    // Event Listeners
    form.addEventListener('submit', handleSubmit);

    confirmPassword.addEventListener('input', function() {
        if (password.value === confirmPassword.value) {
            confirmPassword.setCustomValidity('');
        } else {
            confirmPassword.setCustomValidity('Las contraseñas no coinciden');
        }
    });

    password.addEventListener('input', function() {
        updatePasswordStrength(this.value);
    });
});