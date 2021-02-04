package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    int speed, velocityX, velocityY;



    private Inventory inventory;

    public Player(String name, int x, int y, BufferedImage texture, Game g) {
        super(name, x, y, texture, g);
        speed = 4;
        hitBox = new Rectangle(0,0,width,height);
    }

    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }

    @Override
    public void tick()
    {
        //OWN STUFF
        centerX = x+width/2;
        centerY = y+height/2;
        boolean[] pressedKeys = game.keyManager.pressedKeys;

        //MOVE BY KEYS
        if (pressedKeys[87]) {

            if (y > 0)
                this.velocityY -= this.speed; //FEL
            else
                y = 0;
        }

        if (pressedKeys[83]) {

            if (y < game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - height*2)
                this.velocityY += this.speed; //LE
            else
                {
                    y = game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - height*2;
                }
        }

        if (pressedKeys[65]) {

            if (x > 0)
                this.velocityX -= this.speed; //BALRA
            else
                x = 0;

        }

        if (pressedKeys[68]) {

            if (x < game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - width*2)
                this.velocityX += this.speed; //JOBBRA
            else
                x = game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - width*2;

        }

        //APPLY VELOCITY
        this.x += velocityX;
        this.y += velocityY;

        //DECREASE VELOCITY

        this.velocityX -= velocityX < 0 ? Math.min(0, velocityX+velocityX/-2) : Math.max(0, velocityX-velocityX/2);
        this.velocityY -= velocityY < 0 ? Math.min(0, velocityY+velocityY/-2) : Math.max(0, velocityY-velocityY/2);
    }
}
