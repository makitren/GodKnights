package basededatos;
/*
metodo Cargar() se encarga de cargar la base de datos
metodo guardar() se encarga de guardar los movimientos del jugador
 */

public interface BaseDeDatos {
    public int[] cargar();
    public void guardar(int movimientoArriba,int movimientoAbajo,int movimientoIzquierda,int movimientoDerecha);
}
