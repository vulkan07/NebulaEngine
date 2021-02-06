package me.Barni;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Inventory {

    public BufferedImage hotbarTexture, selectedFrame, buffer;
    public Item[] hotbarContent = new Item[10];
    public final int HOTBAR_SIZE = 10;

    public int selected;
    private int prevSelected;
    public Game game;
    public Player owner;

    public boolean needToUpdate = true;

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
        g.dispose();
        buffer = new BufferedImage(hotbarTexture.getWidth(), hotbarTexture.getHeight(), BufferedImage.TYPE_INT_ARGB);

    }

    public void render(BufferedImage img)
    {
        if (selected != prevSelected){
            prevSelected = selected;
            needToUpdate = true;
        }


        //If not need to render, return a buffer(pre-rendered inventory image)
        if (!needToUpdate) {
            Graphics g = img.getGraphics();
            g.drawImage(buffer, game.WIDTH/2-buffer.getWidth()/2, game.HEIGHT - buffer.getHeight()*2, null);
            g.dispose();
            return;
        }


        buffer = new BufferedImage(hotbarTexture.getWidth(), hotbarTexture.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics bufferGraphics = buffer.getGraphics();

        //Draw bar
        bufferGraphics.drawImage(hotbarTexture, 0, 0, null);

        //Draw selected frame
        bufferGraphics.drawImage(
                selectedFrame,
                selectedFrame.getWidth()*selected,
                0,
                null
                );
        //Draw items in it
        for (int i = 0; i<HOTBAR_SIZE; i++)
        {
            if (hotbarContent[i].getId() != 0) {
                bufferGraphics.drawImage(
                        hotbarContent[i].texture,
                        selectedFrame.getWidth() * i,
                        0,
                        null);

                //Draw item amount
                bufferGraphics.drawString(String.valueOf(hotbarContent[i].count),
                        (selectedFrame.getWidth() * i)+5,
                        15);
            }
        }
        needToUpdate = false;
        bufferGraphics.dispose();
        //Draw it to canvas
        Graphics g2 = img.getGraphics();
        g2.drawImage(buffer, game.WIDTH/2-buffer.getWidth()/2, game.HEIGHT - buffer.getHeight()*2, null);
        g2.dispose();

    }

    public boolean remove(int id)
    {
        Item item;
        needToUpdate = true;
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
        needToUpdate = true;
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
