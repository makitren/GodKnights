package escuchadores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import personajes.Jugador;

/**declaracion de la clase TecladoJugador
 * @author alfre
 * @version 10/03/20
 */
public class TecladoJugador implements InputProcessor {
    private Jugador jugador;

    /**
     * @param j parametro de tipo jugador por el que
     */
    public TecladoJugador(Jugador j){
        super();
        this.jugador=j;
    }

    /**
     *
     * @param keycode recibe un parametro de tipo int que escuchará el teclado
     * @return false
     * keyTyped detecta si se mantiene pulsado una tecla o una imagen
     * keyDown detecta si se ha pulsado
     * keyUp detecta si se levanta el dedo o la imagen
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
                jugador.hacerAnimaciones('d');
                break;
            case Input.Keys.S:
                jugador.hacerAnimaciones('s');
                break;
            case Input.Keys.A:
                jugador.hacerAnimaciones('a');
                break;
            case Input.Keys.W:
                jugador.hacerAnimaciones('w');
                break;

           /*
            case Input.Keys.SPACE:
                jugador.atacar();
                break;
            */
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.D:
                jugador.pararPersonaje('d');
                break;
            case Input.Keys.S:
                jugador.pararPersonaje('s');
                break;
            case Input.Keys.A:
                jugador.pararPersonaje('a');
                break;
            case Input.Keys.W:
                jugador.pararPersonaje('w');
                break;
            case 'f':
                jugador.moverJugador('f');
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
            String letra=String.valueOf(character);
            // System.out.println(keycode);
            switch (letra.toLowerCase()){
                case "w":
                    jugador.moverJugador('w');
                    break;
                case "s":
                    jugador.moverJugador('s');
                    break;
                case "a":
                    jugador.moverJugador('a');
                    break;
                case "d":
                    jugador.moverJugador('d');
                    break;

            }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX+" y:"+screenY);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
