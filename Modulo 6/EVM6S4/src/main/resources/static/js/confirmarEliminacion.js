function confirmarEliminacion(username) {
    const modal = new bootstrap.Modal(document.getElementById('eliminarModal'));
    const btnConfirmar = document.getElementById('btnConfirmarEliminacion');
    btnConfirmar.href = `/eliminar/${username}`;
    modal.show();
}

// Auto-cerrar alertas después de 5 segundos
setTimeout(function() {
    $('.alert').alert('close');
}, 5000);