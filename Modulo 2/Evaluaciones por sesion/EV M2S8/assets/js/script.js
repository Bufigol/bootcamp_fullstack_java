import { Cliente } from "./Cliente.js";

const clientes = [
  new Cliente("Eva", 1, "123", 12.0),
  new Cliente("Eduardo", 2, "456", 15.0),
  new Cliente("Pedro", 3, "789", 20.0),
  new Cliente("Carlos", 4, "012", 25.0),
  new Cliente("Luis", 5, "345", 30.0),
];

let clienteActual = null;
let check = false;
let usr;

function checkIfUserExists(usr) {
  for (let i = 0; i < clientes.length; i++) {
    const element = clientes[i];
    if (element.getNombre() === usr) {
      clienteActual = element;
      return true;
    }
  }
  return false;
}

function realizarGiro() {
  let giro = parseFloat(prompt(
    `Su saldo es: ${clienteActual.getSaldo()}\nIngrese el monto que desea girar`
  ));
  while (isNaN(giro) || giro < 0 || giro > clienteActual.getSaldo()) {
    giro = parseFloat(prompt(
      `Monto inválido. Su saldo es: ${clienteActual.getSaldo()}\nIngrese el monto que desea girar`
    ));
  }
  clienteActual.actualizarSaldo(-giro);
  alert(`Giro realizado. Su nuevo saldo es: ${clienteActual.getSaldo()}`);
}

function realizarDeposito() {
  let deposito = parseFloat(prompt(
    `Su saldo es: ${clienteActual.getSaldo()}\nIngrese el monto que desea depositar`
  ));
  while (isNaN(deposito) || deposito < 0) {
    deposito = parseFloat(prompt(
      `Monto inválido. Su saldo es: ${clienteActual.getSaldo()}\nIngrese el monto que desea depositar`
    ));
  }
  clienteActual.actualizarSaldo(deposito);
  alert(`Depósito realizado. Su nuevo saldo es: ${clienteActual.getSaldo()}`);
}

function iniciarSesion() {
  do {
    usr = prompt("Ingrese su usuario");
    check = checkIfUserExists(usr);
    if (!check) {
      alert("Usuario no encontrado. Por favor, intente de nuevo.");
    }
  } while (!check);

  check = false;
  do {
    const pwd = prompt("Ingrese su contraseña");
    if (clienteActual.getPwd() === pwd) {
      check = true;
    } else {
      alert("Contraseña incorrecta. Por favor, intente de nuevo.");
    }
  } while (!check);

  alert(`Bienvenido ${clienteActual.getNombre()} a Banca en Línea`);
}

function menuPrincipal() {
  let opcion;
  do {
    opcion = prompt(
      "1.- Ver saldo\n2.- Realizar giro\n3.- Realizar depósito\n4.- Salir"
    );
    opcion = parseInt(opcion);

    switch (opcion) {
      case 1:
        alert(`Su saldo es: ${clienteActual.getSaldo()}`);
        break;
      case 2:
        realizarGiro();
        break;
      case 3:
        realizarDeposito();
        break;
      case 4:
        alert("Gracias por usar nuestro servicio. ¡Hasta pronto!");
        break;
      default:
        alert("Opción no válida, por favor ingrese una de las opciones");
        break;
    }
  } while (opcion !== 4);
}

// Iniciar la aplicación
iniciarSesion();
menuPrincipal();