package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import escuchadores.TecladoJugador;
import personajes.Jugador;

public class Main extends Game {
	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	private Jugador jugador;
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer;
	private TiledMap map;
	private World world;
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles
	public static final float pixelsPorCuadro = 1 / 32f; //Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles;
//Setter a unitScale para que pueda ajustar la camara según el mapa, también con el personaje

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla
		batch=new SpriteBatch();
		world=new World(new Vector2(0,-9.8f),true);
		 map = new TmxMapLoader().load("Mapas/InteriorCasaInicialFinal.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, pixelsPorCuadro); //Establecemos el renderizado del mapa dividido en Tiles de 16 dp.
		camera = new OrthographicCamera(); //Declaramos la cámara a través de la que veremos el mundo
		//camera.zoom=0.1f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:
		camera.position.x=WIDTH/2;
		camera.position.y=HEIGHT/2;
		camera.zoom=2f;

		jugador=new Jugador(camera,map,world);
		this.debugRenderer=new Box2DDebugRenderer();

		camera.update();

		for (MapObject objeto:map.getLayers().get("colisionable").getObjects()){
			BodyDef propiedadesRectangulo= new BodyDef(); //Establecemos las propiedades del cuerpo
			propiedadesRectangulo.type = BodyDef.BodyType.StaticBody;
			Body rectanguloSuelo = world.createBody(propiedadesRectangulo);
			FixtureDef propiedadesFisicasRectangulo=new FixtureDef();
			Shape formaRectanguloSuelo=getRectangle((RectangleMapObject)objeto);
			propiedadesFisicasRectangulo.shape = formaRectanguloSuelo;
			propiedadesFisicasRectangulo.density = 1f;
			rectanguloSuelo.createFixture(propiedadesFisicasRectangulo);
		}

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new TecladoJugador(jugador));
		Gdx.input.setInputProcessor(multiplexer);

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				Gdx.app.log("Contacto comenzado!",contact.getFixtureA()+" : "+contact.getFixtureB());
				Gdx.app.log("Es figura A pollo?",(contact.getFixtureA().getBody()==jugador.getCuerpo())+"");
				Gdx.app.log("Es figura B pollo?",(contact.getFixtureB().getBody()==jugador.getCuerpo())+"");

			}

			@Override
			public void endContact(Contact contact) {

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {

			}
		});


	}
		public void render () {
			//Color de limpieza, evita que cuando se desplaza la cámara
			//se queden dibujos antiguos que ya no deberían estar alli.
			Gdx.gl.glClearColor(0, 0, 0, 1);
			//limpia el fondo de pantalla con el color indicado
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			world.step(Gdx.graphics.getDeltaTime(),6,2);
			//indica los elementos que se meten en el batch
			renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
			//camera.update();
			renderer.render(); //Renderizamos la vista
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			jugador.dibujar(batch,0);
			batch.end();
			camera.update();
			debugRenderer.render(world,camera.combined);
		}

		public void dispose ()
		{
			world.dispose();
			this.debugRenderer.dispose();
			this.batch.dispose();
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

