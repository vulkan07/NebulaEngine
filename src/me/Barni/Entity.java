package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {



    public String name;
    public Game game;
    public BufferedImage texture;
    public int width, height,x,y, centerX, centerY;
    public Rectangle hitBox = null;


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

    public void tick()
    {
        centerX = x+width/2;
        centerY = y+height/2;
    }

}
