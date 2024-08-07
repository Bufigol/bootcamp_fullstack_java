// Array de tareas
var tareas = [
    { tarea: "Pintar la fachada de la casa" },
    { tarea: "Comprar comida para el perro" },
    { tarea: "Pagar la tarjeta de crédito" }
];

// Función para cargar las tareas en la tabla
function cargarTareas() {
    const tabla = document.querySelector('.container.mt-5 table');
    tabla.innerHTML = '<tr><th>Tarea</th><th>Finalizar</th></tr>';
    
    tareas.forEach((item, index) => {
        const fila = tabla.insertRow();
        const celdaTarea = fila.insertCell(0);
        const celdaFinalizar = fila.insertCell(1);
        
        celdaTarea.textContent = item.tarea;
        
        const botonFinalizar = document.createElement('button');
        botonFinalizar.textContent = 'Finalizar';
        botonFinalizar.className = 'btn btn-sm btn-danger';
        botonFinalizar.onclick = () => eliminarTarea(index);
        
        celdaFinalizar.appendChild(botonFinalizar);
    });
}

// Función para eliminar una tarea
function eliminarTarea(index) {
    tareas.splice(index, 1);
    cargarTareas();
}

// Función para mostrar/ocultar el formulario
function toggleFormulario() {
    const formulario = document.getElementById('formulario');
    if (formulario.style.display === 'none') {
        formulario.style.display = 'block';
    } else {
        formulario.style.display = 'none';
    }
}

// Función para agregar una nueva tarea
function agregarTarea() {
    const nuevaTareaInput = document.getElementById('nuevaTarea');
    const nuevaTarea = nuevaTareaInput.value.trim();
    
    if (nuevaTarea) {
        tareas.push({ tarea: nuevaTarea });
        nuevaTareaInput.value = '';
        cargarTareas();
        toggleFormulario(); // Oculta el formulario después de agregar
    }
}

// Cargar las tareas cuando se carga la página
document.addEventListener('DOMContentLoaded', () => {
    cargarTareas();
    
    // Agregar evento para mostrar/ocultar el formulario
    const toggleFormBtn = document.getElementById('toggleFormBtn');
    toggleFormBtn.addEventListener('click', toggleFormulario);
    
    // Agregar evento para el botón de agregar tarea
    const agregarTareaBtn = document.getElementById('agregarTareaBtn');
    agregarTareaBtn.addEventListener('click', agregarTarea);
});