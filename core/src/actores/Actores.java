package actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.Renderer;

import escuchadores.TecladoJugador;

/**
 * Clase Actores se encarga de crear los actores y hacerlos visibles
 * @author alfre
 * @version 10/03/20
 */

public class Actores extends Actor {
    protected Sprite sprite; //La imagen del actor
    private Rectangle dimensiones;//Las dimensiones del actor
    private Batch batch;//Inicializacion del batch
    protected boolean colliding; //Nos detecta si está colisionando o no
    protected String nombre;//nombre del actor

    /**
     * @param rutaTextura recibe la ruta de la imagen que tendrá el actor
     * @param x recibe el tamaño de ancho que tendrá el actor
     * @param y recibe el tamaño de alto que tendrá el actor
     * @param posAlt recibe la posicion en altura que tendrá el actor respecto al mapa
     * @param posAnc recibe la posicion en anchura que tendrá el actor respecto al mapa
     */
    public Actores(String rutaTextura, float x, float y, float posAlt, float posAnc) {
        colliding=false;
        //Cambio Posición del Sprite
        sprite=new Sprite(new Texture(rutaTextura));// Recibe la imagen
        dimensiones=new Rectangle((int)x,(int)y,(int)posAlt,(int)posAnc);
        batch=new SpriteBatch();
        sprite.setBounds(x,y, x,y);//le da al sprite recibido las coordenadas
        this.setSize(Gdx.graphics.getWidth()*100,Gdx.graphics.getHeight()*100);
        sprite.setPosition(posAnc,posAlt);//coloca al actor en la posicion
        //Cambio posición del actor

        this.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
        sprite.setOrigin(this.sprite.getOriginX(),this.sprite.getOriginY());



    }

    /**
     * metodo dibujarConHitbox() se encarga de dibujar los actores.
     * metodo getHitBox() se encarga de obtener el hitbox de los actores
     * @return devuelve el rectangulo
     * metodo checkCollission() se encarga de comprobar si se choca contra el actor
     * @return si colisiona o no
     */

    public void dibujarConHitbox(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }



    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }

    public boolean checkCollision(Actores c){
        boolean overlaps=getHitBox().overlaps(c.getHitBox());
        if(overlaps&&colliding==false){
            colliding=true;
            Gdx.app.log("Colisionando","con "+c.getClass().getName());
        }else if(!overlaps){
            colliding=false;
        }
        return colliding;
    }
}
