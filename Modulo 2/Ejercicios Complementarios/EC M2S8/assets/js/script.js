do {
  var opcion = prompt(
    "1.- Opcion 1 \n2.- Opcion 2 \n3.- Opcion 3 \n4.- Opcion 4 \n5.- Salir"
  );
  if (opcion < 1 || opcion > 5) {
    alert("Opcion no valida, por favor ingrese una de las opciones");
  }

} while (opcion != 5);
