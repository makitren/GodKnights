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
    private Actor[]actores;
    private Actor[]actores2;
    private Rectangle[]rect;
    private Rectangle jugador;
    private RectangleMapObject[] obj2;
    private Rectangle[]salida;


    public void checkCollision(TiledMap map,float w,float h) {

        MapObjects mons = map.getLayers().get("Colisionables").getObjects();

        actores=new Actor[mons.getCount()];
        rect=new Rectangle[mons.getCount()];
        for (int i = 0;i < mons.getCount(); i++) {
            RectangleMapObject obj1 = (RectangleMapObject) mons.get(i);
            Rectangle rect1 = obj1.getRectangle();
            rect[i]=rect1;
            rect[i]=new Rectangle((rect1.x*w),(rect1.y*h),(rect1.width*w),(rect1.height*h));

            actores[i]=new Actor();
            actores[i].setBounds(rect1.x*w,rect1.y*h,rect1.width*w,rect1.height*h);
            actores[i].setTouchable(Touchable.disabled);
        }
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
