package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Asteroids extends JPanel implements KeyListener {
    public static final long serialVersionUID = 455;

    private Ship player;

    public Asteroids() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500, 500));

        player = new Ship((int) getPreferredSize().getWidth() / 2, (int) getPreferredSize().getHeight() / 2);
    }

    public void paint(Graphics g) {
        super.paint(g);
        player.draw((Graphics2D) g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
