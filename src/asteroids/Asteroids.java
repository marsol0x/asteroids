package asteroids;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
    private ParticleGenerator pGenerator;
    private BulletGenerator bGenerator;
    private boolean running;
    private Integer score;
    private int lives;
    private String livesStr;

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
        satellites = new Vector<>();

        player = getNewPlayer();
        pGenerator = ParticleGenerator.getInstance();
        bGenerator = BulletGenerator.getInstance();
        running = true;
        score = 0;
        lives = 3;
        setLivesString();
    }

    private Ship getNewPlayer() {
        return new Ship(getPreferredSize().getWidth() / 2, getPreferredSize().getHeight() / 2);
    }

    public void startGame() { gameLoop(); }

    private void gameLoop() {
        while(running) {
            long start = System.nanoTime();

            synchronized(this) { tick(); }
            // Repaint screen
            repaint();

            long total = System.nanoTime() - start;
            if ((total / 1000000) < (1000 / MAX_FPS)) {
                try { Thread.sleep((1000 / MAX_FPS) - (total / 1000000)); }
                catch(InterruptedException e) {}
            }
        }
    }

    private void tick() {
        // Move game entities
        player.move();
        for (Satellite s : satellites) { s.move(); }
        pGenerator.tick();
        bGenerator.tick();

        // Check for collisions
        Vector<Satellite> deadSatellites = new Vector<>();
        Vector<Satellite> newSatellites = new Vector<>();
        for (BulletGenerator.Bullet b : bGenerator.getBullets()) {
            for (Satellite s : satellites) {
                if (b.collided(s)) {
                    pGenerator.generateExplosion(s);
                    deadSatellites.add(s);
                    b.kill();

                    // Score!
                    score += s.getSides() * 10;

                    // Remove a side until it's a triangle
                    if (s.getSides() > 3) {
                        newSatellites.add(new Satellite(s, b));
                        newSatellites.add(new Satellite(s, b));
                    }
                }
            }
        }
        satellites.removeAll(deadSatellites);
        satellites.addAll(newSatellites);
        bGenerator.cullBullets();

        for (Satellite s : satellites) {
            if (!player.isDead() && s.collided(player)) {
                player.kill();
                lives--;
                setLivesString();
            }
        }
    }

    private void setLivesString() {
        livesStr = "";
        for (int i = 0; i < lives; i++) {
            livesStr += "* ";
        }
    }

    private void drawScoreboard(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.drawString(score.toString(), 10, 20);
        g.drawString(livesStr, 10, 35);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        player.draw(g2);
        for (Satellite s : satellites) { s.draw(g2); }
        pGenerator.drawParticles(g2);
        bGenerator.drawBullets(g2);

        drawScoreboard(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized(this) { player.keyPressed(e); }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        synchronized(this) { player.keyReleased(e); }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
