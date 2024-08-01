var opcion = prompt(
  "¡Hola! Soy Eva, tu asistente virtual del Servicio al Cliente de Mentel. Estoy aquí para ayudarte en lo que necesides.\nEscribe el número de la opción que buscas: \n1.- Boletas y Pagos \n2.- Señal y llamadas \n3.- Oferta comercial  \n4.- Otras consultas"
);

//FUNCIONES
var optInt = parseInt(opcion);
var innerOption = 0;
switch (optInt) {
  case 1:
    innerOption = prompt("1.- Ver Boleta \n2.- Pagar cuenta");
    case1(parseInt(innerOption));
    break;
  case 2:
    innerOption = prompt(
      "1.- Problemas con la señal \n2.- Problemas con las llamadas"
    );
    case2(parseInt(innerOption));
    break;
  case 3:
    case3();
    break;
  case 4:
    case2("");
    break;
  default:
    alert("Opcion no valida, gracias por preferir nuestros servicios");
    break;
}

function case1(input) {
  switch (input) {
    case 1:
      alert("Haga click aquí para descrgar la boleta");
      break;
    case 2:
      alert("Usted esta siendo transferido. Epere por favor...");
      break;
    default:
      alert("Opcion no valida");
      break;
  }
}
function case2() {
  let problem = prompt("A continuacion escriba su solicitud.");
  alert(
    "Estimado usuario, su solicitud: '" +
      problem +
      "' ha sido ingresada con exito. Pronto será contactado por uno de nuestros ejecutivos."
  );
}
function case3() {
  let seguir = true;
  do {
    let offer = prompt(
      "¡Mentel tiene una oferta comercial acorde a tus necesidades! \nPara conocer más información y ser asesorado personalmente, por un ejecutivo escribe 'SI' y un ejecutivo te llamara. De lo contrario ingrese 'NO'"
    );
    if (offer == "SI") {
      alert("Un ejecutivo se contactara con usted");
    } else {
      if (offer == "NO") {
        alert("Gracias por preferir nuestros servicios");
      } else {
        alert("Opcion no valida");
      }
    }
    if (offer != "SI" && offer != "NO") {
      seguir = false;
    }
  } while (seguir);
}
