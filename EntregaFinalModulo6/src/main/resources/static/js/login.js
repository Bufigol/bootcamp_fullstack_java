// login.js
document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const submitButton = document.getElementById('submitButton');
    const spinner = submitButton.querySelector('.spinner-border');
    const buttonText = submitButton.querySelector('.button-text');

    form.addEventListener('submit', function() {
        submitButton.disabled = true;
        spinner.classList.remove('d-none');
        buttonText.textContent = 'Procesando...';
    });

    // Agregar evento para reset del formulario
    form.addEventListener('reset', function() {
        submitButton.disabled = false;
        spinner.classList.add('d-none');
        buttonText.textContent = 'Iniciar Sesión';
    });

    // Manejar errores de autenticación
    if (document.querySelector('.alert-danger')) {
        submitButton.disabled = false;
        spinner.classList.add('d-none');
        buttonText.textContent = 'Iniciar Sesión';
    }
});