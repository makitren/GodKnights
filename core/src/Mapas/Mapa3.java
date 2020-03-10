package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Colisiones;
import actores.EntradaMapa3;
import actores.SalidaMapa3;
import basededatos.BaseDeDatos;
import escuchadores.TecladoJugador;
import personajes.Jugador;

public class Mapa3 extends BaseScreen {

    private Juego juego;
    private SpriteBatch batch;
    private EntradaMapa3 em;
    private SalidaMapa3 sm;
    private ImageButton botonArriba,botonAbajo,botonIzquierda,botonDerecha;
    private TextureAtlas buttonAtlas;
    private float posX,posY;
    private char letra;

        public Mapa3(Juego g, float posicionPersonajeX, float posicionPersonajeY, BaseDeDatos bd){
            super(g);
            this.juego=g;
            baseDeDatos=bd;
            bitmapFont=new BitmapFont(Gdx.files.internal("Mapas/score.ttf"));
            abajo=baseDeDatos.cargar()[0];
            arriba=baseDeDatos.cargar()[1];
            derecha=baseDeDatos.cargar()[2];
            izquierda=baseDeDatos.cargar()[3];
            w=Gdx.graphics.getWidth();
            h=Gdx.graphics.getHeight();
            shapeRenderer=new ShapeRenderer();
            this.posX=posicionPersonajeX;
            this.posY=posicionPersonajeY;
            map = new TmxMapLoader().load("Mapas/CiudadBosqueFinal.tmx");
            renderer = new OrthogonalTiledMapRenderer(map,unitScale);


            properties = map.getProperties();
            tileWidth = properties.get("tilewidth", Integer.class);
            tileHeight = properties.get("tileheight", Integer.class);
            mapWidthInTiles = properties.get("width", Integer.class);
            mapHeightInTiles = properties.get("height", Integer.class);
            mapWidthInPixels = mapWidthInTiles * tileWidth;
            mapHeightInPixels = mapHeightInTiles * tileHeight;
            w=w/mapWidthInPixels;
            h=h/mapHeightInPixels;
            mapaSonido=Gdx.audio.newMusic(Gdx.files.internal("raw/mapa3.mp3"));
            mapaSonido.setVolume(1);
            camera = new OrthographicCamera(mapWidthInPixels,mapHeightInPixels);
            batch=new SpriteBatch();
            colisiones=new Colisiones();
            colisiones.checkCollision(map,w,h);
            //Crear variable para posicionPersonajeX e Y, para que segun desde que mapa entre, se origine el jugador en un lado u otro.
            jugador=new Jugador(map,colisiones,camera,posicionPersonajeX,posicionPersonajeY,mapWidthInPixels/20 ,mapHeightInPixels/20,juego,baseDeDatos );
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
            em=new EntradaMapa3();
            sm=new SalidaMapa3();
            pantalla.addActor(em);
            pantalla.addActor(sm);

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

                    hacerMovimiento('w');
                    jugador.hacerAnimaciones('w');
                    pulsado=false;
                    arriba++;
                    baseDeDatos.guardar(arriba,abajo,izquierda,derecha);
                    return true;

                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)   {
                    jugador.pararPersonaje('w');
                    pulsado=true;
                }
            });
            botonAbajo.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    hacerMovimiento('s');
                    jugador.hacerAnimaciones('s');
                    pulsado=false;
                    abajo++;
                    baseDeDatos.guardar(arriba,abajo,izquierda,derecha);
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)   {
                    jugador.pararPersonaje('s');
                    pulsado=true;
                }
            });
            botonDerecha.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    hacerMovimiento('d');
                    jugador.hacerAnimaciones('d');
                    System.out.println(jugador.getX());
                    pulsado=false;
                    derecha++;
                    baseDeDatos.guardar(arriba,abajo,izquierda,derecha);
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)   {
                    jugador.pararPersonaje('d');
                    pulsado=true;
                }
            });
            botonIzquierda.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                    hacerMovimiento('a');
                    jugador.hacerAnimaciones('a');
                    System.out.println(jugador.getX());
                    pulsado=false;
                    izquierda++;
                    baseDeDatos.guardar(arriba,abajo,izquierda,derecha);

                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button)   {
                    jugador.pararPersonaje('a');
                    pulsado=true;
                }
            });

            tableBotones=new Table();
            tableBotones.bottom();

            tableBotones.setFillParent(true);
            tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
            pantalla.addActor(tableBotones);


            InputMultiplexer multiplexer = new InputMultiplexer();
            multiplexer.addProcessor(new TecladoJugador(jugador));
            //Gdx.input.setInputProcessor(multiplexer);
            Gdx.input.setInputProcessor(pantalla);


            pantalla.addActor(jugador);




            for(int b=0;b<colisiones.getActores().length-1;b++){
                pantalla.addActor(colisiones.getActores()[b]);
                colisiones.getActores()[b].setColor(Color.BLUE);

            }
            System.out.println(colisiones.getActores().length);
            mapaSonido.play();
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
        em.dibujarConHitbox();
        sm.dibujarConHitbox();
        batch.begin();
        GlyphLayout puntosLayoutAba=new GlyphLayout(bitmapFont, "Abajo:"+abajo);
        GlyphLayout puntosLayoutIzq=new GlyphLayout(bitmapFont, "Izq:"+izquierda);
        GlyphLayout puntosLayoutDec=new GlyphLayout(bitmapFont, "Derecha:"+derecha);
        GlyphLayout puntosLayoutArr=new GlyphLayout(bitmapFont, "Arriba:"+arriba);
        bitmapFont.draw(batch,puntosLayoutAba,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/13f);
        bitmapFont.draw(batch,puntosLayoutArr,Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/13f);
        bitmapFont.draw(batch,puntosLayoutIzq,Gdx.graphics.getWidth()/1.65f,Gdx.graphics.getHeight()/13f);
        bitmapFont.draw(batch,puntosLayoutDec,Gdx.graphics.getWidth()/1.4f,Gdx.graphics.getHeight()/13f);
        bitmapFont.setColor(Color.BLACK);
        batch.end();
        if(Gdx.input.isButtonPressed(0)&&!pulsado){
            jugador.moverJugador(letra);
        }


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
        sonidoPuerta.play();
        mapaSonido.dispose();
        renderer.dispose();
        pantalla.dispose();
        }
    public void hacerMovimiento(char letra) {
        switch (letra) {
            case 'w':
                this.letra = 'w';
                break;
            case 'd':
                this.letra = 'd';
                break;
            case 's':
                this.letra = 's';
                break;
            case 'a':
                this.letra = 'a';
                break;
        }
    }

        public TiledMap getMap() {
            return map;
        }
}