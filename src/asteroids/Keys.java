package asteroids;

import java.awt.event.KeyEvent;

enum Keys {
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT),
    NONE(-1);

    public int key;

    private Keys(int key) { this.key = key; }
}
