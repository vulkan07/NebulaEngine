package me.Barni;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Item {

    final String prefix = "C:\\Dev\\Item\\";
    final String suffix = ".png";
    public static final String[] txtName = {null,"stick", "pebble", "seed","pickaxe","axe","hoe"};

    public static final int EMPTY = 0;
    public static final int STICK = 1;
    public static final int PEBBLE = 2;
    public static final int SEED = 3;
    public static final int PICKAXE = 4;
    public static final int AXE = 5;
    public static final int HOE = 6;

    public static final int[][] PICKAXE_BREAKS = { {4,2},{5,4},{6,2},{7,2} }; //[ON WHAT] [GIVE WHAT]
    public static final int[][] AXE_BREAKS = { {2, 3} };                      //Same as pick
    public static final int[] HOE_AFFECTS_ON = { 1 };                         //GROUND -> FARM

    public BufferedImage texture;
    public int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        count = 1;
        if (id == 0) return;
        try {
            this.texture = ImageIO.read(new File(prefix+txtName[id]+suffix));
        } catch (IOException e) {
            System.out.println("Can't load item texture: "+prefix+txtName[id]+suffix);
        }
    }

    private int id;

    public Item(int id)
    {
        this.id = id;
        if (id == 0) return;
        try {
            this.texture = ImageIO.read(new File(prefix+txtName[id]+suffix));
        } catch (IOException e) {
            System.out.println("Can't load item texture: "+prefix+txtName[id]+suffix);
        }
    }

}
