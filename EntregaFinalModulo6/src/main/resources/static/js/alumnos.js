document.addEventListener('DOMContentLoaded', function() {
    // Botón nuevo alumno
    const btnNuevoAlumno = document.getElementById('btnNuevoAlumno');
    if (btnNuevoAlumno) {
        btnNuevoAlumno.addEventListener('click', () => {
            fetch('/admin/alumnos/nuevo')
                .then(response => response.text())
                .then(html => {
                    document.getElementById('modalContainer').innerHTML = html;
                    const modal = new bootstrap.Modal(document.getElementById('alumnoModal'));
                    modal.show();
                    initRutValidation();
                })
                .catch(error => showError('Error al cargar el formulario'));
        });
    }

    // Validación de RUT
    function initRutValidation() {
        const rutInput = document.getElementById('rut');
        if (rutInput) {
            rutInput.addEventListener('input', formatRut);
            rutInput.addEventListener('blur', validateRut);
        }
    }
});

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