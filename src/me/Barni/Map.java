package me.Barni;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    public int worldSize = 32;
    public Camera camera;
    public Game game;

    public ArrayList<Entity> entities = new ArrayList<>();
    public Tile[][] tiles = new Tile[worldSize][worldSize];


    //MAKE A MAP
    public Map(Game g)
    {
        game = g;
        Random r = new Random();

        int type;
        for (int x = 0; x < worldSize; x++)
        {
            for (int y = 0; y < worldSize; y++)
            {
                type = y>16?1:0;
                if (y == 16) type = 2;
                tiles[x][y] = new Tile(type, game.ts, this);
            }
        }

    }

    public void loadMap(String pathname)
    {
        try {

            BufferedReader a = new BufferedReader(new FileReader(new File(pathname)));
            int mapSize = Integer.parseInt(a.readLine().split("s=")[1]);

            if (mapSize < 2) return;

            //tiles = new int[mapSize][mapSize];
            tiles = new Tile[mapSize][mapSize];
            worldSize = mapSize;
            for (int x = 0; x < mapSize; x++)
            {
                String[] line = a.readLine().split("\\,");
                for (int y = 0; y < mapSize; y++)
                {
                    tiles[y][x] = new Tile(Integer.parseInt(line[y]), game.ts, this);
                }
            }

            System.out.println("[MAP] Loaded map: " + pathname);

        } catch (FileNotFoundException fnfEx) {
            System.out.println("Can't find map: " + pathname);

        } catch (IOException ioEx)
        {
            System.out.println("There was an error with loading map: " + pathname);
        } catch (NumberFormatException nfEx) {return;}
    }

}
