// Constantes
const STATS_URL = '/api/admin/stats';
const TOKEN_KEY = 'jwt_token';

// Variables globales
let statsChart = null;

// Funciones de utilidad
function getAuthHeader() {
    const token = localStorage.getItem(TOKEN_KEY);
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

function showSpinner() {
    document.getElementById('loadingSpinner').classList.remove('d-none');
}

function hideSpinner() {
    document.getElementById('loadingSpinner').classList.add('d-none');
}

// Funciones para manejar estadísticas
async function loadStats() {
    showSpinner();
    try {
        const response = await fetch(`${STATS_URL}/alumnos`, {
            headers: getAuthHeader()
        });
        const alumnosStats = await response.json();

        const materiasResponse = await fetch(`${STATS_URL}/materias`, {
            headers: getAuthHeader()
        });
        const materiasStats = await materiasResponse.json();

        updateStatsDisplay(alumnosStats, materiasStats);
        updateStatsChart(alumnosStats, materiasStats);
    } catch (error) {
        showError('Error al cargar estadísticas: ' + error.message);
    } finally {
        hideSpinner();
    }
}

function updateStatsDisplay(alumnosStats, materiasStats) {
    document.getElementById('totalAlumnos').textContent = alumnosStats.totalAlumnos;
    document.getElementById('totalMaterias').textContent = materiasStats.totalMaterias;
    document.getElementById('promedioMaterias').textContent =
        alumnosStats.promedioMateriasXAlumno.toFixed(1);
}

function updateStatsChart(alumnosStats, materiasStats) {
    const ctx = document.getElementById('statsChart').getContext('2d');

    if (statsChart) {
        statsChart.destroy();
    }

    statsChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Alumnos', 'Materias', 'Promedio Materias/Alumno'],
            datasets: [{
                label: 'Estadísticas',
                data: [
                    alumnosStats.totalAlumnos,
                    materiasStats.totalMaterias,
                    alumnosStats.promedioMateriasXAlumno
                ],
                backgroundColor: [
                    'rgba(91, 192, 222, 0.8)',
                    'rgba(240, 173, 78, 0.8)',
                    'rgba(92, 184, 92, 0.8)'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                },
                title: {
                    display: true,
                    text: 'Resumen Estadístico',
                    color: '#e0e0e0'
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    },
                    ticks: {
                        color: '#e0e0e0'
                    }
                },
                x: {
                    grid: {
                        color: 'rgba(255, 255, 255, 0.1)'
                    },
                    ticks: {
                        color: '#e0e0e0'
                    }
                }
            }
        }
    });
}

function showError(message) {
    AlertManager.showAlert(message, 'danger');
}

// Función de inicialización
document.addEventListener('DOMContentLoaded', function() {
    // Cargar estadísticas iniciales
    loadStats();

    // Configurar actualización periódica
    setInterval(loadStats, 300000); // Actualizar cada 5 minutos

    // Event listeners para botones de acción rápida
    document.querySelectorAll('.quick-action').forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const action = this.dataset.action;

            switch(action) {
                case 'refresh':
                    loadStats();
                    break;
                case 'export':
                    exportStats();
                    break;
                // Agregar más acciones según sea necesario
            }
        });
    });
});

// Función para exportar estadísticas
async function exportStats() {
    showSpinner();
    try {
        const alumnosResponse = await fetch(`${STATS_URL}/alumnos/export`, {
            headers: getAuthHeader()
        });
        const blob = await alumnosResponse.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'estadisticas.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
    } catch (error) {
        showError('Error al exportar estadísticas: ' + error.message);
    } finally {
        hideSpinner();
    }
}