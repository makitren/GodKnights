package actores;

import com.badlogic.gdx.Gdx;

public class EntradaCasaInicial extends Actores{

    public EntradaCasaInicial() {
        super("Mapas/imagenTransparente.png", Gdx.graphics.getWidth()*0.13f,Gdx.graphics.getHeight()*0.1f,0,935);
        //posAlt es 0 en movil, posAnc 935. En ordenador:
        this.nombre="EntradaCasaInicial";
    }
}
