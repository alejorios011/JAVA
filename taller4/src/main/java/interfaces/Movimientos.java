package interfaces;

public interface Movimientos {
    public void actualizarSaldo(Object objeto);
    public void depositar(double monto, Object objeto);

    public void retirar(double monto, Object objeto) throws Exception;

    public void transaccion(double monto, Object objeto);
}
