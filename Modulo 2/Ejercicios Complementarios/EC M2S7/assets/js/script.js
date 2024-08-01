var message = document.getElementById("message");
var n1  = document.getElementById("n1");
var n2 = document.getElementById("n2");
var mayor = document.getElementById("mayor");

message.textContent ="Comparador";
document.getElementById("numberForm").addEventListener("submit", function(e){
    e.preventDefault();
    if(n1.value == "" || n2.value == ""){
        result.textContent = "Por favor, rellene todos los campos";
    }else{
        let valor1 = parseInt(n1.value);
        let valor2 = parseInt(n2.value);
        result.textContent = "";
        if(valor1 == valor2){
            result.textContent = "Son iguales";
        }else{
            if(valor1 > valor2){
                mayor.textContent = "El primero es mayor";
            }else{
                mayor.textContent = "El segundo es mayor";
            }
        }
    }

})
var num1,num2,resultSum;
num1 = prompt("Ingresa el primer numero");
num2 = prompt("Ingresa el segundo numero");
num1 = parseInt(num1);
num2 = parseInt(num2);
resultSum = num1 + num2;
alert("La suma es: "+resultSum);
if(num1 == null || num2 == null || num1 == "" || num2 == "" || isNaN(num1) || isNaN(num2)){
    alert("Por favor, rellene todos los campos");
}else{
    if(num1 == num2){
        alert("Son iguales");
    }else{
        if(num1 > num2){
            alert("El primero es mayor");
        }else{
            alert("El segundo es mayor");
        }
    }
}