package sweetchili.LoadSavegame;

import java.io.FileWriter;
import java.io.IOException;
import sweetchili.Board_Pieces.Bishop;
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Pawn;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 * Luokka huolehtii pelin tallennuksesta tiedostoon.
 * @author joel
 */
public class Savegame {

    private String path;
    private Piece game[][];
    private boolean whitemove;
    private String ac;

    public Savegame(String path, Piece[][] game, boolean whitemove) {
        this.path = path;
        this.game = game;
        this.ac = "";
        this.whitemove = whitemove;
    }

    /**
     * Kirjoittaa pelitilanteen muistiin string muodossa.
     * @throws IOException 
     */
    public void writeFile() throws IOException {
        FileWriter w = new FileWriter(path);
        String g = gamestateToString(game);
        w.write(g);
        w.write(ac);
        w.close();
    }

    
    /**
     * Muuntaa pelitilanteen string muotoon.
     * @param g
     * @return 
     */
    public String gamestateToString(Piece g[][]) {
        String state = "";
        state += whiteTurn();
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g.length; j++) {
                state += value(g[i][j]);
                storeActivity(i, j, g[i][j]);
            }
            state += "\n";
        }
        return state;
    }

    /**
     * Selvittää onko valkoisen vuoro.
     */
    public String whiteTurn() {
        if (whitemove) {
            return "true\n";
        }
        return "false\n";
    }

    /**
     * Muuntaa piece oliosta oikean arvon eli kirjaimen tiedostoon, joka kuvaa nappulaa.
     * @param square
     * @return 
     */
    public String value(Piece square) {
        if (square == null) {
            return "_";
        }
        if (square instanceof Queen) {
            return side(square.getTeam(), "Q");
        } else if (square instanceof King) {
            return side(square.getTeam(), "K");
        } else if (square instanceof Rook) {
            return side(square.getTeam(), "R");
        } else if (square instanceof Bishop) {
            return side(square.getTeam(), "B");
        } else if (square instanceof Knight) {
            return side(square.getTeam(), "N");
        } else {
            return side(square.getTeam(), "P");
        }
    }

    
    /**
     * Selvittää kummalle puolelle nappula kuuluu.
     * @param side
     * @param s
     * @return 
     */
    public String side(Team side, String s) {
        if (side == Team.White) {
            return s.toUpperCase();
        }
        return s.toLowerCase();
    }

    /**
     * Tallentaa aktiivisuuden string muotoon.
     * @param y
     * @param x
     * @param p 
     */
    public void storeActivity(int y, int x, Piece p) {
        if (p instanceof King) {
            ac += y + "," + x + "," + moveLeg(((King) p).kingMoved()) + "\n";
        } else if (p instanceof Rook) {
            ac += y + "," + x + "," + moveLeg(((Rook) p).rookMoved()) + "\n";
        } else if (p instanceof Pawn) {
            ac += y + "," + x + "," + moveLeg(((Pawn) p).active()) + "," + moveLeg(((Pawn) p).enPassantMove()) + "\n";
        }
    }

    /**
     * Muuntaa aktiivisuuden muodosta boolean muotoon string.
     * @param move
     * @return 
     */
    public String moveLeg(boolean move) {
        if (move) {
            return "true";
        }
        return "false";
    }

}
