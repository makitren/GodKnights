package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import Mapas.Mapa1;
import actores.Actores;
import actores.Colisiones;

public class Jugador extends Actor {
    float x,y;
    private Sprite sprite;
    private Texture texture;
    private Mapa1 mapaInicial;
    private World mundo;
    private OrthographicCamera camara; //La necesito para que me siga
    private Vector3 posicionTiles;
    protected boolean colliding;
    private ShapeRenderer shapeRenderer;
    private Batch batch;// La uso para dibujar en este batch al jugador. Podría pasarlo por constructor. Es decisión nuestra como programadoeres.

    //Variables para poder redimensionar al jugador según el zoom
    private TiledMap mapa; //Necesito el mapa para poder redimensionar al jugador
    private int anchuraMapaPixels; //Anchura del mapa donde nos movemos en pixels
    private int alturaMapaPixels; //Altura del mapa donde nos movemos en pixels
    private int anchuraMapaTiles; //Anchura del mapa donde nos movemos en  tiles
    private int alturaMapaTiles; //Anchura del mapa donde nos movemos en tiles
    private Rectangle rectangle;
    private Rectangle[]rectangles;
    private Colisiones colisiones;

    public Jugador(int x, int y, float anchoJugador, float largoJugador,TiledMap mapa,World m) {
        this.x = x;
        this.y = y;
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        colisiones=new Colisiones();
        mapaInicial=new Mapa1();
        colisiones.checkCollision(mapaInicial.getMap(),this);
        mundo=m;
        texture=new Texture(Gdx.files.internal("Sprites/playerFemale.png"));
        this.sprite = new Sprite(texture);
        this.camara = camara;
        shapeRenderer=new ShapeRenderer();
        posicionTiles=this.camara.position;
        batch=new SpriteBatch();
        this.mapa=mapa;
        anchuraMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        alturaMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        anchuraMapaPixels=anchuraMapaTiles*(int)mapa.getProperties().get("width");
        alturaMapaPixels=alturaMapaTiles*(int)mapa.getProperties().get("height");
        sprite.setPosition(23,23);
    }

    public void dibujar(){
        batch.begin();
        sprite.draw(batch);
        batch.end();
        checkCollision(this);
        dibujarConHitbox();
    }

    /**
     * Esta función redimensiona el sprite del jugador según el tamaño del mapa,
     * el tamaño de la propia textura del jugador, y el zoom actual. Debería llamarse
     * en dibujar.
     */
    private void ajustarACamara(){
        sprite.setSize(
                ((Gdx.graphics.getWidth()*sprite.getTexture().getWidth())
                        /anchuraMapaPixels)*(1/camara.zoom)*5,
                ((Gdx.graphics.getHeight()*sprite.getTexture().getHeight())
                        /alturaMapaPixels)
                        *(1/camara.zoom)*5);
    }
    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }

    public void dibujarConHitbox(){

        batch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.box(sprite.getX(),sprite.getY(),0,sprite.getWidth(),sprite.getHeight(),20);
      if(this.colliding){
          shapeRenderer.setColor(Color.RED);
      }else{
          shapeRenderer.setColor(Color.BLUE);
      }
        sprite.draw(batch);
        shapeRenderer.end();
        batch.end();
    }


    public boolean checkCollision(Jugador c){
        boolean overlaps=getHitBox().overlaps(c.getHitBox());


        if(overlaps&&colliding==false){
            colliding=true;
            Gdx.app.log("Colisionando","con "+c.getClass().getName());
        }else if(!overlaps){
            colliding=false;
        }
        return colliding;
    }

    /**
     * Mueve el jugador un tile en la dirección establecida
     * @param direccion 'u' -> arriba,'d' -> abajo,'l' -> izda, 'r' -> derecha
     */
    public void mover(char direccion){
        switch (direccion){
            case 'u':
                //Cambio posición del jugador, todavía no cambia nada visualmente
                if(posicionTiles.y<this.alturaMapaTiles-1) {
                    sprite.setPosition(sprite.getX(), sprite.getY()+10);
                    System.out.println(sprite.getX()+""+sprite.getY());
                }
                //Pongo la cámara donde esté el jugador, para que siempre quede centrado en el tile en que está
                //Recuerdo que el jugador no está de verdad en el tile: El dibujado
                //del sprite es independiente del dibujado del mapa, y solo estamos
                //moviendo el mapa para hacer parecer que el personaje se mueve.
                break;
            case 'd':
                if(posicionTiles.y>0) {
                    sprite.setPosition(sprite.getX(), sprite.getY()-10);
                    System.out.println(sprite.getX()+""+sprite.getY());
                }
                break;
            case 'l':
                if(posicionTiles.x>0) {
                    sprite.setPosition(sprite.getX()-10, sprite.getY());
                    System.out.println(sprite.getX()+""+sprite.getY());
                }
                break;
            case 'r':
                if(posicionTiles.x<this.anchuraMapaTiles-1) {
                    sprite.setPosition(sprite.getX()+10, sprite.getY());
                    System.out.println(sprite.getX()+""+sprite.getY());
                }
                break;
        }
        camara.update();
    }

    public OrthographicCamera getCamara(){
        return camara;
    }

    public void dispose(){
        batch.dispose();
    }

}
