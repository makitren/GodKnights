package escuchadores;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import personajes.Jugador;

public class TecladoJugador implements InputProcessor {
    private Jugador jugador;

    public TecladoJugador(Jugador j){
        super();
        this.jugador=j;
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.UP:
                jugador.mover('u');
                break;
            case Input.Keys.DOWN:
                jugador.mover('d');
                break;
            case Input.Keys.LEFT:
                jugador.mover('l');
                break;
            case Input.Keys.RIGHT:
                jugador.mover('r');
                break;
            case Input.Keys.MINUS:
                if(jugador.getCamara().zoom<1) {
                    jugador.getCamara().zoom+=0.1;
                    jugador.getCamara().update();
                }
                break;
            case Input.Keys.PLUS:
                if(jugador.getCamara().zoom>0.5) {
                    jugador.getCamara().zoom -= 0.1;
                    jugador.getCamara().update();
                }
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
