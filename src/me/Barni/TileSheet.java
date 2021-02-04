package me.Barni;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileSheet {

    private BufferedImage fullImg;
    public final int DEF_MAP_SIZE = 256, DEF_TILE_SIZE = 32;
    public int mapSize, tileSize;




    public TileSheet(String path, int tsize)
    {
        try {
            fullImg = ImageIO.read(new File(path));
        } catch (IOException e)
        {
            fullImg = new BufferedImage(DEF_MAP_SIZE, DEF_MAP_SIZE, BufferedImage.TYPE_INT_ARGB);
        }
        mapSize = fullImg.getWidth();
        tileSize = tsize;
    }

    public BufferedImage getTileTextureAt(int xOff, int yOff, int size)
    {

        BufferedImage tile = new BufferedImage(tileSize*size ,tileSize*size, BufferedImage.TYPE_INT_ARGB);

        int[] px = fullImg.getRGB(xOff*tileSize,yOff*tileSize,tileSize*size,tileSize*size, null, 0, tileSize*size);
        tile.setRGB(0,0,tileSize*size,tileSize*size,px,0,tileSize*size);

        return tile;
    }

}
