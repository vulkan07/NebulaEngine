package me.Barni;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Inventory {

    public BufferedImage hotbarTexture, selectedFrame;
    public Item[] hotbarContent = new Item[10];
    public final int HOTBAR_SIZE = 10;
    public int selected;
    public Game game;
    public Player owner;


    public Inventory(Game game, Player p)
    {
        owner = p;
        this.game = game;
        BufferedImage hotbarFrame= null;

        try {

             hotbarFrame = ImageIO.read(new File("C:\\Dev\\hotbarFrame.png"));
             selectedFrame = ImageIO.read(new File("C:\\Dev\\selected.png"));

        } catch (IOException e) {
            System.out.println("Can't load inventory hotbar texture");
        }
        hotbarTexture = new BufferedImage(hotbarFrame.getWidth()*HOTBAR_SIZE, hotbarFrame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = hotbarTexture.getGraphics();
        for (int i = 0; i<HOTBAR_SIZE; i++)
        {
            hotbarContent[i] = new Item(0);
            g.drawImage(hotbarFrame, hotbarFrame.getWidth()*i, 0, null);
        }

    }

    public void render(BufferedImage img)
    {

        Graphics g = img.getGraphics();
        g.drawImage(hotbarTexture, game.WIDTH/2-hotbarTexture.getWidth()/2, game.HEIGHT - hotbarTexture.getHeight()*2, null);
        g.drawImage(
                selectedFrame,
                game.WIDTH/2-hotbarTexture.getWidth()/2+(selectedFrame.getWidth()*selected),
                game.HEIGHT - hotbarTexture.getHeight()*2,
                null
                );
        for (int i = 0; i<HOTBAR_SIZE; i++)
        {
            if (hotbarContent[i].getId() != 0) {
                g.drawImage(
                        hotbarContent[i].texture,
                        game.WIDTH / 2 - hotbarTexture.getWidth() / 2 + (selectedFrame.getWidth() * i),
                        game.HEIGHT - hotbarTexture.getHeight() * 2,
                        null);
                g.drawString(String.valueOf(hotbarContent[i].count), game.WIDTH / 2 - hotbarTexture.getWidth() / 2 + (selectedFrame.getWidth() * i)+5,
                        game.HEIGHT - hotbarTexture.getHeight() * 2+15);
            }
        }
    }

    public boolean remove(int id)
    {
        Item item;

        // LEHETNE A SELECTED ALAPJÁN XD

        //HÁTULRÓL CSÓRJA LE
        for (int h = 0; h < HOTBAR_SIZE; h++)
        {
            item = hotbarContent[HOTBAR_SIZE-h-1];

            if (item.getId() == id)
            {

                if (item.count > 1)
                {
                    item.count -= 1;
                } else {
                    item.setId(0);
                }
                return true;
            }

        }

        return false;
    }

    public void add(int id, int amount)
    {
        for (Item i : hotbarContent) {
            if (i.getId() == 0) {
                i.setId(id);
                i.count = amount;
                break;
            } else if (i.getId() == id) {
                i.count += amount;
                if (i.count > 100) {
                    amount  = i.count-100;
                    i.count = 100;
                    continue;
                }
                break;
            }

        }
    }

}
