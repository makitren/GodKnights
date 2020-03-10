package actores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import personajes.Jugador;

public class Colisiones {
    private Actor[]actores;//actores carga un objeto de tipo Actor, el cual se encarga de recoger las colisiones del mapa
    private Actor[]actores2;//actores2 carga un objeto de tipo Actor, que se encarga de recoger las colisiones de las salidas y entradas del mapa
    private Rectangle[]rect;// rect recoge los rectangulos de las colisiones
    private Rectangle jugador;
    private RectangleMapObject[] obj2;//recoge los rectangulos de las colisiones de las salidas del mapa
    private Rectangle[]salida;//salida guarda todas las salidas en un objeto de Rectangle[]
/**
 * Clase Actores se encarga de crear los actores y hacerlos visibles
 * @author alfre
 * @version 10/03/20
 */
    /**
     *
     * @param map parametro que necesita un objeto de Tipo TiledMap
     * @param w parametro que se le pasa a la colision para que reescale
     * @param h parametro que se le pasa a la colision para que reescale
     */
    public void checkCollision(TiledMap map,float w,float h) {
        //Obtiene la capa de objetos Colisionables del mapa que se le ha pasado
        MapObjects mons = map.getLayers().get("Colisionables").getObjects();
        /*
        actores obtiene la cuenta de actores que va a haber en el mapa en dicha capa
        rect saca los rectangulos
         */
        actores=new Actor[mons.getCount()];
        rect=new Rectangle[mons.getCount()];
        /*
        Saco los actores del mapa y los guardo en el array correspondiente.
        Los reescalo con w e y
         */
        for (int i = 0;i < mons.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons.get(i);
            Rectangle rect1 = obj1.getRectangle();
            rect[i]=rect1;
            rect[i]=new Rectangle((rect1.x*w),(rect1.y*h),(rect1.width*w),(rect1.height*h));

            actores[i]=new Actor();
            actores[i].setBounds(rect1.x*w,rect1.y*h,rect1.width*w,rect1.height*h);
            actores[i].setTouchable(Touchable.disabled);
        }
        //Mismo proceso en este apartado en comparacion con el de arriba, lo unico que cambia es la capa de objetos Salida
        MapObjects mons2=map.getLayers().get("Salida").getObjects();
        obj2=new RectangleMapObject[mons2.getCount()];
        salida=new Rectangle[mons2.getCount()];
        actores2=new Actor[mons2.getCount()];
        for (int i=0; i<mons2.getCount();i++){
            RectangleMapObject obj1=(RectangleMapObject)mons2.get(i);

            obj2[i]=obj1;
            Rectangle rect1=obj1.getRectangle();
            salida[i]=new Rectangle(rect1.x*w,rect1.y*h,rect1.width*w,rect1.height*h);
            actores2[i]=new Actor();
            actores2[i].setBounds(rect1.x*w,rect1.y*h,rect1.width*w,rect1.height*h);

        }
    }

    /**
     * metodo Actor[] getActores
     * @return actores. Devuelve los actores del mapa
     *
     * metodo Rectangle[] getRect
     * @return rect. Devuelve los rectangulos del mapa
     *
     * metodo RectangleMapObject[] getObj2
     * @return obj2. Devuelve los actores de la segunda capa del mapa
     *
     * metodo Rectangle[] getSalida
     * @return salida. Devuelve las salidas
     */
    public Actor[] getActores() {
        return actores;
    }

    public Rectangle[] getRect() {

        return rect;
    }
    public RectangleMapObject[] getObj2() {
        return obj2;
    }
    public Rectangle[] getSalida() {
        return salida;
    }
}
