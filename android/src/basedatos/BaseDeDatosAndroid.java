package basedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import basededatos.BaseDeDatos;

/**declaracion de la clase BaseScreen
 * @author alfre
 * @version 10/03/20
 */
public class BaseDeDatosAndroid implements BaseDeDatos {
    private BDOpenHelper openHelper;
    //Constructor que recibe por parametro un Context
    public BaseDeDatosAndroid(Context c){
        openHelper=new BDOpenHelper(c,1);
    }

    /*
    Funcion cargar() carga los datos de la base de datos y permite usarlos en el juego
     */
    @Override
    public int[] cargar(){
        int[] puntuaciones=new int[4];
        SQLiteDatabase db=openHelper.getWritableDatabase();
        Cursor c=db.query("numeroMovimientos",null,null,null,null,null,null);

       if(c.moveToFirst()){

           puntuaciones[0]=c.getInt(c.getColumnIndex("movimientosAbajo"));
           puntuaciones[1]=c.getInt(c.getColumnIndex("movimientosArriba"));
           puntuaciones[2]=c.getInt(c.getColumnIndex("movimientosDerecha"));
           puntuaciones[3]=c.getInt(c.getColumnIndex("movimientosIzquierda"));
       }
       c.close();
       return puntuaciones;
    }

    /**
     *
     * @param movimientosArriba guarda el numero de veces que ha ido el personaje hacia arriba
     * @param movimientosAbajo guarda el numero de veces que ha ido el personaje hacia abajo
     * @param movimientosDerecha guarda el numero de veces que ha ido el personaje hacia derecha
     * @param movimientosIzquierda guarda el numero de veces que ha ido el personaje hacia izquierda
     */
     @Override
    public void guardar(int movimientosArriba, int movimientosAbajo, int movimientosDerecha, int movimientosIzquierda) {
        SQLiteDatabase db=openHelper.getWritableDatabase();
        Cursor c=db.query("numeroMovimientos",
                null,null,null,
                null,null,null);



        ContentValues cv=new ContentValues();
        cv.put("movimientosArriba",movimientosArriba);
        cv.put("movimientosAbajo",movimientosAbajo);
        cv.put("movimientosDerecha",movimientosDerecha);
        cv.put("movimientosIzquierda",movimientosIzquierda);

        if(c.moveToFirst()){ //False si no hay ninguna fila, true si hay una
            //Caso en que ya haya una fila
            //Siempre voy a tener solo una fila, por tanto, cuando actualizo
            //puedo dejar whereClause y whereArgs a null. Me va a actualizar
            //todas las filas, es decir, la única que existe.
            db.update("numeroMovimientos",cv,null,
                    null);
        }else{
            //Caso en que la tabla esté vacía
            db.insert("numeroMovimientos",null,cv);
        }
        c.close();
        db.close();
    }

}
