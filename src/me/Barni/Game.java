package me.Barni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public final int WIDTH = (int)(1920/1.25), HEIGHT = (int)(1080/1.25);
    public final String TITLE = "Árvíztűrő Tükörfúrógép";
    public JFrame frame;
    public static final String $GAME_DIR = "C:\\Dev\\";

    public TileSheet ts, ts_entity;
    public Map map;
    public Camera cam;

    public KeyManager keyManager;
    public Mouse mouse;
    public GameState gameState;

    private boolean running = false;


    private BufferedImage image = new BufferedImage((WIDTH), (HEIGHT), BufferedImage.TYPE_INT_ARGB);



    //===== S T A R T =====
    public synchronized void start()
    {

        //Preparations
        ts = new TileSheet($GAME_DIR+"tile_set.png",32);
        ts_entity = new TileSheet($GAME_DIR+"tile_set_entity.png",32);
        map = new Map(this);
        cam = new Camera(this, map, ts);
        map.camera = cam;
        map.camera.cacheTextures();

        gameState = new GameState(this, image, map, ts);
        gameState.start();


        createBufferStrategy(2);
        running = true;
        new Thread(this).start();
    }



    //===== M A K E   T H E   W I N D O W =====
    public void createWindow()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle(TITLE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(this);


        keyManager = new KeyManager();
        this.addKeyListener(keyManager);

        mouse = new Mouse();
        this.addMouseListener(mouse);

        this.addMouseWheelListener(e -> {
            Inventory inv = gameState.player.getInventory();
            int direction =e.getWheelRotation()*-1;

            if ((inv.selected < 9 && direction == -1) || (inv.selected > 0 && direction == 1))
            {
                inv.selected -= direction;
                keyManager.pressedNum -= direction;
            }

        });

        Dimension d = new Dimension(WIDTH, HEIGHT);
        frame.setMaximumSize(d);
        frame.setMinimumSize(d);
        frame.setPreferredSize(d);
        frame.setFocusable(false);
    }

    //===== R U N  (THREAD THING)=====
    public void run()
    {

        int fps = 0, tickCounts = 0;
        long last = System.nanoTime();

        double undone = 0;
        double pertickCount = 1000000000.0/60.0;
        long lastMillis = System.currentTimeMillis();

        while (running)
        {
            //Calculate the elapsed time
            long now = System.nanoTime();
            undone += (now-last)/pertickCount;
            last = now;

            //Catching up
            while (undone >= 1)
            {
                tickCounts++;
                undone--;
            }

            fps++;
            stepGame();


            /*try {
                Thread.sleep(  (int)(undone*1000/60)  );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/


            //EVERY SECONDS:
            if (System.currentTimeMillis() - lastMillis > 1000)
            {
                lastMillis += 1000;
                System.out.println(tickCounts + " tickCounts, " + fps + " fps");
                fps = 0;
                tickCounts = 0;
            }

        }
    }


    //===== M A I N   G A M E   M  E C H A N I C S =====
    public void stepGame()
    {
        gameState.tick();
        render();
    }


    //====== =  =   R E N D E R   =  = ======\\
    public void render()
    {

        gameState.render();

        Graphics g = getBufferStrategy().getDrawGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        getBufferStrategy().show();
    }


}
