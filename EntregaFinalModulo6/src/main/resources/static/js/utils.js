// src/main/resources/static/js/utils.js

// Módulo para manejo de alertas
const AlertManager = {
    init: function() {
        this.setupAutoClose();
    },

    setupAutoClose: function() {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            setTimeout(() => {
                const bsAlert = new bootstrap.Alert(alert);
                alert.style.transition = 'opacity 0.5s';
                alert.style.opacity = '0';
                setTimeout(() => bsAlert.close(), 500);
            }, 5000);
        });
    },

    showAlert: function(message, type = 'success') {
        // Método para mostrar alertas dinámicamente
        const alertHtml = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        `;
        const alertContainer = document.querySelector('.alert-container');
        if (alertContainer) {
            alertContainer.insertAdjacentHTML('beforeend', alertHtml);
            this.setupAutoClose();
        }
    }
};

// Módulo para manejo del menú
const NavManager = {
    init: function() {
        this.markActiveLink();
    },

    markActiveLink: function() {
        const currentPath = window.location.pathname;
        document.querySelectorAll('.nav-link').forEach(link => {
            if (link.getAttribute('href') === currentPath) {
                link.classList.add('active');
            }
        });
    }
};

// Exportar los módulos
window.AlertManager = AlertManager;
window.NavManager = NavManager;