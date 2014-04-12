package asteroids;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame window = new JFrame("Asteroids: The Clone");
        Asteroids game = new Asteroids();

        window.add(game);
        window.addKeyListener(game);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
