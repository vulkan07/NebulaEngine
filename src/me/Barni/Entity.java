package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity {



    public String name;

    public int getX() {return x;}

    public int getY() {return y;}

    public Game game;
    public BufferedImage texture;
    public int width, height, centerX, centerY;
    private int x,y;
    public Rectangle hitBox = null;
    public boolean textureUpdated, moved, textureRotated;

    public short facing, toBeFaced; //1:up   2:right   3:down   4:left


    public void move(int x2, int y2)
    {
        if (x2==0 && y2==0) return;
        x += x2;
        y += y2;
        moved = true;
    }

    public Entity(String name, int x, int y, BufferedImage texture, Game g)
    {
        this.name = name;
        this.game = g;
        this.x = x;
        this.y = y;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        this.texture = texture;
    }


    public void rotate()
    {

        int amount = 4-facing+toBeFaced;
        if (amount <= 0) return;

        BufferedImage img2 = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i <= amount; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    img2.setRGB(y, x, texture.getRGB(x, y));
                }
            }
        }
        texture = img2;
    }

    public void render(BufferedImage img)
    {
        //If don't need to render, return;
        //if (!textureUpdated && !moved) return;

        int scale = game.map.camera.zoom;

        //if (textureUpdated || moved) { rotate(); textureRotated=false;}

        img.getGraphics().drawImage(texture, x-game.map.camera.scrollX, y-game.map.camera.scrollY, width*scale, height*scale, null);
        textureUpdated = false;
        moved = false;

    }

    public void tick()
    {
        centerX = x+width/2;
        centerY = y+height/2;
    }

}
