package sweetchili.Board_Pieces;

/**
 * Kuvastaa sijaintia pelilaudalla.
 * @author joel
 */

public class Move {
    /**
     * Attribuutit y ja x asetetaan konstruktorissa. Kuvaa koordinaatteja.
     */
    private int y;
    private int x;
  
    public Move(int y, int x) {
        this.y = y;
        this.x = x;
    }

    /**
     * Palauttaa y-koordinaatin
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Palauttaa x-koordinaatin
     * @return 
     */
    public int getX() {
        return x;
    }
    

    /**
     * Kertoo onko sijainnit samat oliossa.
     * @param o
     * @return sijannit samat 
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        Move v = (Move) o;
        return v.getY() == this.y && v.getX() == this.x;
    }
}
