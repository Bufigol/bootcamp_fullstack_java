document.querySelector('form').addEventListener('submit', function(e) {
    const password = document.getElementById('password');
    const confirmarPassword = document.getElementById('confirmarPassword');

    if (password.value !== confirmarPassword.value) {
        e.preventDefault();
        alert('Las contraseñas no coinciden');
    }
});