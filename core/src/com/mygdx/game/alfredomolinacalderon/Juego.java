package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.compression.lzma.Base;

import Mapas.BaseScreen;
import Mapas.Mapa1;
import basededatos.BaseDeDatos;

/**declaracion de la clase Juego
 * @author alfre
 * @version 10/03/20
 */

public class Juego extends Game {
    private BaseScreen pantallaActual;
    private BaseDeDatos baseDeDatos;
    private Music mapaSonido;


	/**
	 * @param bd define un objeto de tipo BaseDeDatos, para iniciar el juego es necesario pasarle un objeto BaseDeDatos
	 */
	public Juego(BaseDeDatos bd){
		baseDeDatos=bd;
	}


	/**
	 * Creacion metodo create() se ejecutará para comenzar el juego.
	 * Ejecuta el metodo setPantallaActual
	 * Creacion render() es el metodo que se encarga de que la pantalla que se está viendo se ejecute.
	 * Creacion dispose() es el metodo que se encarga de que la pantalla se deje de usar
	 */
	@Override
	public void create() {
			setPantallaActual(new Mapa1(this,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f,baseDeDatos));
		System.out.println("Estou haciendo esto");
	}

	/**
	 * @param pa parametro de tipo BaseScreen que se encarga de que la pantalla que se le pase sea la ejecutada.
	 */

	public void setPantallaActual(BaseScreen pa){
		pantallaActual=pa;
		setScreen(pantallaActual);
	}



	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		pantallaActual.render(Gdx.graphics.getDeltaTime());
	}

		public void dispose (){
		pantallaActual.dispose();
	}

	@Override
	public void pause() {
		//super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}

