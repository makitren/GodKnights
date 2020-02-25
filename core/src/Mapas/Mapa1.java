package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
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


public class Mapa1 extends BaseScreen {
    private Juego juego;
    public Mapa1(Juego g){
        super(g);
        this.juego=g;
        world=new World(new Vector2(0,-9.8f),true);
        //world=new World(new Vector2(0,-9.8f),true);
        pantalla=new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        camera=new OrthographicCamera(640.f,480.f);
        manager.load("InteriorCasaInicialFinal.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("InteriorCasaInicialFinal.tmx", TiledMap.class);
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);

        jugador=new Jugador(map,world,camera,280,40);
        System.out.println(mapWidthInTiles);//El sout de mapWidthInTiles y Heigh da la altura y anchura del mapa, el de Gdx da el viewportWidth y Heigth
        System.out.println(mapHeightInTiles);
        //
        debugRenderer=new Box2DDebugRenderer();

       camera.zoom=0.1f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
        WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa

        camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:
        camera.position.x=WIDTH/2;
        camera.position.y=HEIGHT/2;

        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);
        camera.position.x = mapWidthInPixels / 2f;
        camera.position.y = mapHeightInPixels / 2f;

        renderer = new OrthogonalTiledMapRenderer(map);

        MapLayers mapLayers = map.getLayers();

        terrainLayer = (TiledMapTileLayer) mapLayers.get("Suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("Cosas");

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new TecladoJugador(jugador));
        Gdx.input.setInputProcessor(multiplexer);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public int getMapWidthInPixels() {
        return mapWidthInPixels;
    }

    public int getMapHeightInPixels() {
        return mapHeightInPixels;
    }

    @Override
    public void show() {


    }
    /*
dispose() Invocado cuando se liberan todos los recursos
hide() Invocado cuando esta Screen ya no es la actual
pause() Invocado cuando la Screen pasa a segundo plano
render() Invocado como un bucle para renderizar la Screen actual
resize() Invocado cuando se redimensiona la pantalla
resume() Invocado cuando la Screenpasa a primer plano
show() Invocado en el momento en que esta Screen pasa a ser la actual
 */

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        //limpia el fondo de pantalla con el color indicado
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //indica los elementos que se meten en el batch
         //Establecemos la vista del mundo a través de la cámara.
        //camera.update();
        //renderer.render(); //Renderizamos la vista
        super.render(delta);
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer);
        renderer.getBatch().end();
        jugador.dibujar();
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer2);
        renderer.getBatch().end();
        /*
        pantalla.act(Gdx.graphics.getDeltaTime());

        */
        pantalla.setDebugAll(true);
        camera.update();
        renderer.setView(camera);
        debugRenderer.render(world,camera.combined);
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

    public void dispose() {
        manager.dispose();
        jugador.dispose();
        renderer.dispose();
        pantalla.dispose();
    }

    public TiledMap getMap() {
        return map;
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
