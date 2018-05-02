package sweetchili.LoadSavegame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import sweetchili.Board_Pieces.Bishop;
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Pawn;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 *Luokka huolehtii pelin lataamisesta tiedostosta.
 * @author joel
 */
public class LoadGame {

    private boolean whitemove;
    private Piece state[][];
    private String path;

    public LoadGame(String path) {
        this.state = new Piece[8][8];
        this.path = path;
    }

    /**
     * Lataa pelin muistista. Kun nappulat on aseteltu niin sotilaaseen, kuninkaseen ja torniin tulee asettaa onko niitä liikutettu ja
     * sotilaihin totuusarvo siitä voidaanko ohestalyönti suorittaa. 
     * @throws FileNotFoundException 
     */
    public void loadGame() throws FileNotFoundException {
        File f = new File(path);
        Scanner sc = new Scanner(f);
        setTurn(sc.nextLine());
        int y = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (y < 8) {
                setRow(line, y);
                y++;
            } else {
                setActivity(line);
            }
        }
        sc.close();
    }

    /**
     * Asettaa valkoisen vuoron perustuen tiedostosta saatuun tietoon.
     * @param turn 
     */
    public void setTurn(String turn) {
        if (turn.equals("false")) {
            whitemove = false;
        } else {
            whitemove = true;
        }
    }

     /**
      * Asettaa nappulat riville
      * @param row
      * @param y 
      */
    public void setRow(String row, int y) {
        for (int x = 0; x < row.length(); x++) {
            String echo = "";
            echo += row.charAt(x);
            state[y][x] = getPieceType(echo);
            setKingLocation(y, x);
        }
    }

    /**
     * Selvittää nappulan värin, jos iso kirjain niin valkoinen jos pienin niin musta.
     * @param p
     * @return 
     */
    public Piece getPieceType(String p) {
        String h = p.toLowerCase();
        if (h.equals("q")) {
            return new Queen(getSide(p.charAt(0)));
        } else if (h.equals("k")) {
            return new King(getSide(p.charAt(0)));
        } else if (h.equals("r")) {
            return new Rook(getSide(p.charAt(0)));
        } else if (h.equals("b")) {
            return new Bishop(getSide(p.charAt(0)));
        } else if (h.equals("n")) {
            return new Knight(getSide(p.charAt(0)));
        } else if (h.equals("p")) {
            return new Pawn(getSide(p.charAt(0)));
        }
        return null;
    }

    /**
     * Asettaa sotilaalle, kuninkaalle tai tornille aktiivisuuden, jotta laittomaa linnoittautumista ei voida suorittaa tai ohestalyöntiä.
     */
    public void setActivity(String line) {
        String data[] = line.split(",");
        int y = Integer.parseInt(data[0]);
        int x = Integer.parseInt(data[1]);
        boolean activity = getActivity(data[2]);
        setData(y, x, activity);
        if (state[y][x] instanceof Pawn) {
            ((Pawn) state[y][x]).setEnpassant(getActivity(data[3]));
        }
    }

    /**
     * Asettaa tiedon nappulaan onko sitä liikutettu pelissä, joka on ruudussa [y][×]
     * @param y
     * @param x
     * @param activity 
     */
    public void setData(int y, int x, boolean activity) {
        Piece bu = state[y][x];
        if (bu instanceof King) {
            ((King) bu).setActivity(activity);
        } else if (bu instanceof Rook) {
            ((Rook) bu).setActivity(activity);
        } else if (bu instanceof Pawn) {
            ((Pawn) bu).setActivity(activity);
        }
    }

    /**
     * Lukee tiedostosta saadusta datasta onko nappulaa siirretty.
     * @param data
     * @return 
     */
    public boolean getActivity(String data) {
        if (data.equals("false")) {
            return false;
        }
        return true;
    }

    /**
     * Tarkastaa onko kyseessä iso vai pieni kirjain.
     * @param p
     * @return 
     */
    public Team getSide(char p) {
        if (p >= 65 && p <= 90) {
            return Team.White;
        }
        return Team.Black;
    }

    /**
     * Palauttaa pelitilanteen.
     * @return 
     */
    public Piece[][] getState() {
        return state;
    }

    
    /**
     * Palauttaa onko valkoisen vuoro.
     * @return 
     */
    public boolean whiteMove() {
        return whitemove;
    }

    /**
     * Asettaa kuninkaan sijainnin oliolle.
     * @param y
     * @param x 
     */
    public void setKingLocation(int y, int x) {
        if (state[y][x] instanceof King) {
            ((King) state[y][x]).addY(y);
            ((King) state[y][x]).addX(x);
        }
    }

}
