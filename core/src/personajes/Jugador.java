package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;

public class Jugador {
    private Sprite sprite;
    private OrthographicCamera camara; //La necesito para que me siga
    private Vector3 posicionTiles;
    private Batch batch;// La uso para dibujar en este batch al jugador. Podría pasarlo por constructor. Es decisión nuestra como programadoeres.

    //Variables para poder redimensionar al jugador según el zoom
    private TiledMap mapa; //Necesito el mapa para poder redimensionar al jugador
    private int anchuraMapaPixels; //Anchura del mapa donde nos movemos en pixels
    private int alturaMapaPixels; //Altura del mapa donde nos movemos en pixels
    private int anchuraMapaTiles; //Anchura del mapa donde nos movemos en  tiles
    private int alturaMapaTiles; //Anchura del mapa donde nos movemos en tiles

    public Jugador(OrthographicCamera camara,TiledMap mapa) {
        this.sprite = new Sprite(new Texture("Sprites/playerFemale.png"));
        this.camara = camara;
        posicionTiles=this.camara.position;
        batch=new SpriteBatch();
        this.mapa=mapa;
        anchuraMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        alturaMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        anchuraMapaPixels=anchuraMapaTiles*(int)mapa.getProperties().get("width");
        alturaMapaPixels=alturaMapaTiles*(int)mapa.getProperties().get("height");


        //Como siempre estaré en el medio, voy a poner el sprite en el medio
        //Como la cámara está centrada en el medio, voy a necesitar coger el tile de ahi
        Vector3 posPixels = camara.project(
                new Vector3(camara.position.x,camara.position.y,0));
        sprite.setPosition(posPixels.x,posPixels.y);
    }

    public void dibujar(){
        ajustarACamara();
        batch.begin();
        sprite.draw(batch);
        batch.end();
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
