// Obtener los elementos necesarios
const text1 = document.getElementById('text1');
const text2 = document.getElementById('text2');
const caja2 = document.getElementById('caja2');
const caja3 = document.getElementById('caja3');
const imagen = document.getElementById('img');
const parrafo = caja3.querySelector('p');

// Guardar el tamaño original de la imagen y del texto de la caja 3
const tamañoOriginal = imagen.width;


// Obtener el tamaño de letra actual
let tamañoActual = window.getComputedStyle(parrafo).fontSize;
tamañoActual = parseFloat(tamañoActual);

// Si no se puede obtener el tamaño, establecer un valor por defecto
if (isNaN(tamañoActual)) {
    tamañoActual = 16; // 16px es generalmente el tamaño por defecto en muchos navegadores
}

// Ocultar text2 inicialmente
text2.style.display = 'none';

// Agregar eventos de mouse a text1
text1.addEventListener('mouseenter', () => {
  text2.style.display = 'block';
});

text1.addEventListener('mouseleave', () => {
  text2.style.display = 'none';
});

// Agregar eventos de clic a caja2
caja2.addEventListener('click', () => {
    imagen.width = tamañoOriginal * 2; // Duplica el tamaño (100% más grande)
  });
  
  // Agregar evento para volver al tamaño original cuando el mouse sale de caja2
  caja2.addEventListener('mouseleave', () => {
    imagen.width = tamañoOriginal;
  });

// Agregar evento de doble clic a caja3
caja3.addEventListener('dblclick', () => {
    // Aumentar el tamaño de la letra en un 50%
    var nuevoTamaño = tamañoActual * 1.5;
    parrafo.style.fontSize = `${nuevoTamaño}px`;
});