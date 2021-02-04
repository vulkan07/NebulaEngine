package me.Barni;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public boolean[] pressedKeys;
    public int pressedNum = 1;

    public KeyManager() {pressedKeys = new boolean[256];}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Pressed: "+e.getKeyCode());

        try {
            pressedNum = Integer.parseInt(String.valueOf(e.getKeyChar()));
        } catch (NumberFormatException ex) {}
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
    }
}
