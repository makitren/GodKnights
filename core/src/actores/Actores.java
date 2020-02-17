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

import java.util.ArrayList;
import java.util.HashSet;

import escuchadores.TecladoJugador;

public class Actores extends Actor {
    protected Sprite sprite;
    private Rectangle dimensiones;
    private Batch batch;
    protected boolean colliding; //Nos detecta si está colisionando o no
    protected String nombre;
    ShapeRenderer shapeRenderer;

    public Actores(String rutaTextura) {
        //Cambio Posición del Sprite
        sprite=new Sprite(new Texture(rutaTextura));
        batch=new SpriteBatch();
        sprite.setBounds(0,0, Gdx.graphics.getWidth()*10,Gdx.graphics.getHeight()*10);
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/7);
        sprite.setPosition(30,23);
        this.setPosition(0,0); //Cambio posición del actor
        this.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
    }


    public Actores(String rutaTextura, float x, float y, float posAlt, float posAnc) {
        shapeRenderer=new ShapeRenderer();
        //Cambio Posición del Sprite
        sprite=new Sprite(new Texture(rutaTextura));
        dimensiones=new Rectangle((int)x,(int)y,(int)posAlt,(int)posAnc);
        batch=new SpriteBatch();
        sprite.setBounds(x,y, 50,50);
        this.setSize(Gdx.graphics.getWidth()*100,Gdx.graphics.getHeight()*100);
        sprite.setPosition(posAnc,posAlt);
        this.setPosition(posAnc,posAlt); //Cambio posición del actor
        this.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
    }
    public void dibujar(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }
    /*
    public void dibujarConHitbox(){
        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.box(20,20,0,40,20,20);
        shapeRenderer.setColor(Color.BLUE);
        sprite.draw(batch);
        shapeRenderer.end();
        batch.end();
    }
    */


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
