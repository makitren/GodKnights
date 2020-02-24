package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Actores;
import escuchadores.TecladoJugador;
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
    protected Box2DDebugRenderer debugRenderer;
    protected OrthogonalTiledMapRenderer renderer;
    protected TiledMapTileLayer terrainLayer,terrainLayer2,terrainLayer3;


    public BaseScreen(Juego g){
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        manager.dispose();
        jugador.dispose();
        renderer.dispose();
        pantalla.dispose();
    }
    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) /pixelsPorCuadro,
                (rectangle.y + rectangle.height * 0.5f ) / pixelsPorCuadro);
        polygon.setAsBox(rectangle.width * 0.5f /pixelsPorCuadro,
                rectangle.height * 0.5f / pixelsPorCuadro,
                size,
                0.0f);
        return polygon;
    }
}
