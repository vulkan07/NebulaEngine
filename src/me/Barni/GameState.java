package me.Barni;

import java.awt.image.BufferedImage;
import java.util.Random;

public class GameState extends State {


    public Map map;
    public Player player;
    Random r;
    Animator anim;


    public GameState(Game game, BufferedImage renderCanvas, Map m, TileSheet ts)
    {
        super(game, renderCanvas);
        this.map = m;
        r = new Random();
    }

    //CALLED BY GAME RENDERER
    @Override
    public void render() {
        if (running)
        {

            //map.camera.checkRenderNeed();
            //===== CAMERA =====\\
            //Center Camera to player
            int size = game.ts.tileSize*game.map.worldSize/map.camera.zoom;
            map.camera.scrollX = Math.min(Math.max(0,(player.getX() - game.WIDTH/2 + player.width/2)), size+game.WIDTH);
            map.camera.scrollY = Math.min(Math.max(0,(player.getY() - game.HEIGHT/2 + player.height/2)), size+game.WIDTH);




            //=====  MAP  =====\\
            //Calculate to render only visible tiles
            int x0 = Math.max(0,(map.camera.scrollX/game.ts.tileSize/map.camera.zoom));
            int y0 = Math.max(0,(map.camera.scrollY/game.ts.tileSize/map.camera.zoom));
            map.camera.renderTiles(image, x0, y0,
                    game.WIDTH/(game.ts.tileSize)/map.camera.zoom+x0+1,
                    game.HEIGHT/(game.ts.tileSize)/map.camera.zoom+y0+1, false);

            //===== ENTITY =====\\
            map.camera.renderEntities(image);


        }
    }

    @Override
    public void tick() {
        ticks++;

        for (Entity e : map.entities) {e.tick();}
        //if (game.keyManager.pressedKeys[32]) map.loadMap("C:\\Dev\\test.txt"); I CAN LOAD A MAP!!! :D


        //MOUSE
        try {

            int scale = (game.ts.tileSize * game.map.camera.zoom);
            int posX = (game.getMousePosition().x + game.map.camera.scrollX) / scale;
            int posY = (game.getMousePosition().y + game.map.camera.scrollY) / scale;
            //Tile t = game.map.tiles[posX][posY];

            //LEFT CLICK
            if (game.mouse.buttons[1]) {
            }
            //RIGHT CLICK
            else if (game.mouse.buttons[3]) {
            }

            //ON ANY CLICK
            if (game.mouse.buttons[1] || game.mouse.buttons[3])
            {

            //NO CLICK
            } else
            {

            }


        } catch (ArrayIndexOutOfBoundsException ex) {}
          catch (NullPointerException ex2) {}
    }

    @Override
    public void start() {

        player = new Player("Player",0,0, game.ts_entity.getTileTextureAt(1,0,1), game);
        map.entities.add(player);
        map.loadMap(game.$GAME_DIR+"test.map");

        anim = new Animator(4, "player.png", player, game);

        Thread t = new Thread(anim);
        t.start();

        running = true;


    }

    @Override
    public void stop() {
        running = false;
    }
}
