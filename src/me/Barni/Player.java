package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    final float gravity = 0.8f;
    float speed, velocityX, velocityY, jumpForce;
    boolean isOnGround, canJump = true, collided;

    //static Rectangle otherBox = new Rectangle(200,200,100,100);

    public Player(String name, int x, int y, BufferedImage texture, Game g) {
        super(name, x, y, texture, g);
        speed = 1;
        hitBox = new Rectangle(0,0,width,height);
    }

    @Override
    public void render(BufferedImage img )
    {

        /*try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        super.render(img);

        Graphics g = img.getGraphics();
        g.setColor(Color.RED);
        //g.drawRect(otherBox.x-game.cam.scrollX, otherBox.y-game.cam.scrollY, otherBox.width, otherBox.height);
        /*if (collided)
            g.fillRect(x-game.cam.scrollX, y-game.cam.scrollY, width*game.cam.zoom, height*game.cam.zoom);
        else
            g.drawRect(x-game.cam.scrollX, y-game.cam.scrollY, width*game.cam.zoom, height*game.cam.zoom);*/

        int cX = width/2*game.cam.zoom;
        int cY = height/2*game.cam.zoom;

        int x = getX()-game.cam.scrollX;
        int y = getY()-game.cam.scrollY;
        g.drawLine(x+cX, y+cY,x+(int)velocityX*5+cX, y+(int)velocityY*5+cY);
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

            if (canJump) {
                jumpForce = 8;
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
                this.velocityX = 0;

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
        //this.velocityY += gravity;

        //APPLY JUMP FORCE
        this.velocityY -= jumpForce;

        collided=false;


        for (Rectangle otherBox : game.map.hitboxes) {

            if (otherBox==null) continue;

            int otherCenterX = otherBox.x + otherBox.width/2;
            int otherCenterY = otherBox.y + otherBox.height/2;
            int vX = centerX - otherCenterX;
            int vY = centerY - otherCenterY;
            boolean dir = vX*vX > vY*vY;

            if (y + height > otherBox.y && y < otherBox.y + otherBox.height)
                if (x + width > otherBox.x && x < otherBox.x + otherBox.width) {
                    collided = true;

                    if (dir) {
                        if (centerX > otherCenterX) {
                            //RIGHT
                            if (velocityX < 0) velocityX = 0;
                            this.x = otherBox.x + otherBox.width;
                        } else {
                            //LEFT
                            if (velocityX > 0) velocityX = 0;
                            this.x = otherBox.x - width;
                        }
                    } else {
                        if (centerY > otherCenterY) {
                            //DOWN

                            if (velocityY < 0) velocityY = 0;
                            this.y = otherBox.y + otherBox.height;
                        } else {
                            //UP
                            canJump = true;
                            if (velocityY > 0) velocityY = 0;
                            this.y = otherBox.y - height;
                        }
                    }
                }
        }




        //APPLY VELOCITY ON POSITION
        move((int)velocityX,(int)velocityY);


    }
}


/*
boolean horizontal = x+width+velocityX > otherBox.x && x+width+velocityX < otherBox.x+otherBox.width;
boolean vertical = y+height+velocityY > otherBox.y && y+height+velocityY < otherBox.y+otherBox.height;
if ( vertical && horizontal)
{
    if (x+width > otherBox.x || x > otherBox.x + otherBox.width) {
        this.x = (x + width < otherBox.x + otherBox.width / 2 ? otherBox.x - width /*bal : otherBox.x + otherBox.width/2 /*jobb);
        velocityX = 0;
    }
    if (y+height > otherBox.y || y > otherBox.y + otherBox.height){
        this.y = (y+height < otherBox.y+ otherBox.height/2 ? otherBox.y-height /*bal : otherBox.y + otherBox.height/2 /*jobb);
        velocityY = 0;
    }
}
 */
