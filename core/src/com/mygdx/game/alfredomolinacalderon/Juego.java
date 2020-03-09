package com.mygdx.game.alfredomolinacalderon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.compression.lzma.Base;

import Mapas.BaseScreen;
import Mapas.Mapa1;
import Mapas.Mapa2;
import Mapas.Mapa3;
import Mapas.Mapa4;
import basededatos.BaseDeDatos;

public class Juego extends Game {
    private BaseScreen pantallaActual;
    private BaseDeDatos baseDeDatos;
    private Music mapaSonido;
	public Juego(BaseDeDatos bd){
		baseDeDatos=bd;
	}
	@Override
	public void create() {
			this.setPantallaActual(new Mapa1(this,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f,baseDeDatos));

	}


	public void setPantallaActual(BaseScreen pa){
		pantallaActual=pa;
		setScreen(pantallaActual);
	}
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		pantallaActual.render(Gdx.graphics.getDeltaTime());
	}
/*
	public void pararMusica(Music sonido){
		this.mapaSonido=sonido;
		mapaSonido=Gdx.audio.newMusic(Gdx.files.internal("raw/mapa1.mp3"));
		pantallaActual.setMapaSonido(mapaSonido);
		pantallaActual.getMapaSonido().stop();
		pantallaActual.getMapaSonido().dispose();
	}

 */
		public void dispose (){
		pantallaActual.dispose();
	}
	}

