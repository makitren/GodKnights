package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;

import actores.Tienda1;
import actores.Tienda2;
import actores.Tienda3;
import actores.Tienda4;
import actores.Tienda5;
import actores.Tienda6;
import escuchadores.TecladoJugador;
import personajes.Jugador;

public class Main extends Game {
	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	private Jugador jugador;
	protected Stage pantalla;
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles
	public static final float unitScale = 1 / 32f; //Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles;
    private Tienda1 t;
    private Tienda2 t2;
    private Tienda3 t3;
    private Tienda4 t4;
    private Tienda5 t5;
    private Tienda6 t6;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla
		TiledMap map = new TmxMapLoader().load("Mapas/CiudadBosqueFinal.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 16 dp.
		camera = new OrthographicCamera(); //Declaramos la cámara a través de la que veremos el mundo
		//camera.zoom=0.1f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
		camera.setToOrtho(false, WIDTH,HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Doc:
		camera.position.x=WIDTH/2;
		camera.position.y=HEIGHT/2;
		camera.zoom=1f;
		pantalla=new Stage(new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
		pantalla.addActor(t=new Tienda1(10,10));
		pantalla.addActor(t2=new Tienda2(10,10));
        pantalla.addActor(t3=new Tienda3(10,10));
        pantalla.addActor(t4=new Tienda4(10,10));
        pantalla.addActor(t5=new Tienda5(10,10));
        pantalla.addActor(t6=new Tienda6(10,10));
		jugador=new Jugador(camera,map);
		camera.update();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new TecladoJugador(jugador));
		Gdx.input.setInputProcessor(multiplexer);

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
			renderer.render(); //Renderizamos la vista
			jugador.dibujar();
			t.dibujar();
			t2.dibujar();
			t3.dibujar();
			t4.dibujar();
			t5.dibujar();
			t6.dibujar();
			camera.update();

		}

		public void dispose (){
			jugador.dispose();
			renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
	}
	}

