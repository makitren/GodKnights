package escuchadores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import personajes.Jugador;

public class Botones extends Actor {
    protected Texture texture;
    protected Sprite sprite;
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;

    public Botones(Jugador j) {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("Mapas/upButtonFinal.png"));
        this.sprite = new Sprite(texture);
        sprite.setBounds(Gdx.graphics.getWidth() / 18.9666f, Gdx.graphics.getHeight() / 6.4f, Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 11);

    }
    public static class BotonAbajo extends Botones {
        public BotonAbajo(Jugador j){
            super(j);
            shapeRenderer=new ShapeRenderer();
            texture=new Texture(Gdx.files.internal("Mapas/downButtonFinal.png"));
            this.sprite=new Sprite(texture);
            sprite.setBounds(Gdx.graphics.getWidth()/18.9625f,Gdx.graphics.getHeight()/54,Gdx.graphics.getWidth()/11,Gdx.graphics.getHeight()/11);

        }
    }

    public static class BotonDerecha extends Botones {
        public BotonDerecha(Jugador j){
            super(j);
            shapeRenderer=new ShapeRenderer();
            texture=new Texture(Gdx.files.internal("Mapas/rightButton.png"));
            this.sprite=new Sprite(texture);
            sprite.setBounds(Gdx.graphics.getWidth()/8.9666f,Gdx.graphics.getWidth()/21,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/13);

        }
    }

    public static class BotonIzquierda extends Botones {
        public BotonIzquierda(Jugador j){
            super(j);
            shapeRenderer=new ShapeRenderer();
            texture=new Texture(Gdx.files.internal("Mapas/leftButton.png"));
            this.sprite=new Sprite(texture);
            sprite.setBounds(0,Gdx.graphics.getWidth()/23,Gdx.graphics.getWidth()/11,Gdx.graphics.getHeight()/11);
        }
    }
    public void dibujarConHitbox(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.box(sprite.getX(),sprite.getY(),0,sprite.getWidth(),sprite.getHeight(),20);
        shapeRenderer.setColor(Color.BLUE);

        shapeRenderer.end();
    }


}




