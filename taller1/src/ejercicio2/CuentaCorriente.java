package ejercicio2;

public class CuentaCorriente extends Cuenta {
    private float sobregiro;

    public CuentaCorriente(float saldo, float tasaAnual) {
        super(saldo, tasaAnual);
        // Inicilizamos los sobregiros en 0
        this.sobregiro = 0;
    }

    // Métodos


    @Override
    public void retirarSaldo(float retirar) {
        // Se puede retirar dinero superior al saldo, pero el dinero que se debe queda como sobregiro
        if (retirar > this.saldo) {
            this.sobregiro = this.saldo - retirar;
            // Dejamos el saldo en 0
            this.saldo = 0;
        } else {
            this.saldo = this.saldo - retirar;
            System.out.println("Aquí tiene su dinero: " + this.saldo);
            // Aumentamos el número de retiros
            this.numeroRetiros = this.numeroRetiros + 1;
        }
    }

    // Si hay sobregiro, la cantidad consignada reduce el sobregiro
    public void consignar(float consignar) {
        // Verificamos primero si existe un sobregiro
        if (this.sobregiro < 0){
            // Consignamos al sobregiro primero
            this.sobregiro = sobregiro + consignar;
            // Almacenamos lo que sobre al saldo de la cuenta
            if (this.sobregiro > 0) {
                // Al ser mayor a 0 quiere decir que el sobregiro tiene un saldo a favor por lo que ese es el valor que agregamos al saldo de la cuenta
                consignarSaldo(this.sobregiro);
                // Por último, dejamos el sobregiro en 0
                this.sobregiro = 0;
            }
        } else {
            consignarSaldo(consignar);
        }
    }

    @Override
    public void extractoMensual() {
        super.extractoMensual();
    }

    @Override
    public void imprimir() {
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Número de consignaciones: " + this.numeroConsignaciones);
        System.out.println("Número de retiros: " + this.numeroRetiros);
        System.out.println("Tasa anual: " + this.tasaAnual);
        System.out.println("Comisión Mensual: " + this.comisionMensual);
        System.out.println("Valor de Sobregiro: " + this.sobregiro);
    }
}
