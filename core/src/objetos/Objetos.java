package objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Objetos extends Actor {
    protected Sprite sprite;

    public Objetos(String rutaTextura,float x, float y){
        sprite=new Sprite(new Texture(rutaTextura));
        sprite.setBounds(x,y, Gdx.graphics.getWidth()*10,Gdx.graphics.getHeight()*10);
        this.setOrigin(this.sprite.getWidth()/2,this.sprite.getHeight()/2);
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
    }
    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }
}
