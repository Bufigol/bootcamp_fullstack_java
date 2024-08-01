import { Cliente } from "./clientes.js";

const clientes = [
  new Cliente("Eva", 1, "123", 12.0),
  new Cliente("Eduardo", 2, "456", 15.0),
  new Cliente("Pedro", 3, "789", 20.0),
  new Cliente("Carlos", 4, "012", 25.0),
  new Cliente("Luis", 5, "345", 30.0),
];
var clienteActual = null;

var check;
var usr;
do {
  usr = prompt("Ingrese su usuario");
  check = checkIfUserExists(usr);
} while (check);
do {
  check = false;
  var pwd = prompt("Ingrese su password");
  if (clienteActual.getPwd() == pwd) {
    check = true;
  }
} while (!check);

alert("Bienvenido " + clienteActual.getNombre() + " a Banca en Linea");

do {
  var opcion = prompt(
    "1.- Ver saldo \n2.- Realizar giro \n3.- Realizar depósito  \n4.- Salir"
  );
  if (opcion < 1 || opcion > 4) {
    alert("Opcion no valida, por favor ingrese una de las opciones");
  }

  switch (parseInt(opcion)) {
    case 1:
      alert("Su saldo es: " + clienteActual.getSaldo());
      break;
    case 2:
      realizarGiro();
      break;
    case 3:
      alert("Su saldo es: " + clienteActual.getSaldo());
      realizarDeposito();
      break;
    default:
      alert("Opcion no valida, por favor ingrese una de las opciones");
      break;
  }
} while (opcion != 4);

function checkIfUserExists(usr) {
  var out = true;
  for (let i = 0; i < clientes.length; i++) {
    const element = clientes[i];
    if (element.getNombre() == usr) {
      out = true;
      clienteActual = element;
    }
  }
  return out;
}

function realizarGiro() {
  var giro = prompt(
    "Su saldo es: " +
      clienteActual.getSaldo() +
      "\n ingrese el monto que desea girar"
  );
  giro = parseFloat(giro);
  while (giro < 0 || giro > clienteActual.getSaldo()) {
    giro = prompt(
      "Su saldo es: " +
        clienteActual.getSaldo() +
        "\n ingrese el monto que desea girar"
    );
    giro = parseFloat(giro);
  }
  clienteActual.actualizarSaldo(-giro);
  alert("Giro realizado.Su nuevo saldo es: " + clienteActual.getSaldo());
}

function realizarDeposito() {
  var deposito = prompt(
    "Su saldo es: " +
      clienteActual.getSaldo() +
      "\n ingrese el monto que desea depositar"
  );
  deposito = parseFloat(deposito);
  while (deposito < 0) {
    deposito = prompt(
      "Su saldo es: " +
        clienteActual.getSaldo() +
        "\n ingrese el monto que desea depositar"
    );
    deposito = parseFloat(deposito);
  }
  clienteActual.actualizarSaldo(deposito);
  alert("Deposito realizado.Su nuevo saldo es: " + clienteActual.getSaldo());
}
