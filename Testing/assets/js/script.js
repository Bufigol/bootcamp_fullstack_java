var message = document.getElementById("message");
var n1  = document.getElementById("n1");
var n2 = document.getElementById("n2");
var result = document.getElementById("result");

message.textContent ="Comparador";
document.getElementById("numberForm").addEventListener("submit", function(e){
    e.preventDefault();
    if(n1.value == "" || n2.value == ""){
        result.textContent = "Por favor, rellene todos los campos";
    }else{
        if(n1.value == n2.value){
            result.textContent = "Son iguales";
        }else{
            if(n1.value > n2.value){
                result.textContent = "El primero es mayor";
            }else{
                result.textContent = "El segundo es mayor";
            }
        }
    }

})