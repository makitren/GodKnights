package actores;

import com.badlogic.gdx.Gdx;

public class SalidaMapa3 extends Actores {
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public SalidaMapa3() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth() * 0.04f, Gdx.graphics.getHeight() * 0.04f, 500, 1150);
        this.nombre = "SalidaMapa3";
    }
}
