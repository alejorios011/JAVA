package ejercicio2;

public class Cuenta {
    protected float saldo;
    protected int numeroConsignaciones;
    protected int numeroRetiros;
    protected float tasaAnual;
    protected float comisionMensual;

    // Constructor
    protected Cuenta(float saldo, float tasaAnual) {
        this.saldo = saldo;
        this.tasaAnual = (tasaAnual*100)/100;
        // Inicializamos algunos de los valores en 0
        this.numeroConsignaciones = 0;
        this.numeroRetiros = 0;
        this.comisionMensual = 0;
    }

    // Métodos
    public void consignarSaldo(float consginar) {
        this.saldo = this.saldo + consginar;
        // Lo mostramos en pantalla para poder visualizar la transacción
        System.out.println("Transaccion realizada exitosamente por un valor de: " + consginar);
        // Aumentamos el número de consignaciones
        this.numeroConsignaciones = this.numeroConsignaciones + 1;
    }

    public void retirarSaldo(float retirar) {
        // El valor a retirar no debe superar el saldo por lo que debemos asegurarlo
        if (retirar > this.saldo) {
            System.out.println("Lo sentimos, no tiene saldo suficiente");
        } else {
            this.saldo = this.saldo - retirar;
            System.out.println("Aquí tiene su dinero: " + retirar);
            // Aumentamos el número de retiros
            this.numeroRetiros = this.numeroRetiros + 1;
        }
    }

    public void calcularInteresMensual() {
        float tasaMensual = tasaAnual/12;
        float interesMensual = saldo * tasaMensual;
        this.saldo = this.saldo - interesMensual;
        System.out.println("El interes mensual es de: " + interesMensual);
    }

    public void extractoMensual() {
        this.saldo = this.saldo - this.comisionMensual;
        calcularInteresMensual();
    }

    // Muestra en pantalla los valores de los atributos
    public void imprimir(){
        System.out.println("Saldo: " + this.saldo);
        System.out.println("Número de consignaciones: " + this.numeroConsignaciones);
        System.out.println("Número de retiros: " + this.numeroRetiros);
        System.out.println("Tasa anual: " + this.tasaAnual);
        System.out.println("Comisión Mensual: " + this.comisionMensual);
    }

    public float getSaldo() {
        return saldo;
    }

    public int getConsignaciones() {
        return numeroConsignaciones;
    }

    public int getRetiros() {
        return numeroRetiros;
    }

    public float getTasaAnual() {
        return tasaAnual;
    }

    public float getComisionMensual() {
        return comisionMensual;
    }
}
