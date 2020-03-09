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

public class BaseScreen implements Screen {
    protected Juego game;
    protected World world;
    protected Actores stage;
    protected Stage pantalla;

    protected TiledMap map;
    protected static int WIDTH; //Aquí almacenaremos la anchura en tiles
    protected static int HEIGHT; //Aquí almacenaremos la altura en tiles
    protected MapProperties properties;
    protected AssetManager manager;
    protected int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    protected OrthographicCamera camera;
    protected Jugador jugador;
    public static final float unitScale = 1 / 32f;//Nos servirá para establecer que la pantalla se divide en tiles de 32 pixeles
    public static final float pixelsPorCuadro=32f;
    protected OrthogonalTiledMapRenderer renderer;
    protected ShapeRenderer shapeRenderer;
    protected TiledMapTileLayer terrainLayer,terrainLayer2,terrainLayer3;
    protected Colisiones colisiones;
    protected Table tableBotones;
    protected float w,h;
    protected BitmapFont bitmapFont;
    protected  Music sonidoPuerta;
    protected Music mapaSonido;

    protected int arriba,abajo,derecha,izquierda;
    protected BaseDeDatos baseDeDatos;
    boolean pulsado;


    public BaseScreen(Juego g){
        Gdx.app.log("Pixels Altura",Gdx.graphics.getHeight()+"");
        Gdx.app.log("Pixels Anchura",Gdx.graphics.getWidth()+"");
        sonidoPuerta=Gdx.audio.newMusic(Gdx.files.internal("raw/puerta.mp3"));
        sonidoPuerta.setVolume(10);



        game=g;

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
    mapaSonido.stop();
    mapaSonido.dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    mapaSonido.stop();
        mapaSonido.dispose();
    }

    @Override
    public void dispose() {
        jugador.dispose();
        renderer.dispose();
        pantalla.dispose();
    }
    public Music getMapaSonido() {
        return mapaSonido;
    }

}
