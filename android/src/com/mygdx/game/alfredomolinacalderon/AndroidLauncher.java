package com.mygdx.game.alfredomolinacalderon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.alfredomolinacalderon.Juego;

import Mapas.BaseScreen;
import basedatos.BaseDeDatosAndroid;
import basededatos.BaseDeDatos;

public class AndroidLauncher extends AndroidApplication {
	private Music musica;
	private Juego j;
	private boolean funcional=false;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		System.out.println("mecagondios");
		j=new Juego(new BaseDeDatosAndroid(this));
		if (!funcional) {
			funcional=true;
			initialize(j, config);
		}
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

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		if (!funcional) {
		    Toast.makeText(this, "nfjdsngjns", Toast.LENGTH_LONG).show();
		}
	}
}
