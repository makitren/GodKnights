package actores;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import personajes.Jugador;

public class Colisiones {
    private Actor[]actores;
    private Actor[]actores2;
    private Rectangle[]rect;
    private Rectangle jugador;
    private RectangleMapObject[] obj2;
    private Rectangle[]salida;


    public void checkCollision(TiledMap map, Jugador personaje) {
        jugador=new Rectangle();
        //this.widthEsc=width;
        //this.heightEsc=height;
        jugador.set(personaje.getX(),personaje.getY(),personaje.getWidth(),personaje.getHeight());
        MapObjects mons = map.getLayers().get("Colisionables").getObjects();
       // MapObjects mons2 = map.getLayers().get("Entrada").getObjects();
        actores=new Actor[mons.getCount()];
        rect=new Rectangle[mons.getCount()];
        for (int i = 0;i < mons.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons.get(i);
            Rectangle rect1 = obj1.getRectangle();
            rect[i]=new Rectangle((rect1.x*2),(rect1.y*2),(rect1.width*2),(rect1.height*2));
            actores[i]=new Actor();
            actores[i].setBounds(rect1.x*2,rect1.y*2,rect1.width*2,rect1.height*2);

        }
        MapObjects mons2=map.getLayers().get("Salida").getObjects();
        obj2=new RectangleMapObject[mons2.getCount()];
        salida=new Rectangle[mons2.getCount()];
        actores2=new Actor[mons2.getCount()];
        for (int i=0; i<mons2.getCount();i++){
            RectangleMapObject obj1=(RectangleMapObject)mons2.get(i);
            //System.out.println("Nombre: "+obj1.getName());
            obj2[i]=obj1;
            Rectangle rect1=obj1.getRectangle();
            salida[i]=new Rectangle(rect1.x*2,rect1.y*2,rect1.width*2,rect1.height*2);
            actores2[i]=new Actor();
            actores2[i].setBounds(rect1.x*2,rect1.y*2,rect1.width*2,rect1.height*2);

        }

    }

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
