package personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.alfredomolinacalderon.Juego;

import Mapas.Mapa1;
import Mapas.Mapa2;
import Mapas.Mapa3;
import Mapas.Mapa4;
import actores.Colisiones;

public class Jugador extends Actor {
    private float x,y;
    private Sprite sprite;
    private Boolean colliding;
    private Texture texture;

    private Animation animation;
    private TextureRegion textureRegion;
    private TextureRegion[][] tmp;
    private String jugadorVista;
    private float tiempo;
    private TextureRegion[] regions;


    private OrthographicCamera camara; //La necesito para que me siga
    private Vector3 posicionTiles;
    private Batch batch;// La uso para dibujar en este batch al jugador. Podría pasarlo por constructor. Es decisión nuestra como programadoeres.
    private Boolean colision;
    //Variables para poder redimensionar al jugador según el zoom
    private TiledMap mapa; //Necesito el mapa para poder redimensionar al jugador
    private int anchuraMapaPixels; //Anchura del mapa donde nos movemos en pixels
    private int alturaMapaPixels; //Altura del mapa donde nos movemos en pixels
    private int anchuraMapaTiles; //Anchura del mapa donde nos movemos en  tiles
    private int alturaMapaTiles; //Anchura del mapa donde nos movemos en tiles
    private Rectangle rectangle;
    private Rectangle[]rectangles;
    private Colisiones colisiones;
     float anchoJugador, largoJugador;
    private Music sonidoColision;

     private Juego juego;
    public Jugador(TiledMap mapa, Colisiones col, OrthographicCamera c,float posicionPersonajeX, float posicionPersonajeY, float anchoJugador, float largoJugador, Juego juego) {
        this.colisiones=col;
        colision=false;
        this.juego=juego;
        this.x=posicionPersonajeX;
        this.y=posicionPersonajeY;
        this.anchoJugador=anchoJugador;
        this.largoJugador=largoJugador;
        this.setSize(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
        texture=new Texture(Gdx.files.internal("Sprites/gfx/character.png"));
        this.sprite = new Sprite(texture);
        colliding=new Boolean(false);
        sonidoColision=Gdx.audio.newMusic(Gdx.files.internal("raw/sonidoColision.mp3"));

        sonidoColision.setVolume(10);
        this.camara = c;
        rectangles=colisiones.getRect();
        rectangle=new Rectangle(posicionPersonajeX,posicionPersonajeY,texture.getWidth(),texture.getHeight());
        batch=new SpriteBatch();
        anchuraMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getWidth(); //Obtenemos desde el mapa el número de tiles de ancho de la 1º Capa
        alturaMapaTiles = ((TiledMapTileLayer) mapa.getLayers().get(0)).getHeight(); //Obtenemos desde el mapa el número de tiles de alto de la 1º Capa
        System.out.println(sprite.getX());
        System.out.println(sprite.getY());
        anchuraMapaPixels=anchuraMapaTiles*(int)mapa.getProperties().get("width");
        alturaMapaPixels=alturaMapaTiles*(int)mapa.getProperties().get("height");
        //sprite.setPosition(250,250);
        sprite.setBounds(posicionPersonajeX,posicionPersonajeY,anchoJugador,largoJugador);
        //x e y es donde aparece el personaje, width y height altura y anchura
       jugadorVista="";
        tmp = TextureRegion.split(texture, texture.getWidth() / 17, texture.getHeight() / 8);
        regions = new TextureRegion[4];
        for (int b = 0; b < regions.length; b++) {
            regions[b] = tmp[0][0];
            animation = new Animation((float) 0.2, regions);
            tiempo = 0f;
        }
    }


    /**
     * Esta función redimensiona el sprite del jugador según el tamaño del mapa,
     * el tamaño de la propia textura del jugador, y el zoom actual. Debería llamarse
     * en dibujar.
     */

    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }

    public void dibujarConHitbox( SpriteBatch batch){

        tiempo += Gdx.graphics.getDeltaTime();
        textureRegion = (TextureRegion) animation.getKeyFrame(tiempo, true);
        setBounds(x,y,anchoJugador-1,largoJugador-5);
        batch.draw(textureRegion, x, y,anchoJugador,largoJugador);

    }
    /**
     * Mueve el jugador un tile en la dirección establecida
     * @param direccion 'u' -> arriba,'d' -> abajo,'l' -> izda, 'r' -> derecha
     */
    public void moverJugador(char direccion){
        switch (direccion){
            case 'w':
                //Cambio posición del jugador, todavía no cambia nada visualmente
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y+7,sprite.getWidth(),sprite.getHeight()))){
                        colision=true;
                        sonidoColision.play();
                        System.out.println(colision);
                        break;

                    }
                    if(colision==false){
                        for (int i=0;i<colisiones.getSalida().length;i++){
                            if(colisiones.getSalida()[i].overlaps(rectangle.set(x,y,anchoJugador,largoJugador))){
                                switch (colisiones.getObj2()[i].getName()){
                                    case "EntradaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                    case "SalidaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/7.4f,Gdx.graphics.getWidth()/16.857f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa1":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/4.414f,Gdx.graphics.getHeight()/2.2f));
                                        System.out.println("Entrando a  mapa2");
                                        break;

                                    case "EntradaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/1.66f,Gdx.graphics.getWidth()/2.4609f));
                                        System.out.println("Entrando a mapa2");
                                        break;

                                    case "SalidaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa4(this.juego,Gdx.graphics.getWidth()/44.58f,Gdx.graphics.getWidth()/3.057f));
                                        System.out.println("Entrando a mapa4");
                                        break;
                                    case "EntradaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/2.08f,Gdx.graphics.getWidth()/4.457f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                }
                            }

                        }
                    }else {
                        System.out.println("No detectado");
                        colision = false;
                    }

                }

                if(colision==false){
                    y=y+3;
                }

                break;
            case 's':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x,y-7,sprite.getWidth(),sprite.getHeight()))){
                        colision=true;
                        sonidoColision.play();
                        System.out.println(colision);

                        break;
                    }
                    if(colision==false){
                        for (int i=0;i<colisiones.getSalida().length;i++){
                            if(colisiones.getSalida()[i].overlaps(rectangle.set(x,y,anchoJugador,largoJugador))){
                                switch (colisiones.getObj2()[i].getName()){
                                    case "EntradaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                    case "SalidaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/7.4f,Gdx.graphics.getWidth()/16.857f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa1":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/4.414f,Gdx.graphics.getHeight()/2.2f));
                                        System.out.println("Entrando a  mapa2");
                                        break;
                                    case "EntradaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/1.66f,Gdx.graphics.getWidth()/2.4609f));
                                        System.out.println("Entrando a mapa2");
                                        break;

                                    case "SalidaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa4(this.juego,Gdx.graphics.getWidth()/44.58f,Gdx.graphics.getWidth()/3.057f));
                                        System.out.println("Entrando a mapa4");
                                        break;
                                    case "EntradaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/2.08f,Gdx.graphics.getWidth()/4.457f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                }
                            }

                        }
                    }else {
                        System.out.println("No detectado");
                        colision = false;
                    }
                }
                if(colision==false){
                    y=y-3;
                }
                break;
            case 'a':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x-7,y,sprite.getWidth(),sprite.getHeight()))){
                        colision=true;
                        sonidoColision.play();

                        break;

                    }
                    if(colision==false){
                        for (int i=0;i<colisiones.getSalida().length;i++){
                            if(colisiones.getSalida()[i].overlaps(rectangle.set(x,y,anchoJugador,largoJugador))){
                                switch (colisiones.getObj2()[i].getName()){
                                    case "EntradaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                    case "SalidaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/7.4f,Gdx.graphics.getWidth()/16.857f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa1":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/4.414f,Gdx.graphics.getHeight()/2.2f));
                                        System.out.println("Entrando a  mapa2");
                                        break;
                                    case "EntradaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/1.66f,Gdx.graphics.getWidth()/2.4609f));
                                        System.out.println("Entrando a mapa2");
                                        break;

                                    case "SalidaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa4(this.juego,Gdx.graphics.getWidth()/44.58f,Gdx.graphics.getWidth()/3.057f));
                                        System.out.println("Entrando a mapa4");
                                        break;
                                    case "EntradaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/2.08f,Gdx.graphics.getWidth()/4.457f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                }
                            }

                        }
                    }else {
                        System.out.println("No detectado");
                        colision = false;
                    }
                }
                if(colision==false){
                    x=x-3;
                }
                break;
            case 'd':
                for(int b=0;b<rectangles.length;b++){
                    if(rectangles[b].overlaps(rectangle.set(x+7,y,sprite.getWidth(),sprite.getHeight()))){
                        colision=true;
                        sonidoColision.play();

                        break;

                    }
                    if(colision==false){
                        for (int i=0;i<colisiones.getSalida().length;i++){
                            if(colisiones.getSalida()[i].overlaps(rectangle.set(x,y,anchoJugador,largoJugador))){
                                switch (colisiones.getObj2()[i].getName()){
                                    case "EntradaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                    case "SalidaMapa2":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/7.4f,Gdx.graphics.getWidth()/16.857f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa1":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/4.414f,Gdx.graphics.getHeight()/2.2f));
                                        System.out.println("Entrando a  mapa2");
                                        break;
                                    case "EntradaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa2(this.juego,Gdx.graphics.getWidth()/1.66f,Gdx.graphics.getWidth()/2.4609f));
                                        System.out.println("Entrando a mapa2");
                                        break;

                                    case "SalidaMapa3":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa4(this.juego,Gdx.graphics.getWidth()/44.58f,Gdx.graphics.getWidth()/3.057f));
                                        System.out.println("Entrando a mapa4");
                                        break;
                                    case "EntradaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa3(this.juego,Gdx.graphics.getWidth()/2.08f,Gdx.graphics.getWidth()/4.457f));
                                        System.out.println("Entrando a mapa3");
                                        break;
                                    case "SalidaMapa4":
                                        juego.dispose();
                                        juego.setPantallaActual(new Mapa1(this.juego,Gdx.graphics.getWidth()/2f,Gdx.graphics.getWidth()/6.857f));
                                        System.out.println("Entrando a mapa1");
                                        break;
                                }
                            }

                        }
                    }else {
                        System.out.println("No detectado");
                        colision = false;
                    }
                }
                if(colision==false){
                    x=x+3;
                }
                break;
        }
    }

     public void hacerAnimaciones(char letra) {
        switch (letra) {
            case 'd':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 's':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'a':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'w':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case 'º':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[5][b];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
        }


    }

     public void pararPersonaje(char letra) {
        switch (letra) {
            case 'd':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Derecha";
                }
                break;
            case 's':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Abajo";
                }
                break;
            case 'a':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Izquierda";
                }
                break;
            case 'w':
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                    jugadorVista = "Arriba";
                }
                break;
        }

    }
    public void pararJugador() {
        texture = new Texture(Gdx.files.internal("Sprites/gfx/character.png"));
        tmp = TextureRegion.split(texture, texture.getWidth() / 17, texture.getHeight() / 8);
        regions = new TextureRegion[4];
        switch (jugadorVista) {
            case "Derecha":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[1][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Izquierda":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[3][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Abajo":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[0][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
            case "Arriba":
                for (int b = 0; b < regions.length; b++) {
                    regions[b] = tmp[2][0];
                    animation = new Animation((float) 0.2, regions);
                    tiempo = 0f;
                }
                break;
        }
    }
    public OrthographicCamera getCamara(){
        return camara;
    }

    public void dispose(){
        batch.dispose();
    }
}
