package actores;

import com.badlogic.gdx.Gdx;

public class EntradaMapa4 extends Actores {
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public EntradaMapa4() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth() * 0.07f, Gdx.graphics.getHeight() * 0.07f, Gdx.graphics.getHeight()/1.35f, 0);
        this.nombre = "EntradaMapa4";
    }
}
