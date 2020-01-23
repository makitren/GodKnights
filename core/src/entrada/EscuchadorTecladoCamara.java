package entrada;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;

public class EscuchadorTecladoCamara extends EscuchadorTeclado {private char movimientoAutomatico; //vale '-' si no hay movimiento automático. 'd' si se está moviendo automáticamente abajo, 'u' automáticamente arriba, 'l' automáticamente a la izda, 'r' automáticamente a dcha.

    public EscuchadorTecladoCamara(OrthographicCamera oc, TiledMap tm){
        super(oc,tm);
        movimientoAutomatico='-';
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.LEFT:
                camera.position.x = Math.max(camera.position.x - 1, 0); //Se mueve la cámara hasta el mínimo del mapa
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
            case Input.Keys.RIGHT:
                camera.position.x = Math.min(camera.position.x + 1,
                        (int)map.getProperties().get("width"));
                //Se mueve la cámara hasta el máximo del mapa
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
            case Input.Keys.UP:
                camera.position.y = Math.min(camera.position.y + 1,
                        (int)map.getProperties().get("height"));
                //Se mueve la cámara hasta el máximo del mapa
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
            case Input.Keys.DOWN:
                camera.position.y = Math.max(camera.position.y - 1,0);
                //Se mueve la cámara hasta el máximo del mapa
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
            case Input.Keys.CONTROL_RIGHT:
                movimientoAutomatico='d';
                realizarMovimientoAutomático();
                break;
            case Input.Keys.PLUS:
                camera.zoom = Math.max(camera.zoom - 0.2f, 0.1f); //Se hace zoom entre 0.1 y 2
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
            case Input.Keys.MINUS:
                camera.zoom = Math.min(camera.zoom + 0.2f, 1); //Se hace zoom entre 0.1 y 2
                keepCameraInBounds();
                movimientoAutomatico='-';
                break;
        }


        camera.update();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("touchdown",screenX+" , "+screenX+" | "+pointer+" | "+button);
        Vector3 unprojection=camera.unproject(new Vector3(screenX,screenY,0));  //Convertimos los píxeles de la pulsación a tiles
        unprojection.x=(float)Math.floor(unprojection.x); //Hacemos Floor para que nos dé el tile exacto, sin decimales.
        unprojection.y=(float)Math.floor(unprojection.y); //Hacemos Floor para que nos dé el tile exacto, sin decimales.
        camera.position.x=unprojection.x; //Establecemos la cámara a la posic.
        camera.position.y=unprojection.y; //Establecemos la cámara a la posic.
        Gdx.app.log("unprojection",unprojection.x+" , "+unprojection.y+" , "+unprojection.z);
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

    public char getMovimientoAutomatico(){
        return movimientoAutomatico;
    }

    //ESTA FUNCIÓN SE DEBE LLAMAR EN RENDER
    //Siempre empiezo moviendo hacia abajo
    //Establecemos patrón de movimiento: Primero siempre
    //me voy a y=0, y desde ahí voy hacia x=(anchura del mapa) y empiezo a moverme
    //en cuadrados en la totalidad del borde del mapa. Perpetuamente hasta que pulse otra tecla

    public void realizarMovimientoAutomático(){
        //Esto solo funciona si no hemos anulado el movimiento automático
        if(this.movimientoAutomatico!='-'){

        }
    }

}
