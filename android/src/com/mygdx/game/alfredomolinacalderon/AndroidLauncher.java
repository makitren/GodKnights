package com.mygdx.game.alfredomolinacalderon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.alfredomolinacalderon.Juego;

import basedatos.BaseDeDatosAndroid;
import basededatos.BaseDeDatos;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Juego(new BaseDeDatosAndroid(this)), config);


	}

	@Override
	protected void onStop() {
		super.onStop();

		BaseDeDatos bd=new BaseDeDatosAndroid(this);
		Juego juego=new Juego(bd);
		//juego.pararMusica();
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
