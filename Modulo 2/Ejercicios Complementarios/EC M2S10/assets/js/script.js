$(document).ready(function() {
  // Obtener los elementos necesarios
  const $text1 = $('#text1');
  const $text2 = $('#text2');
  const $caja2 = $('#caja2');
  const $caja3 = $('#caja3');
  const $imagen = $('#img');
  const $parrafo = $caja3.find('p');

  // Guardar el tamaño original de la imagen y del texto de la caja 3
  const tamañoOriginal = $imagen.width();

  // Obtener el tamaño de letra actual
  let tamañoActual = parseFloat($parrafo.css('font-size'));

  // Si no se puede obtener el tamaño, establecer un valor por defecto
  if (isNaN(tamañoActual)) {
      tamañoActual = 16; // 16px es generalmente el tamaño por defecto en muchos navegadores
  }

  // Ocultar text2 inicialmente
  $text2.hide();

  // Agregar eventos de mouse a text1
  $text1.hover(
      function() {
          $text2.show();
      },
      function() {
          $text2.hide();
      }
  );

  // Agregar eventos de clic y mouseleave a caja2
  $caja2.on('click', function() {
      $imagen.width(tamañoOriginal * 2); // Duplica el tamaño (100% más grande)
  }).on('mouseleave', function() {
      $imagen.width(tamañoOriginal);
  });

  // Agregar evento de doble clic a caja3
  $caja3.on('dblclick', function() {
      // Aumentar el tamaño de la letra en un 50%
      var nuevoTamaño = tamañoActual * 1.5;
      $parrafo.css('font-size', `${nuevoTamaño}px`);
  }).on('mouseleave', function() {
      $parrafo.css('font-size', `${tamañoActual}px`);
  });
});