package actores;

import com.badlogic.gdx.Gdx;

public class EntradaCasaMapa2 extends Actores{
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public EntradaCasaMapa2() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth()*0.07f,Gdx.graphics.getHeight()*0.07f,550,430);
        this.nombre="EntradaCasaMapa2";
    }
}
