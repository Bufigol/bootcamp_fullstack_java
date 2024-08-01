class Cliente{
    constructor(nombre,id,pwd,saldo){
        this.nombre =String(nombre);
        this.id=parseInt(id);
        this.pwd=String(pwd);
        this.saldo=parseFloat(saldo);
    }
    constructor(){
        this.nombre =String("");
        this.id=parseInt(0);
        this.pwd=String("");
        this.saldo=parseFloat(1.000);
        this.saldo=this.saldo-1;
    }
    
    getSaldo() {
        return this.saldo;
    }
    getNombre() {
        return this.nombre;
    }
    getPwd(){
        return this.pwd;
    }
    actualizarSaldo(input) {
        this.saldo=this.saldo+input;
    }
}