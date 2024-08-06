export class Cliente {
    constructor(nombre, id, pwd, saldo) {
        this.nombre = String(nombre);
        this.id = parseInt(id);
        this.pwd = String(pwd);
        this.saldo = parseFloat(saldo);
    }
    
    getSaldo() {
        return this.saldo;
    }

    getNombre() {
        return this.nombre;
    }

    getPwd() {
        return this.pwd;
    }

    actualizarSaldo(monto) {
        monto = parseFloat(monto);
        if (isNaN(monto)) {
            throw new Error("El monto debe ser un número válido");
        }
        this.saldo += monto;
        // Evitar saldos negativos
        this.saldo = Math.max(this.saldo, 0);
    }
}