package actores;

import com.badlogic.gdx.Gdx;

public class EntradaMapa3 extends Actores{
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */
    public EntradaMapa3() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth()*0.04f,Gdx.graphics.getHeight()*0.04f,-10,265);
        this.nombre="EntradaMapa3";
    }
}
