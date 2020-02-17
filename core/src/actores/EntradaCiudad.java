package actores;

import com.badlogic.gdx.Gdx;

public class EntradaCiudad extends Actores{

    public EntradaCiudad(float x, float y) {
        super("Probando.png", Gdx.graphics.getWidth()*100,Gdx.graphics.getHeight()*100,0,65);
        this.nombre="Entrada Ciudad";
    }
}
