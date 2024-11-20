document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const submitButton = document.getElementById('submitButton');
    const spinner = document.getElementById('loginSpinner');
    const buttonText = submitButton.querySelector('.button-text');

    loginForm.addEventListener('submit', function(e) {
        // Deshabilitar el botón y mostrar spinner
        submitButton.disabled = true;
        spinner.classList.remove('d-none');
        buttonText.textContent = 'Procesando...';
    });
});