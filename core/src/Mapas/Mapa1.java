package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Colisiones;
import actores.EntradaCasaInicial;
import escuchadores.TecladoJugador;
import personajes.Jugador;


public class Mapa1 extends BaseScreen {
    private Juego juego;
    private SpriteBatch batch;
    private EntradaCasaInicial eci;
    public Mapa1(Juego g){
        super(g);
        this.juego=g;
        shapeRenderer=new ShapeRenderer();


         map = new TmxMapLoader().load("Mapas/InteriorCasaInicialFinal.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,unitScale);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        camera.update();
         properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        batch=new SpriteBatch();

        jugador=new Jugador(map,camera,1080,150,mapWidthInPixels/10 ,mapHeightInPixels/5 );
        System.out.println(mapWidthInTiles);//El sout de mapWidthInTiles y Heigh da la altura y anchura del mapa, el de Gdx da el viewportWidth y Heigth
        System.out.println(mapHeightInTiles);
        //MUY IMPORTANTE, DURANTE LA FASE DE ORDENADOR, EL PERSONAJE ESTARÁ EN 280,100,/20,/20, PERO EN MOVIL ESTARÁ EN 1080,150,/10,/5

        //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
        WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        System.out.println(WIDTH);
        System.out.println(HEIGHT);
        camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:

        camera.position.x=WIDTH/2;
        camera.position.y=HEIGHT/2;
        camera.position.set(WIDTH/2,HEIGHT/2,1);




        MapLayers mapLayers = map.getLayers();

        terrainLayer = (TiledMapTileLayer) mapLayers.get("Suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("Cosas");


        colisiones=new Colisiones();
        colisiones.checkCollision(map,jugador);


        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new TecladoJugador(jugador));
        Gdx.input.setInputProcessor(multiplexer);
        pantalla=new Stage();
        pantalla.setDebugAll(true);
        pantalla.addActor(jugador);
        eci=new EntradaCasaInicial();
        pantalla.addActor(eci);


        for(int b=0;b<colisiones.getActores().length-1;b++){
            pantalla.addActor(colisiones.getActores()[b]);
            colisiones.getActores()[b].setColor(Color.BLUE);

        }

        System.out.println(colisiones.getActores().length);
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

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render(delta);
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer);
        renderer.getBatch().end();
        batch.begin();
        jugador.dibujarConHitbox(batch);
        batch.end();
        renderer.getBatch().begin();
        renderer.renderTileLayer(terrainLayer2);
        renderer.getBatch().end();
        eci.dibujar();
        renderer.setView(camera);

        pantalla.act(Gdx.graphics.getDeltaTime());
        pantalla.draw();

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

}
