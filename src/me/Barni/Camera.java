package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class Camera {

    public int zoom, scrollX, scrollY;
    private int zoom2, scrollX2, scrollY2;

    private BufferedImage[] textures;

    //private boolean needToRenderEntities;
    //public boolean needToRenderTiles;
    public Game game;
    public Map map;
    public TileSheet ts;

    public Camera(Game game, Map map, TileSheet ts) {

        this.ts = ts;
        this.zoom = 1;
        this.scrollX = 0;
        this.scrollY = 0;
        zoom2 = zoom;
        scrollX2 = scrollX;
        scrollY2 = scrollY;
        this.game = game;
        this.map = map;

    }

    /*public void checkRenderNeed()
    {
        if (zoom == zoom2 && scrollX == scrollX2 && scrollY == scrollY2 && game.gameState.player.velocityX==0 &&game.gameState.player.velocityY==0)
        {
            needToRenderTiles = false;
        } else {needToRenderTiles = true;}

        zoom2 = zoom;
        scrollX2 = scrollX;
        scrollY2 = scrollY;
    }*/

    public void scrollTiles(BufferedImage renderedImg, int vert, int horiz) {
        int[] pixels = ((DataBufferInt) renderedImg.getRaster().getDataBuffer()).getData();

        for (int y = 0; y < renderedImg.getHeight(); y++) {
            for (int x = 0; x < renderedImg.getWidth(); x++) {

                pixels[x*renderedImg.getHeight() + Math.max(y-horiz,0)] = pixels[x*renderedImg.getHeight() + y];

            }
        }
    }
        //HORIZ
        /*for (int x = 0; x < renderedImg.getHeight(); x++)
        {
            for (int y = 0; y < renderedImg.getWidth(); y++)
            {
                pixels[ Math.max(y-horiz,0) + x*renderedImg.getWidth() ]
                        =
                pixels[ y + x*renderedImg.getWidth()];
            }
        }*/


    /**PRELOADS TEXTURES INTO AN ARRAY(textures) FOR FASTER ACCESS**/
    public void cacheTextures()
    {
        textures = new BufferedImage[Tile.textureOffset.length];

        for (int i = 0; i < Tile.textureOffset.length; i++)
        {
            textures[i] = ts.getTileTextureAt(Tile.textureOffset[i][1], Tile.textureOffset[i][0],1);
        }

        //textures[0] = ts.getTileTextureAt( Tile.textureOffset[0][1], Tile.textureOffset[0][0], 1);

    }

    //RENDER EACH TILE
    public void renderTiles(BufferedImage image, int x1, int y1, int x2, int y2, boolean forced)
    {

        //if (!needToRenderTiles && !forced) {return;}

        Graphics g = image.getGraphics();
        int scale = (ts.tileSize * zoom);

        //Background
        //g.setColor(new Color(20, 75, 30));
        //g.fillRect(0,0, image.getWidth(), image.getHeight());
        //if (!forced) g.drawImage(bg, -scrollX/2-200, -scrollY/2-200, zoom*bg.getWidth()*2, zoom*bg.getHeight()*2, null);

        for (int x = x1; x < (Math.min(x2, map.worldSize)); x++)
        {
            for (int y = y1; y < (Math.min(y2, map.worldSize)); y++)
            {
                int realX = x * ts.tileSize  * zoom - scrollX;
                int realY = y * ts.tileSize  * zoom - scrollY;
                if (map.tiles[x][y].getId() == 0)
                {
                    g.fillRect(realX,realY,scale,scale);
                } else {
                    //textures[map.tiles[x][y].id]
                    g.drawImage(textures[map.tiles[x][y].id], realX, realY, scale, scale, null);
                }
            }
        }
        //needToRenderTiles = false;
        //needToRenderEntities = true;
    }



    public void renderEntities(BufferedImage image)
    {
        for (Entity e : map.entities) {
            e.render(image);
        }
        //needToRenderEntities = false;
    }
}
