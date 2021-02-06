package me.Barni;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class Animator implements Runnable {

    BufferedImage[] images;
    BufferedImage canvas;
    int frames, current;
    int[] delays;
    boolean playing;

    Game game;
    Camera c;
    Entity e;

    public Animator(int length, String fileName, Entity e, Game game)
    {
        current = 0;
        this.game = game;
        this.c = game.map.camera;
        this.e = e;
        this.canvas = game.gameState.image;
        frames = length;
        images = new BufferedImage[frames];
        delays = new int[frames];
        this.playing = true;

        BufferedImage fullImage = null;
        try {
            fullImage = ImageIO.read(new File(Game.$GAME_DIR+fileName));
            BufferedReader f = new BufferedReader(new FileReader(new File(Game.$GAME_DIR+fileName.split("\\.png")[0]+".anim")));

            String[] g = f.readLine().split("-");
            for(int i = 0; i < frames; i++)
            {
                delays[i] = Integer.parseInt(g[i]);
            }


        } catch (IOException ioException) {
            System.out.println("[ANIMATOR] Can't load txt and/or .anim");
        }


        int size = fullImage.getHeight();

        //load frames
        for (int i = 0; i < frames; i++)
        {

            int[] px = fullImage.getRGB(32*i,0, size, size, null,  0,size*size);

            BufferedImage img = new BufferedImage(fullImage.getWidth()/frames, size, BufferedImage.TYPE_INT_ARGB);
            img.setRGB(0,0,size, size, px, 0, size*size);

            images[i] = img;
        }
    }

    /*public void step()
    {
        current = current+1>frames ? 0 : current+1;
        e.texture = images[current];
        int posX = e.centerX / game.ts.tileSize;
        int posY = e.centerY / game.ts.tileSize;


        c.renderTiles(canvas,posX-1,posY-1,posX+1,posY+1,true);
        c.renderEntities(canvas);
    }*/

    @Override
    public void run() {

        while (true)
        {
            //STEP FRAME
            current = current+1 < frames ? current+1 : 0;
            //CHANGE TEXTURE
            e.texture = images[current];
            e.textureUpdated = true;

            //DELAY
            try {
                Thread.sleep(delays[current]);
            } catch (InterruptedException ie) {}

            while ( !playing ) {}
        }
    }
}

