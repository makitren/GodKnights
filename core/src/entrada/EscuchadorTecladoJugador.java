package entrada;

import com.badlogic.gdx.Input;

import personajes.Jugador;

public class EscuchadorTecladoJugador extends EscuchadorTeclado {
    private Jugador jugador; //El jugador que se va a mover con este escuchador

    public EscuchadorTecladoJugador(Jugador j){
        super(j.getCamara(),j.getMapa());
        jugador=j;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.W:
                jugador.moverCamaraTile('u');
                jugador.moverJugadorTile('u',keepCameraInBounds());
                break;
            case Input.Keys.S:
                jugador.moverCamaraTile('d');
                jugador.moverJugadorTile('d',keepCameraInBounds());

                break;
            case Input.Keys.A:
                jugador.moverCamaraTile('l');
                jugador.moverJugadorTile('l',keepCameraInBounds());

                break;
            case Input.Keys.D:
                jugador.moverCamaraTile('r');
                jugador.moverJugadorTile('r',keepCameraInBounds());

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

