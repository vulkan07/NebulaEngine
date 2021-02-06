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
    public boolean textureUpdated, moved;

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

    public void render(BufferedImage img)
    {
        //If don't need to render, return;
        //if (!textureUpdated && !moved) return;

        img.getGraphics().drawImage(texture, x, y, null);
        textureUpdated = false;
        moved = false;

    }

    public void tick()
    {
        centerX = x+width/2;
        centerY = y+height/2;
    }

}
