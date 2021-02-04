package me.Barni;

import java.awt.image.BufferedImage;

public abstract class State {

    public Game game;
    public BufferedImage image;
    public int ticks;
    public boolean running;

    public State(Game game, BufferedImage renderCanvas)
    {
        running = false;
        this.game = game;
        this.image = renderCanvas;
    }

    public void render() {}
    public void tick() {}
    public void start() {}
    public void stop() {}

}
