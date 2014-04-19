package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.InterruptedException;
import java.util.Vector;
import javax.swing.JPanel;

public class Asteroids extends JPanel implements KeyListener {
    public static final long serialVersionUID = 455;

    private static final long MAX_FPS = 30;
    private static final int w = 500;
    private static final int h = 500;
    private static Asteroids instance = null;

    private Ship player;
    private Vector<Satellite> satellites;
    private boolean running;

    public Asteroids() {
        // Use a static instance so that we can check state
        if (Asteroids.instance == null) {
            Asteroids.instance = this;
        }

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(w, h));

        newGame();
    }

    public static Asteroids getInstance() {
        return instance;
    }

    private void newGame() {
        satellites = new Vector<Satellite>();

        player = new Ship(getPreferredSize().getWidth() / 2, getPreferredSize().getHeight() / 2);
        satellites.add(new Satellite(10, 10));
        running = true;
    }

    public void startGame() { gameLoop(); }

    private void gameLoop() {
        while(running) {
            long start = System.nanoTime();

            player.move();
            for (Satellite s : satellites) { s.move(); }
            repaint();

            long total = System.nanoTime() - start;
            if ((total / 1000000) < (1000 / MAX_FPS)) {
                try { Thread.sleep((1000 / MAX_FPS) - (total / 1000000)); }
                catch(InterruptedException e) {}
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        player.draw(g2);
        for (Satellite s : satellites) { s.draw(g2); }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
