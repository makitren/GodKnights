package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Jugador {
    private Sprite sprite;
    private World mundo;
    private BodyDef propiedadesCuerpo;
    private Body cuerpo;
    private FixtureDef propiedadesFisicasCuerpo;
    private OrthographicCamera camara; //La necesito para que me siga
    private Vector3 posicionTiles;
    private Batch batch;// La uso para dibujar en este batch al jugador. Podría pasarlo por constructor. Es decisión nuestra como programadoeres.

    //Variables para poder redimensionar al jugador según el zoom
    private TiledMap mapa; //Necesito el mapa para poder redimensionar al jugador
    private int anchuraMapaPixels; //Anchura del mapa donde nos movemos en pixels
    private int alturaMapaPixels; //Altura del mapa donde nos movemos en pixels
    private int anchuraMapaTiles; //Anchura del mapa donde nos movemos en  tiles
    private int alturaMapaTiles; //Anchura del mapa donde nos movemos en tiles

    public Jugador(OrthographicCamera camara,TiledMap mapa, World m) {
        mundo=m;
        sprite = new Sprite(new Texture("Sprites/playerFemale.png"));
        int anchuraSprite=1; //Anchura y altura se expresan ahora en metros
        int alturaSprite=1;//Anchura y altura se expresan ahora en metros
        sprite.setBounds(10,10,anchuraSprite,alturaSprite);
        this.camara = camara;
        posicionTiles=this.camara.position;
        batch=new SpriteBatch();
        this.mapa=mapa;
        anchuraMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        alturaMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        anchuraMapaPixels=anchuraMapaTiles*(int)mapa.getProperties().get("width");
        alturaMapaPixels=alturaMapaTiles*(int)mapa.getProperties().get("height");

        this.propiedadesCuerpo= new BodyDef(); //Establecemos las propiedades del cuerpo
        propiedadesCuerpo.type = BodyDef.BodyType.StaticBody;
        propiedadesCuerpo.position.set(sprite.getX(), sprite.getY());

        cuerpo = mundo.createBody(propiedadesCuerpo);

        propiedadesFisicasCuerpo = new FixtureDef();
        propiedadesFisicasCuerpo.shape = new PolygonShape();
        ((PolygonShape)propiedadesFisicasCuerpo.shape).setAsBox(anchuraSprite/1f, alturaSprite/1f);
        propiedadesFisicasCuerpo.density = 1f;
        cuerpo.createFixture(propiedadesFisicasCuerpo);

        sprite.setOrigin(this.sprite.getWidth()/2,
                this.sprite.getHeight()/2);

        //Como siempre estaré en el medio, voy a poner el sprite en el medio
        //Como la cámara está centrada en el medio, voy a necesitar coger el tile de ahi
        Vector3 posPixels = camara.project(
                new Vector3(camara.position.x,camara.position.y,0));
        sprite.setPosition(posPixels.x,posPixels.y);
    }

    public void dibujar(Batch batch, float parentAlpha){
       /*
        //Esta cuenta hace falta por lo de la media altura. Ese absurdo cálculo...
        Dibuja al personaje en una Posicion(Preguntar como hacer que el personaje se dibuje en el centro y no en una esquina

        */
        sprite.setPosition(cuerpo.getPosition().x-sprite.getWidth()/2,cuerpo.getPosition().y-sprite.getHeight()/2);
        //Sprite quiere la rotación en grados, el cuerpo la da en radianes. Esta constante convierte de uno a otro.
        sprite.setRotation(MathUtils.radiansToDegrees*cuerpo.getAngle());


        ajustarACamara();


        sprite.draw(batch);

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
    public float getX(){
        return this.cuerpo.getPosition().x;
    }


    public float getY(){
        return this.cuerpo.getPosition().y;
    }

    public Body getCuerpo(){
        return cuerpo;
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
                    posicionTiles.y++;
                }
                //Pongo la cámara donde esté el jugador, para que siempre quede centrado en el tile en que está
                //Recuerdo que el jugador no está de verdad en el tile: El dibujado
                //del sprite es independiente del dibujado del mapa, y solo estamos
                //moviendo el mapa para hacer parecer que el personaje se mueve.
                camara.position.y=posicionTiles.y;
                break;
            case 'd':
                if(posicionTiles.y>0) {
                    posicionTiles.y--;
                }
                camara.position.y=posicionTiles.y;
                break;
            case 'l':
                if(posicionTiles.x>0) {
                    posicionTiles.x--;
                }
                camara.position.x=posicionTiles.x;
                break;
            case 'r':
                if(posicionTiles.x<this.anchuraMapaTiles-1) {
                    posicionTiles.x++;
                }
                camara.position.x=posicionTiles.x;
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
