package Mapas;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Mapa3{
    private TiledMap map;
        private AssetManager manager;
        private int tileWidth, tileHeight,
                mapWidthInTiles, mapHeightInTiles,
                mapWidthInPixels, mapHeightInPixels;
        private OrthographicCamera camera;
        private OrthogonalTiledMapRenderer renderer;
        private TiledMapTileLayer terrainLayer,terrainLayer2,terrainLayer3;

        public Mapa3(){
            manager = new AssetManager();
            manager.setLoader(TiledMap.class, new TmxMapLoader());
            manager.load("CiudadBosqueFinal.tmx", TiledMap.class);
            manager.finishLoading();
            map = manager.get("CiudadBosqueFinal.tmx", TiledMap.class);
            MapProperties properties = map.getProperties();
            tileWidth = properties.get("tilewidth", Integer.class);
            tileHeight = properties.get("tileheight", Integer.class);
            mapWidthInTiles = properties.get("width", Integer.class);
            mapHeightInTiles = properties.get("height", Integer.class);
            mapWidthInPixels = mapWidthInTiles * tileWidth;
            mapHeightInPixels = mapHeightInTiles * tileHeight;
            camera = new OrthographicCamera(mapWidthInPixels, mapHeightInPixels);
            camera.position.x = mapWidthInPixels / 2f;
            camera.position.y = mapHeightInPixels / 2f;
            renderer = new OrthogonalTiledMapRenderer(map);
            MapLayers mapLayers = map.getLayers();
            terrainLayer = (TiledMapTileLayer) mapLayers.get("Colisionables");
            terrainLayer2 = (TiledMapTileLayer) mapLayers.get("Entrada");
            terrainLayer3 = (TiledMapTileLayer) mapLayers.get("Salida");

            renderer = new OrthogonalTiledMapRenderer(map);
        }

        public void renderSuelos() {
            renderer.setView(camera);
            renderer.getBatch().begin();
            renderer.renderTileLayer(terrainLayer);
            renderer.renderTileLayer(terrainLayer2);
            renderer.renderTileLayer(terrainLayer3);
            renderer.getBatch().end();
        }

        public OrthographicCamera getCamera() {
            return camera;
        }

        public int getMapWidthInPixels() {
            return mapWidthInPixels;
        }

        public int getMapHeightInPixels() {
            return mapHeightInPixels;
        }

        public void dispose() {
            manager.dispose();
        }

        public TiledMap getMap() {
            return map;
        }
}