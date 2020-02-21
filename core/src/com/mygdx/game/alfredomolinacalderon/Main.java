package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import actores.Actores;
import actores.Colisiones;
import actores.EntradaCiudad;
import actores.SalidaCiudad;
import actores.Tienda1;
import actores.Tienda2;
import actores.Tienda3;
import actores.Tienda4;
import actores.Tienda5;
import actores.Tienda6;
import escuchadores.TecladoJugador;
import personajes.Jugador;

public class Main extends Game {
    private World world;
    private Actores stage;
    private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	private Jugador jugador;
	protected Stage pantalla;
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles
	public static final float unitScale = 1 / 32f; //Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles;
    private static final float pixelsPorCuadro=32f;
    private Box2DDebugRenderer debugRenderer;
    private AssetManager manager;
    private TiledMapTileLayer terrainLayer;
	private TiledMapTileLayer terrainLayer2;

    private Tienda1 t;
    private Tienda2 t2;
    private Tienda3 t3;
    private Tienda4 t4;
    private Tienda5 t5;
    private Tienda6 t6;
    private EntradaCiudad ec;
    private SalidaCiudad sc;
    private TiledMap map;
    private Colisiones colisiones;

	@Override
	public void create() {
		manager=new AssetManager();
		manager.setLoader(TiledMap.class,new TmxMapLoader());
		manager.load("Mapas/CiudadBosqueFinal.tmx", TiledMap.class);
		manager.finishLoading();
        this.debugRenderer=new Box2DDebugRenderer();
        world=new World(new Vector2(0,-9.8f),true);
		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla
		map = manager.get("Mapas/CiudadBosqueFinal.tmx",TiledMap.class); //Cargamos el tilemap desde assets

        colisiones=new Colisiones();
        colisiones.checkCollision(map,jugador);
		camera = new OrthographicCamera(640.f,480.f); //Declaramos la cámara a través de la que veremos el mundo
		//camera.zoom=0.1f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:
		camera.position.x=WIDTH/2;
		camera.position.y=HEIGHT/2;
		camera.zoom=1f;
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 16 dp.
		pantalla=new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

		pantalla.addActor(t=new Tienda1(10,10));
		pantalla.addActor(t2=new Tienda2(10,10));
        pantalla.addActor(t3=new Tienda3(10,10));
        pantalla.addActor(t4=new Tienda4(10,10));
        pantalla.addActor(t5=new Tienda5(10,10));
        pantalla.addActor(t6=new Tienda6(10,10));
        pantalla.addActor(ec=new EntradaCiudad(10,10));
        pantalla.addActor(sc=new SalidaCiudad(10,10));
		jugador=new Jugador(map,world);
        pantalla.addActor(jugador);
		camera.update();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new TecladoJugador(jugador));
		Gdx.input.setInputProcessor(multiplexer);
        for(int b=0;b<colisiones.getActores().length-1;b++){
            pantalla.addActor(colisiones.getActores()[b]);
        }
        /*
		for (MapObject objeto:map.getLayers().get("Colisionables").getObjects()){
            BodyDef propiedadesRectangulo= new BodyDef(); //Establecemos las propiedades del cuerpo
            propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
            Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
            FixtureDef propiedadesFisicasRectangulo=new FixtureDef();
            Shape formaRectanguloSuelo=getRectangle((RectangleMapObject)objeto);
            propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
            propiedadesFisicasRectangulo.density = 1f;
            rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);
        }
        for (MapObject objeto:map.getLayers().get("EntradaSalida").getObjects()){
            BodyDef propiedadesRectangulo= new BodyDef(); //Establecemos las propiedades del cuerpo
            propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
            Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
            FixtureDef propiedadesFisicasRectangulo=new FixtureDef();
            Shape formaRectanguloSuelo=getRectangle((RectangleMapObject)objeto);
            propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
            propiedadesFisicasRectangulo.density = 1f;
            rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);
        }

         */

		MapLayers mapLayers=map.getLayers();
		terrainLayer=(TiledMapTileLayer)mapLayers.get("suelo");
		terrainLayer2=(TiledMapTileLayer)mapLayers.get("objetos");

		pantalla.setDebugAll(true);
	}
		public void render () {
			//Color de limpieza, evita que cuando se desplaza la cámara
			//se queden dibujos antiguos que ya no deberían estar alli.
			Gdx.gl.glClearColor(0, 0, 0, 1);
			//limpia el fondo de pantalla con el color indicado
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//indica los elementos que se meten en el batch
			renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
			//camera.update();
			//renderer.render(); //Renderizamos la vista

			renderer.getBatch().begin();
			renderer.renderTileLayer(terrainLayer);
			renderer.getBatch().end();

			jugador.dibujar();

			renderer.getBatch().begin();
			renderer.renderTileLayer(terrainLayer2);
			renderer.getBatch().end();
            t.dibujar();
            t2.dibujar();
            t3.dibujar();
            t4.dibujar();
            t5.dibujar();
            t6.dibujar();
            ec.dibujar();
            sc.dibujar();
			pantalla.act(Gdx.graphics.getDeltaTime());


			pantalla.setDebugAll(true);

			camera.update();

			debugRenderer.render(world,camera.combined);

		}

		public void dispose (){
			jugador.dispose();
			renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
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

