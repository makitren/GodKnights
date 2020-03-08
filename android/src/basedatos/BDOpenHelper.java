package basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDOpenHelper extends SQLiteOpenHelper {

    public BDOpenHelper(Context c, int version){
        super(c,"baseDatosGodKnight",null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table numeroMovimientos(movimientosAbajo int(5) default 0 primary key, movimientosArriba int(5) default 0, movimientosDerecha int(5) default 0,movimientosIzquierda int(5) default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
