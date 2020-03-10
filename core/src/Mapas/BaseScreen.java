package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Actores;
import actores.Colisiones;
import basededatos.BaseDeDatos;
import personajes.Jugador;
/**declaracion de la clase BaseScreen
 * @author alfre
 * @version 10/03/20
 */
public class BaseScreen implements Screen {
    protected Juego game;//Variable tipo Juego
    protected Stage pantalla;//variable de tipo Stage que se encarga de tener los actores

    protected TiledMap map;//variable tipo TiledMap donde se guarda el mapa
    protected static int WIDTH; //Aquí almacenaremos la anchura en tiles
    protected static int HEIGHT; //Aquí almacenaremos la altura en tiles
    protected MapProperties properties;//Recoge las propiedasdes del mapa
    protected int tileWidth, tileHeight,//Recoge la anchura y altura de los tiles, Anchura y Altura en Tiles, y Anchura y Altura en Pixels
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    protected OrthographicCamera camera;//Camara del juego
    protected Jugador jugador;//Variable de tipo Jugador
    public static final float unitScale = 1 / 32f;//Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles
    public static final float pixelsPorCuadro = 32f;
    protected OrthogonalTiledMapRenderer renderer;//Variable de tipo OrthogonalTiledMapRenderer que se encarga de renderizar el mapa
    protected ShapeRenderer shapeRenderer;//Variable que dibuja los hitbox de los objetos y personaje
    protected TiledMapTileLayer terrainLayer, terrainLayer2;// Capas del mapa que se dibujan
    protected Colisiones colisiones;//variable de tipo Colisiones
    protected Table tableBotones;//variable de tipo Table que guardará los botones para moverse
    protected float w, h;//width y height del mapa
    protected BitmapFont bitmapFont;//Letras que se insertan en la pantalla
    protected Music sonidoPuerta;//Sonido que se ejecutará cada vez que se cierre un mapa
    protected Music mapaSonido;//Sonido que se ejecutará durante el juego

    protected int arriba, abajo, derecha, izquierda;//Contador que guardará el dato de los movimientos del jugador
    protected BaseDeDatos baseDeDatos;//objeto de tipo BaseDeDatos
    boolean pulsado;//booleano que permite que el jugador pueda andar solo pulsando una vez el boton

    /**
     *
     * @param g se le pasa parametro de tipo Juego y cada vez que haga dispose del mapa sonará la puerta
     */
    public BaseScreen(Juego g) {
        Gdx.app.log("Pixels Altura", Gdx.graphics.getHeight() + "");
        Gdx.app.log("Pixels Anchura", Gdx.graphics.getWidth() + "");
        sonidoPuerta = Gdx.audio.newMusic(Gdx.files.internal("raw/puerta.mp3"));
        sonidoPuerta.setVolume(10);


        game = g;

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }


    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        jugador.dispose();
        renderer.dispose();
        pantalla.dispose();
    }
}
