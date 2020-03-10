package actores;

import com.badlogic.gdx.Gdx;

public class EntradaCasaInicial extends Actores{
    /**
     * Actor EntradaCasaInicial carga un actor con la siguiente textura, X como anchura e Y como altura,
     * posAlt y posAnc como posicion en la que se encontrará
     */

    public EntradaCasaInicial() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth()*0.13f,Gdx.graphics.getHeight()*0.1f,0,935);
        //posAlt es 0 en movil, posAnc 935. En ordenador:
        this.nombre="EntradaCasaInicial";
    }
}
