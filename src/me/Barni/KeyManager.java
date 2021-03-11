package me.Barni;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public boolean[] pressedKeys;

    public final int KEY_UP = 87;
    public final int KEY_DOWN = 83;
    public final int KEY_LEFT = 65;
    public final int KEY_RIGHT = 68;
    public final int KEY_SPACE = 32;
    public final int KEY_ESC = 27;

    public KeyManager() {pressedKeys = new boolean[256];}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Pressed: "+e.getKeyCode());
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }
}
