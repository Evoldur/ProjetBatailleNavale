package Metier;

/**
 *
 * @author Powerofcloud
 */
public class Coordonnees {
    private int x; //horizontal
    private int y; //vertical

    /**
     * Constructeur
     * @param x la ligne
     * @param y la colonne
     */
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
