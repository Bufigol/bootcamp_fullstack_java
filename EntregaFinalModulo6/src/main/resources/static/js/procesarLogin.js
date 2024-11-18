document.addEventListener('DOMContentLoaded', function() {
    // Referencias a elementos del DOM
    const loginForm = document.getElementById('loginForm');
    const submitButton = document.getElementById('submitButton');
    const spinner = document.getElementById('loginSpinner');
    const buttonText = submitButton.querySelector('.button-text');
    const alertContainer = document.getElementById('alertContainer');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    // Verificar si ya hay una sesión activa
    const token = localStorage.getItem('jwt_token');
    if (token) {
        window.location.href = '/home';
        return;
    }

    // Función para mostrar alertas
    function showAlert(message, type = 'danger') {
        alertContainer.innerHTML = ''; // Limpiar alertas previas

        const alert = document.createElement('div');
        alert.className = `alert alert-${type} alert-dismissible fade show`;
        alert.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;

        alertContainer.appendChild(alert);

        // Auto-cerrar la alerta después de 5 segundos
        setTimeout(() => {
            alert.classList.remove('show');
            setTimeout(() => alert.remove(), 150);
        }, 5000);
    }

    // Función para manejar el estado del botón
    function toggleSubmitButton(loading) {
        submitButton.disabled = loading;
        spinner.classList.toggle('d-none', !loading);
        buttonText.textContent = loading ? 'Iniciando sesión...' : 'Iniciar Sesión';
    }

    // Función para validar el formulario
    function validateForm(username, password) {
        if (!username || username.trim().length < 3) {
            showAlert('El nombre de usuario debe tener al menos 3 caracteres');
            return false;
        }
        if (!password || password.trim().length < 6) {
            showAlert('La contraseña debe tener al menos 6 caracteres');
            return false;
        }
        return true;
    }

    // Función para realizar el inicio de sesión
    async function handleLogin(username, password) {
        try {
            const response = await fetch('/api/auth/signin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Error en el inicio de sesión');
            }

            const data = await response.json();

            if (data.token) {
                // Almacenar el token JWT
                localStorage.setItem('jwt_token', data.token);

                // Almacenar información adicional del usuario si está disponible
                if (data.username) {
                    localStorage.setItem('username', data.username);
                }

                // Almacenar roles como string JSON
                if (data.roles && Array.isArray(data.roles)) {
                    localStorage.setItem('user_roles', JSON.stringify(data.roles));
                }

                return data;
            } else {
                throw new Error('No se recibió un token válido');
            }
        } catch (error) {
            console.error('Error en la autenticación:', error);
            throw error;
        }
    }


    // Manejar el envío del formulario
    loginForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();

        if (!validateForm(username, password)) {
            return;
        }

        toggleSubmitButton(true);

        try {
            const data = await handleLogin(username, password);
            showAlert('Inicio de sesión exitoso, redirigiendo...', 'success');

            // Redirigir después de un breve delay
            setTimeout(() => {
                window.location.href = '/home';
            }, 1000);
        } catch (error) {
            showAlert(error.message || 'Error al intentar iniciar sesión', 'danger');
        } finally {
            toggleSubmitButton(false);
        }
    });

    // Limpiar alertas al escribir en los inputs
    [usernameInput, passwordInput].forEach(input => {
        input.addEventListener('input', () => {
            const alerts = alertContainer.querySelectorAll('.alert');
            alerts.forEach(alert => alert.remove());
        });
    });

    // Prevenir múltiples envíos del formulario
    loginForm.addEventListener('submit', function(e) {
        if (submitButton.disabled) {
            e.preventDefault();
            return false;
        }
    });

    // Manejar la tecla Enter
    document.addEventListener('keypress', function(e) {
        if (e.key === 'Enter' && !submitButton.disabled) {
            const activeElement = document.activeElement;
            if (activeElement.tagName === 'INPUT') {
                e.preventDefault();
                submitButton.click();
            }
        }
    });

    // Evitar el envío del formulario si ya está en proceso
    let isSubmitting = false;
    loginForm.addEventListener('submit', function(e) {
        if (isSubmitting) {
            e.preventDefault();
            return;
        }
        isSubmitting = true;
        setTimeout(() => {
            isSubmitting = false;
        }, 2000);
    });
});