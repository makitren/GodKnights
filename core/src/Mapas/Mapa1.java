package Mapas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.alfredomolinacalderon.Juego;

import actores.Colisiones;
import actores.EntradaCasaInicial;
import basededatos.BaseDeDatos;
import escuchadores.TecladoJugador;
import personajes.Jugador;

/**declaracion de la clase Juego
 * @author alfre
 * @version 10/03/20
 */
public class Mapa1 extends BaseScreen {
    private Juego juego;//Variable tipo Juego
    private SpriteBatch batch;//Variable tipo SpriteBatch
    private EntradaCasaInicial eci;//Actor Entrada
    private ImageButton botonArriba,botonAbajo,botonIzquierda,botonDerecha;//Imagenes que actuan como botones para el movimiento del personaje
    private TextureAtlas buttonAtlas;//variable tipo TextureAtlas que carga los botones
    private float posX, posY;//Posicion x e y del personaje
    private char letra;//variable de tipo letra que detecta la letra que usan los botones

    public Mapa1(Juego g, float posicionPersonajeX,float posicionPersonajeY, BaseDeDatos bd){
            super(g);
            baseDeDatos=bd;
            this.juego=g;
            bitmapFont=new BitmapFont(Gdx.files.internal("Mapas/score.ttf"));//Carga el tipo de fuente que se introduce en el path

        /*
        Este apartado carga las puntuaciones de la base de datos
         */
            abajo=baseDeDatos.cargar()[0];
            arriba=baseDeDatos.cargar()[1];
            derecha=baseDeDatos.cargar()[2];
            izquierda=baseDeDatos.cargar()[3];

            //w y h reciben width y height del mapa
            w=Gdx.graphics.getWidth();
            h=Gdx.graphics.getHeight();
            //se le asigna la posicion al personaje
            this.posX=posicionPersonajeX;
            this.posY=posicionPersonajeY;
            //se carga el mapa
             map = new TmxMapLoader().load("Mapas/InteriorCasaInicialFinal.tmx");
             //Se carga el OrthogonalTiledMapRenderer y se pasa el objeto de tipo TiledMap, y la medida unitScale
            renderer = new OrthogonalTiledMapRenderer(map,unitScale);
            //Creacion del sonido que se ejecutará en el mapa
        mapaSonido=Gdx.audio.newMusic(Gdx.files.internal("raw/mapa1.mp3"));
        mapaSonido.setVolume(1);


        //Propiedades del mapa, se le dan las propiedades de cada parte a sus respectivas variables
             properties = map.getProperties();
            tileWidth = properties.get("tilewidth", Integer.class);
            tileHeight = properties.get("tileheight", Integer.class);
            mapWidthInTiles = properties.get("width", Integer.class);
            mapHeightInTiles = properties.get("height", Integer.class);
            //Se cogen las medidas que se van a usar
            mapWidthInPixels = mapWidthInTiles * tileWidth;
            mapHeightInPixels = mapHeightInTiles * tileHeight;
            //w y h permitirán reescalar las colisiones al mapa
            w=w/mapWidthInPixels;
            h=h/mapHeightInPixels;

            //Inicializa la camara con la altura y anchura del mapa en pixels
        camera = new OrthographicCamera(mapWidthInPixels,mapHeightInPixels);
        //Inicializa el batch
            batch=new SpriteBatch();
            //Inicializa las colisiones
        colisiones=new Colisiones();
        //Comprueba si se realiza la colision
        colisiones.checkCollision(map,w,h);
        //Se inicia el jugador y se le inserta el mapa, las colisiones, la camara, las posiciones en las que se encuentra, alto y ancho del jugador
             jugador=new Jugador(map,colisiones,camera,posicionPersonajeX,posicionPersonajeY,mapWidthInPixels/10 ,mapHeightInPixels/5,juego,baseDeDatos );

             System.out.println(mapWidthInTiles);//El sout de mapWidthInTiles y Heigh da la altura y anchura del mapa, el de Gdx da el viewportWidth y Heigth
            System.out.println(mapHeightInTiles);
            //MUY IMPORTANTE, DURANTE LA FASE DE ORDENADOR, EL PERSONAJE ESTARÁ EN 280,100,/20,/20, PERO EN MOVIL ESTARÁ EN 1080,150,/10,/5

        //WIDTH y HEIGHT recogen anchura y altura de las capas del mapa
            WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
            HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
            System.out.println(WIDTH);
            System.out.println(HEIGHT);
            //coloca la camara en una proyeccion ortografica, centrada en WIDTH y HEIGHT
            camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:

        //Se coloca la camara en la siguiente posicion
            camera.position.x=WIDTH/2;
            camera.position.y=HEIGHT/2;
            camera.position.set(WIDTH/2,HEIGHT/2,1);

            //Se inicializa el Stage Pantalla, y se inserta el actor EntradaInicial
            pantalla=new Stage(new ScreenViewport());
            eci=new EntradaCasaInicial();
            pantalla.addActor(eci);

            //Carga los botones que se van a introducir
        buttonAtlas=new TextureAtlas("Mapas/buttons.pack");
        Skin buttonSkin=new Skin();
        buttonSkin.addRegions(buttonAtlas);
        ImageButton.ImageButtonStyle miraArriba=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraAbajo=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraIzquierda=new ImageButton.ImageButtonStyle();
        ImageButton.ImageButtonStyle miraDerecha=new ImageButton.ImageButtonStyle();
        //carga las imagenes
        miraArriba.up=buttonSkin.getDrawable("upRemastered");
        miraAbajo.up=buttonSkin.getDrawable("downRemastered");
        miraDerecha.up=buttonSkin.getDrawable("rightRemastered");
        miraIzquierda.up=buttonSkin.getDrawable("leftRemastered");

        //se genera el boton
        botonArriba=new ImageButton(miraArriba);
        botonAbajo=new ImageButton(miraAbajo);
        botonIzquierda=new ImageButton(miraIzquierda);
        botonDerecha=new ImageButton(miraDerecha);

        /*
        Se introducen las acciones que va a tener cada boton del control
         */
        botonArriba.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                //se realiza el movimiento, despues la animacion, el booleano se pone en false para que pueda andar constantemente
                //se suma +1 al contador de arriba, y guarda el dato en base de datos
                hacerMovimiento('w');
                jugador.hacerAnimaciones('w');
                pulsado=false;
                arriba++;
                baseDeDatos.guardar(arriba,abajo,izquierda,derecha);
                return true;

            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)   {
                //el personaje para mirando en la direccion que se ha pulsaod la tecla
                //el booleano se pone en true para que no detecte más movimiento
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

        //genera la tabla, se coloca en la posicion botton, y se colocan los botones. Se añade la tabla al stage, y se coloca el touchable para que detecte el tacto
        tableBotones=new Table();

        tableBotones.bottom();


        tableBotones.setFillParent(true);
        tableBotones.add(botonArriba).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonAbajo).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonIzquierda).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        tableBotones.add(botonDerecha).height(Gdx.graphics.getHeight() / 6.4f).width(Gdx.graphics.getWidth() / 18.9666f);
        pantalla.addActor(tableBotones);
        tableBotones.setTouchable(Touchable.childrenOnly);

        //detecta las capas del mapa
        MapLayers mapLayers = map.getLayers();
        terrainLayer = (TiledMapTileLayer) mapLayers.get("Suelo");
        terrainLayer2 = (TiledMapTileLayer) mapLayers.get("Cosas");



        //permite detectar el stage para usarlo como controlador
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new TecladoJugador(jugador));
        Gdx.input.setInputProcessor(pantalla);



            //se le añade al stage el jugador
            pantalla.addActor(jugador);

            //añade al mapa los actores que van a colisionar(colisiones)

            for(int b=0;b<colisiones.getActores().length-1;b++){
                pantalla.addActor(colisiones.getActores()[b]);
                System.out.println(colisiones.getActores().length+"HOLA");

            }



        }

        //Cuando aparezca el mapa, el sonido comienza
        @Override
        public void show() {
            mapaSonido.play();

        }
        /*
        -El renderer hace 60fps
        -Se renderiza la primera capa del mapa
        -Se renderiza el jugador
        -Se renderiza la segunda capa del mapa
        -Se dibuja el actor del mapa
        -GlyphLayout carga la fuente que se va a usar, y el dato que quiero que aparezca
        -bitmapfont.draw dibuja con los parametros de batch, los puntos que va a cargar, y al posicion en la que se va a encontrar en el juego
         -Gdx permite hacer que el jugador se mueva fluidamente
         -renderer.setView mete por parametro la camara, y es lo que se va a ver por pantalla
         -se dibuja el stage
         */
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
            eci.dibujarConHitbox();
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
        //Se dispone del jugador, el sonido del mapa, el renderer y el stage. Suena sonidoPuerta.

        public void dispose() {

            jugador.dispose();
            sonidoPuerta.play();
            mapaSonido.dispose();
            renderer.dispose();
            pantalla.dispose();
        }
        //detector de movimiento del jugador
    public void hacerMovimiento(char letra){
        switch (letra){
            case 'w':
                this.letra='w';
                break;
            case 'd':
                this.letra='d';
                break;
            case 's':
                this.letra='s';
                break;
            case 'a':
                this.letra='a';
                break;
        }
    }

        public TiledMap getMap() {
            return map;
        }


}
