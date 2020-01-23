package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Main extends ApplicationAdapter {
	private OrthogonalTiledMapRenderer renderer; //Clase auxiliar para renderizar un mapa.
	private OrthographicCamera camera; //Cámara a través de la que veremos el mundo.
	private static int WIDTH; //Aquí almacenaremos la anchura en tiles
	private static int HEIGHT; //Aquí almacenaremos la altura en tiles
	public static final float unitScale = 1 / 16f; //Nos servirá para establecer que la pantalla se divide en tiles de 16 pixeles;


	@Override
	public void create() {
		//CREACIóN DEL MUNDO
		float w = Gdx.graphics.getWidth(); //Obtenemos la anchura de nuestra pantalla en pixels
		float h = Gdx.graphics.getHeight(); //Obtenemos la atura de nuestra pantalla en pixels
		TiledMap map = new TmxMapLoader().load("Mapas/InteriorCasaInicialFinal.tmx"); //Cargamos el tilemap desde assets
		renderer = new OrthogonalTiledMapRenderer(map, unitScale); //Establecemos el renderizado del mapa dividido en Tiles de 32 dp.
		camera = new OrthographicCamera(); //Declaramos la cámara a través de la que veremos el mundo
		camera.zoom = 2.2f; //Establecemos el zoom de la cámara. 0.1 es más cercano que 1.
		WIDTH = ((TiledMapTileLayer) map.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
		HEIGHT = ((TiledMapTileLayer) map.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa

		Gdx.app.log("Width", Float.toString(WIDTH)); //Sacamos por el log el número de tiles de ancho
		Gdx.app.log("Height", Float.toString(HEIGHT)); //Sacamos por el log el número de tiles de alto

		camera.setToOrtho(false, WIDTH, HEIGHT); //Establecemos la cámara, y le decimos cuanto tiene que ocupar. Por defecto enfoca al 0,0.
		camera.position.x = 32;  //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
		camera.position.y = 23; //Establecemos la posición x de la cámara en función del número de tiles de la anchura. Jugaremos con esto en clase
		Gdx.app.log("camera x", Float.toString(camera.position.x));
		Gdx.app.log("camera x", Float.toString(camera.position.y));
		camera.update(); //Colocamos la Cámara.

	}
		public void render () {
			//Color de limpieza, evita que cuando se desplaza la cámara
			//se queden dibujos antiguos que ya no deberían estar alli.
			Gdx.gl.glClearColor(0, 0, 0, 1);
			//limpia el fondo de pantalla con el color indicado
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//indica los elementos que se meten en el batch

			camera.update();
			renderer.render(); //Renderizamos la vista


			renderer.setView(camera); //Establecemos la vista del mundo a través de la cámara.
		}

		public void dispose (){
			renderer.dispose(); //Destruimos el objeto que renderiza un mapa, para no tener filtraciones de memoria
		}
	}

