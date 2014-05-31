package asteroids;

class Controller {
    private Keys key;
    private int tickNum;

    public Controller() {
        key = Keys.NONE;
    }

    public void setKey(Keys key) { this.key = key; }
    public void clearKey() { key = Keys.NONE; }
    public final Keys getKey() { return key; }
}
