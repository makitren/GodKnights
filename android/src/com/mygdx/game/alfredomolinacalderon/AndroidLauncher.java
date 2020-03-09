package com.mygdx.game.alfredomolinacalderon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.alfredomolinacalderon.Juego;

import Mapas.BaseScreen;
import basedatos.BaseDeDatosAndroid;
import basededatos.BaseDeDatos;

public class AndroidLauncher extends AndroidApplication {
	private Music musica;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Juego(new BaseDeDatosAndroid(this)), config);



	}
	@Override
	public void onBackPressed() {
		AlertDialog.Builder alert=new AlertDialog.Builder(this);
		alert.setMessage("¿Quieres salir de Godknights?");

		alert.setCancelable(false);
		alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();

			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.show();
	}

}
