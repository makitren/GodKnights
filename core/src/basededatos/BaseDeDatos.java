package basededatos;

public interface BaseDeDatos {
    public int[] cargar();
    public void guardar(int movimientoArriba,int movimientoAbajo,int movimientoIzquierda,int movimientoDerecha);
}
