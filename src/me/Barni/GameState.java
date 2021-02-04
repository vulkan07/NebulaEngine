package me.Barni;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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


            //===== CAMERA =====\\
            //Center Camera to player
            int size = game.ts.tileSize*game.map.worldSize/map.camera.zoom;
            map.camera.scrollX = Math.min(Math.max(0,(player.x - game.WIDTH/2 + player.width/2)), size+ game.WIDTH);
            map.camera.scrollY = Math.min(Math.max(0,(player.y - game.HEIGHT/2 + player.height/2)), size+game.HEIGHT*3);
            map.camera.checkRenderNeed();


            //=====  MAP  =====\\
            //Calculate to render only visible tiles
            int x0 = Math.max(0,(map.camera.scrollX/game.ts.tileSize/map.camera.zoom));
            int y0 = Math.max(0,(map.camera.scrollY/game.ts.tileSize/map.camera.zoom));
            map.camera.renderTiles(image, x0, y0,
                    game.WIDTH/(game.ts.tileSize)/map.camera.zoom+x0+1,
                    game.HEIGHT/(game.ts.tileSize)/map.camera.zoom+y0+1, false);

            //===== ENTITY =====\\
            map.camera.renderEntities(image, false);


        }
    }

    @Override
    public void tick() {
        ticks++;
        player.tick();
        player.getInventory().selected = game.keyManager.pressedNum-1 == -1 ? 9 : game.keyManager.pressedNum-1;

        if (game.keyManager.pressedKeys[81]) {player.getInventory().hotbarContent[player.getInventory().selected].setId(0);}
        //if (game.keyManager.pressedKeys[32]) map.loadMap("C:\\Dev\\test.txt"); I CAN LOAD A MAP!!! :D


        //MOUSE
        try {

            int scale = (game.ts.tileSize * game.map.camera.zoom);
            int posX = (game.getMousePosition().x + game.map.camera.scrollX) / scale;
            int posY = (game.getMousePosition().y + game.map.camera.scrollY) / scale;
            Tile t = game.map.tiles[posX][posY];
            int selected = player.getInventory().hotbarContent[player.getInventory().selected].getId();

            //LEFT CLICK
            if (game.mouse.buttons[1]) {
                //PLACE STONE
                if (t.getId()==1 && selected == 2)
                {
                    if (player.getInventory().remove(2)) t.setId(7);

                    /*for (Item i : player.getInventory().hotbarContent) {
                        if (i.getId() == 2) {
                            i.count-=1;
                            if (i.count==0) i.setId(0);
                            break;
                        }
                        t.setId(7); //r.nextInt(2)==1?6:4
                    }*/
                }
            }
            //RIGHT CLICK
            else if (game.mouse.buttons[3]) {


                //PICKAXE
                if (selected == 4)
                    for (int[] a : Item.PICKAXE_BREAKS)
                    {
                        if (t.getId() == a[0]) {
                            t.setId(1);
                            player.getInventory().add(a[1], 1);
                        }
                    }
                //AXE
                if (selected == 5)
                    for (int[] a : Item.AXE_BREAKS)
                    {
                        if (t.getId() == a[0]) {
                            t.setId(1);
                            player.getInventory().add(a[1], 1);
                        }
                    }

                //HOE
                if (selected == 6)
                    for (int a : Item.HOE_AFFECTS_ON)
                        if (t.getId() == a) t.setId(Tile.FARM);

                //HAND
                if (t.getId()==2) {
                    t.setId(1);
                    player.getInventory().add(3,r.nextInt(3)+1);
                }
            }

            //ON ANY CLICK
            if (game.mouse.buttons[1] || game.mouse.buttons[3])
            {
                t.isClicked = true;
                game.map.camera.renderTiles(image, posX, posY, posX+1, posY+1, true);
                game.map.camera.renderEntities(image, true);

            //NO CLICK
            } else
                { t.isClicked = false; }


        } catch (ArrayIndexOutOfBoundsException ex) {}
          catch (NullPointerException ex2) {}
    }

    @Override
    public void start() {

        player = new Player("Player",0,0, game.ts_entity.getTileTextureAt(1,0,1), game);
        Inventory inv = new Inventory(game, player);
        player.setInventory(inv);
        player.getInventory().hotbarContent[0].setId(4);
        player.getInventory().hotbarContent[1].setId(5);
        player.getInventory().hotbarContent[2].setId(6);
        map.entities.add(player);


        anim = new Animator(4, "grass.png", player, game);

        Thread t = new Thread(anim);
        t.start();

        running = true;


    }

    @Override
    public void stop() {
        running = false;
    }
}
