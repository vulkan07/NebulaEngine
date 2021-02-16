package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    final float gravity = 1f;
    float speed, velocityX, velocityY, jumpForce;
    boolean isOnGround, canJump = true;

    public Player(String name, int x, int y, BufferedImage texture, Game g) {
        super(name, x, y, texture, g);
        speed = 4;
        hitBox = new Rectangle(0,0,width,height);
    }

    @Override
    public void render(BufferedImage img )
    {
        super.render(img);
        img.getGraphics().drawLine(getX(),getY(),getX()+(int)velocityY, getY()+(int)velocityY);
    }

    @Override
    public void tick()
    {
        super.tick();

        int x = getX();
        int y = getY();
        boolean[] pressedKeys = game.keyManager.pressedKeys;

        //MOVE BY KEYS
        if (pressedKeys[87]) {

            //canJump = true;
            if (canJump) {
                jumpForce = 10;
            }
            canJump = false;
        }

        if (pressedKeys[83]) {

            if (y < game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - height*2) {
                this.velocityY += this.speed; //LE
            }
            else
                    y = game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - height*2;
        }

        if (pressedKeys[65]) {

            if (x > 0) {
                this.velocityX -= this.speed; //BALRA
            }
            else
                x = 0;

        }

        if (pressedKeys[68]) {

            if (x < game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - width * 2) {
                this.velocityX += this.speed;
            } //JOBBRA
            else {
                x = game.ts.tileSize * game.map.camera.zoom * game.map.worldSize - width * 2;
            }
        }



        //DECREASE VELOCITY
        this.velocityX -= velocityX < 0 ? Math.min(0, velocityX+velocityX/-1.2) : Math.max(0, velocityX-velocityX/1.2);
        this.velocityY -= velocityY < 0 ? Math.min(0, velocityY+velocityY/-1.2) : Math.max(0, velocityY-velocityY/1.2);
        //DECREASE JUMP FORCE
        jumpForce -= jumpForce>0 ? 1 : 0;


        //APPLY GRAVITY
        this.velocityY += gravity;
        //APPLY JUMP FORCE
        this.velocityY -= jumpForce;





        //Test collision
        //DOWN


        try {
            int[] x2 = {x, x+width, x, x+width};
            int[] y2 = {y, y, y+width, y+width};

            for (int i = 0; i <= 5; i++ ) {
                int posX = (x2[i]) / 64;
                int posY = (y2[i]) / 64;

                //X
                if (game.map.tiles[posX + (int) velocityX][posY].getId() == 1)
                {
                   velocityX = 0;
                }


                //Y
                if (game.map.tiles[posX][posY + (int) velocityY].getId() == 1)
                {
                    velocityY = 0;
                }

                /*if (game.map.tiles[posX][posY].getId() == 1 || game.map.tiles[posX][posY].getId() == 2) {
                    velocityX -= (x+velocityX+width)-posX*64;
                    velocityY -= (y+velocityY+height)-posY*64;
                }*/
            }
        } catch (Exception aioobEx) {}

        //APPLY VELOCITY ON POSITION
        move((int)velocityX,(int)velocityY);
    }
}
