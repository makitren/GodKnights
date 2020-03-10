package actores;

import com.badlogic.gdx.Gdx;

public class SalidaMapa4 extends Actores {
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public SalidaMapa4() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth() * 0.07f, Gdx.graphics.getHeight() * 0.17f, 450, 2000);
        this.nombre = "SalidaMapa4";
    }
}
