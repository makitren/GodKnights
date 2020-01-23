package entrada;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;

public abstract class EscuchadorTeclado implements InputProcessor {
    protected OrthographicCamera camera;
    protected TiledMap map;

    public EscuchadorTeclado(OrthographicCamera c,TiledMap m){
        camera=c;
        map=m;
    }

    /**
     * Función que corrige la posición de cámara si al
     * moverla junto al personaje o por separado,
     * se tiene que corregir la posición para que no se
     * salga de los límites y no se vea la zona de limpieza (EL ROJO).
     * @return boolean que indica si se ha corregido la posición o no
     */
    protected boolean keepCameraInBounds(){
        if(camera.position.x-(camera.viewportWidth/2)
                *camera.zoom<0){
            camera.position.x=
                    (camera.viewportWidth/2)*camera.zoom;
            return true;
        }
        if(camera.position.y-(camera.viewportHeight/2)
                *camera.zoom<0){
            camera.position.y=(
                    camera.viewportHeight/2)*camera.zoom;
            return true;
        }
        if(camera.position.x+(camera.viewportWidth/2)*
                camera.zoom>(int)map.getProperties().get("width")){
            camera.position.x=(int)map.getProperties().get("width")-
                    (camera.viewportWidth/2)
                            *camera.zoom;
            return true;
        }if(camera.position.y+
                (camera.viewportHeight/2)*camera.zoom>(int)map.getProperties().get("height"))
        {camera.position.y=(int)map.getProperties().get("height")-
                (camera.viewportHeight/2)*camera.zoom;
            return true;
        }
        return false;
    }
}

