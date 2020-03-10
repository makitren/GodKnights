package actores;

import com.badlogic.gdx.Gdx;

public class Tienda6 extends Actores {
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public Tienda6(float x, float y) {
        super("Doraemon.png", Gdx.graphics.getWidth() * 100, Gdx.graphics.getHeight() * 100, 250, 135);
        this.nombre = "Tienda6";
    }
}
