package me.Barni;

import java.awt.image.BufferedImage;

public class Tile {



    //ID -> TILEMAP OFFSETS
    public static final int[][] textureOffset = {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7}, {1,0}};

    //========================================\\
        public static final int VOID = 0;
        public static final int BORDER = 1;
        public static final int GRASS = 2;

        public static final int PLANT = 3;
        public static final int ROCK1 = 4;
        public static final int URANIUM = 5;

        public static final int ROCK2 = 6;
        public static final int STONE = 7;
        public static final int FARM = 8;
    //========================================\\


    public final int SIZE = 32;

    public Map map;
    public TileSheet ts;
    //public BufferedImage texture;

    public int getId() {
        return id;
    }

    public void setId(int nid) {
        this.id = nid;
        //this.texture = ts.getTileTextureAt( Tile.textureOffset[nid][1], Tile.textureOffset[nid][0], 1);;
    }

    public int id;
    public boolean isHovered, isClicked;

    public Tile(int type, TileSheet ts, Map m)
    {
        this.map = m;
        this.ts = ts;
        //this.texture = ts.getTileTextureAt( Tile.textureOffset[type][1], Tile.textureOffset[type][0], 1);;
        this.id = type;
    }


}
