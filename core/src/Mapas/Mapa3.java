package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Colisiones;
import actores.EntradaMapa3;
import actores.SalidaMapa3;
import escuchadores.TecladoJugador;
import personajes.Jugador;

public class Mapa3 extends BaseScreen {

    private Juego juego;
    private SpriteBatch batch;
    private EntradaMapa3 em;
    private SalidaMapa3 sm;
    private ImageButton botonArriba,botonAbajo,botonIzquierda,botonDerecha;
    private TextureAtlas buttonAtlas;

        public Mapa3(Juego g){
            super(g);
            this.juego=g;
            shapeRenderer=new ShapeRenderer();

            map = new TmxMapLoader().load("Mapas/CiudadBosqueFinal.tmx");
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
            //Crear variable para posicionPersonajeX e Y, para que segun desde que mapa entre, se origine el jugador en un lado u otro.
            jugador=new Jugador(map,camera,280,100,mapWidthInPixels/20 ,mapHeightInPixels/20 );
            System.out.println(mapWidthInTiles);//El sout de mapWidthInTiles y Heigh da la altura y anchura del mapa, el de Gdx da el viewportWidth y Heigth
            System.out.println(mapHeightInTiles);
            //MUY IMPORTANTE, DURANTE LA FASE DE ORDENADOR, EL PERSONAJE ESTARÁ EN 280,100,/20,/20, PERO EN MOVIL ESTARÁ EN 1080,150,/10,/5

            WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
            HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
            System.out.println(WIDTH);
            System.out.println(HEIGHT);
            camera.setToOrtho(false, WIDTH,HEIGHT);

            camera.position.x=WIDTH/2;
            camera.position.y=HEIGHT/2;
            camera.position.set(WIDTH/2,HEIGHT/2,1);

            MapLayers mapLayers = map.getLayers();
            terrainLayer = (TiledMapTileLayer) mapLayers.get("suelo");
            terrainLayer2 = (TiledMapTileLayer) mapLayers.get("objetos");


            pantalla=new Stage(new ScreenViewport());

            buttonAtlas=new TextureAtlas("Mapas/buttons.pack");
            Skin buttonSkin=new Skin();
            buttonSkin.addRegions(buttonAtlas);
            ImageButton.ImageButtonStyle miraArriba=new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle miraAbajo=new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle miraIzquierda=new ImageButton.ImageButtonStyle();
            ImageButton.ImageButtonStyle miraDerecha=new ImageButton.ImageButtonStyle();
            miraArriba.up=buttonSkin.getDrawable("upRemastered");
            miraAbajo.up=buttonSkin.getDrawable("downRemastered");
            miraDerecha.up=buttonSkin.getDrawable("rightRemastered");
            miraIzquierda.up=buttonSkin.getDrawable("leftRemastered");


            botonArriba=new ImageButton(miraArriba);
            botonAbajo=new ImageButton(miraAbajo);
            botonIzquierda=new ImageButton(miraIzquierda);
            botonDerecha=new ImageButton(miraDerecha);

            botonArriba.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    jugador.moverJugador('w');
                    jugador.hacerAnimaciones('w');

                    return true;
                }
            });
            botonAbajo.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    jugador.moverJugador('s');
                    jugador.hacerAnimaciones('s');

                    return true;
                }
            });
            botonDerecha.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    jugador.moverJugador('d');
                    jugador.hacerAnimaciones('d');

                    return true;
                }
            });
            botonIzquierda.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    jugador.moverJugador('a');
                    jugador.hacerAnimaciones('a');

                    return true;
                }
            });

            tableBotones=new Table();
            tableBotones.bottom();
            tableBotones.debug();
            tableBotones.setFillParent(true);
            tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            pantalla.addActor(tableBotones);





            colisiones=new Colisiones();
            colisiones.checkCollision(map,jugador);

            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(new TecladoJugador(jugador));
            Gdx.input.setInputProcessor(multiplexer);
            Gdx.input.setInputProcessor(pantalla);


            pantalla.setDebugAll(true);
            pantalla.addActor(jugador);


            em=new EntradaMapa3();
            sm=new SalidaMapa3();
            pantalla.addActor(em);
            pantalla.addActor(sm);

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
        em.dibujar();
        sm.dibujar();

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

        jugador.dispose();
        renderer.dispose();
        pantalla.dispose();
        }

        public TiledMap getMap() {
            return map;
        }
}