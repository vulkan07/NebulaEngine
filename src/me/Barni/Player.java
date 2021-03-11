package me.Barni;


import java.awt.*;
import java.awt.image.BufferedImage;


public class Player extends Entity {

    float speed = 0.5f, resistance = 0.3f, jumpForce = -25f;
    final Vec2D gravity = new Vec2D(0, 0.5f);
    Vec2D vel, acc;
    KeyManager km;

    boolean isOnGround, jumped = false, collided;


    public Player(String name, int x, int y, BufferedImage texture, Game g) {
        super(name, x, y, texture, g);

        km = game.keyManager;

        //Initialize physics vectors, scalars
        pos = new Vec2D(0,0);
        vel = new Vec2D(0,0);
        acc = new Vec2D(0,0);

        hitBox = new Rectangle(0,0,width,height);
    }

    @Override
    public void render(BufferedImage img )
    {
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
        g.drawLine(x+cX, y+cY,x+(int)vel.x*5+cX, y+(int)vel.y*5+cY);
    }

    @Override
    public void update(float dt)
    {
        //Calculates center pos
        super.update(dt);

        int x = getX();
        int y = getY();


        //RESET ACCELERATION
        acc.mult(new Vec2D(0,0));

        //==JUMP LOGIC ==\\

        if ( km.pressedKeys[km.KEY_UP] )
        {
            if (isOnGround &&!jumped)
            {
                acc.y+=jumpForce*dt;
                isOnGround=false;
            }
            jumped = true;

        } else jumped=false;
        //====ACCELERATE BY KEYS====\\
        if ( km.pressedKeys[km.KEY_DOWN] ) acc.y += speed*dt;
        if ( km.pressedKeys[km.KEY_LEFT] ) acc.x -= speed*dt;
        if ( km.pressedKeys[km.KEY_RIGHT] ) acc.x += speed*dt;

        //DECREASE VELOCITY
        vel.decrease(resistance*dt);

        //APPLY GRAVITY

        acc.add( gravity );

        //APPLY ACCELERATION
        vel.add(acc);

        //LIMIT VEL (MAX SPEED)
        vel.limit(12f);

        collided=false;
        for (Rectangle otherBox : game.map.hitboxes) {

            if (otherBox == null) continue;

            int otherCenterX = otherBox.x + otherBox.width / 2;
            int otherCenterY = otherBox.y + otherBox.height / 2;
            int vX = (int) center.x - otherCenterX;
            int vY = (int) center.y - otherCenterY;
            boolean dir = vX * vX > vY * vY;

            if (y + height > otherBox.y && y < otherBox.y + otherBox.height)
                if (x + width > otherBox.x && x < otherBox.x + otherBox.width) {
                    collided = true;
                    /*if (game.map.tiles[otherBox.x/game.ts.tileSize][otherBox.y/game.ts.tileSize].getId() == 1) {
                        this.x = 0;
                        this.y = 0;
                        this.vel.x = 0;
                        this.vel.y = 0;
                    }*/

                    if (dir) {
                        if (center.x > otherCenterX) {
                            //RIGHT
                            if (vel.x < 0) vel.x = 0;
                            pos.x = otherBox.x + otherBox.width - 1;
                        } else {
                            //LEFT
                            if (vel.x > 0) vel.x = 0;
                            pos.x = otherBox.x - width + 1;
                        }
                    } else {
                        if (center.y > otherCenterY) {
                            //DOWN

                            if (vel.y < 0) vel.y = 0;
                            pos.y = otherBox.y + otherBox.height - 1;
                        } else {
                            //UP
                            isOnGround = true;
                            if (vel.y > 0) vel.y = 0;
                            pos.y = otherBox.y - height + 1;
                        }
                    }
                }
        }
        //APPLY VELOCITY ON POSITION
        move(vel);
    }
}
