package ejercicio2;

public class CuentaAhorros extends Cuenta {
    private boolean estadoCuenta;

    public CuentaAhorros(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
        verificarCuenta();
    }

    public void verificarCuenta() {
        if(this.saldo < 10.000) {
            // Quiere decir que la cuenta esta inactiva
            this.estadoCuenta = false;
        } else {
            // De lo contrario esta activa
            this.estadoCuenta = true;
        }
    }

    // Métodos
    @Override
    public void consignarSaldo(float consginar) {
        // Verificamos que la cuenta este activa usando el método creado
        verificarCuenta();
        // Si lo esta, se puede consignar el saldo
        if (this.estadoCuenta == true) {
            this.saldo = this.saldo + consginar;
            // Lo mostramos en pantalla para poder visualizar la transacción
            System.out.println("Transaccion realizada exitosamente por un valor de: " + consginar);
            // Aumentamos el número de consignaciones
            this.numeroConsignaciones = this.numeroConsignaciones + 1;
        } else {
            System.out.println("La cuenta esta inactiva");
        }
    }

    @Override
    public void retirarSaldo(float retirar) {
        // Primero verificamos que la cuenta este activa
        verificarCuenta();
        // Si lo esta, se puede retirar el saldo
        if (this.estadoCuenta == true) {
            if (this.numeroRetiros > 4) {
                // Si el número de retiros es mayor que 4, por cada retiro adicional,
                // se cobra $1000 como comisión mensual.
                this.comisionMensual = 1000;
                // Efectuamos el cobro en el saldo y hacemos el retiro
                this.saldo = (this.saldo - retirar) - this.comisionMensual;
                System.out.println("Aquí tiene su dinero: " + retirar + " Se cobro una comision de $1000 debido a que supero los 4 retiros mensuales");
                // Tanbién, aumentamos el número de retiros
                this.numeroRetiros = this.numeroRetiros + 1;
            } else {
                // De lo contrario, simplemente hacemos el retiro
                this.saldo = this.saldo - retirar;
                System.out.println("Aquí tiene su dinero: " + retirar);
                // Tanbién, aumentamos el número de retiros
                this.numeroRetiros = this.numeroRetiros + 1;
            }
        }
        // De lo contrario la cuenta esta inactiva y no puede hacer transacciones con esta
        else {
            System.out.println("La cuenta esta inactiva");
        }
    }

    @Override
    public void extractoMensual() {
        super.extractoMensual();
        // Al generar el extracto, se determina si la cuenta está activa o no con el saldo.
        verificarCuenta();
        System.out.println(this.estadoCuenta == true ? "Estado de la cuenta: Activa" : "Estado de la cuenta: Inactiva");
    }

    public void imprimir(){
        System.out.println("Saldo de la cuenta: " + this.saldo);
        System.out.println("Comisión mensual: " + this.comisionMensual);
        System.out.println("Transacciones realizadas: ");
        System.out.println("- Consignaciones: " + this.numeroConsignaciones);
        System.out.println("- Retiros: " + this.numeroRetiros);
    }
}
