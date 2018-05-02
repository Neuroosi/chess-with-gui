package sweetchili.Board_Pieces;

/**
 *Lähetti luokka. Perii abstraktin luokan piece ja toteuttaa rajapinnat SearchDiagonal ja CheckBlock. Rajapinnoista tarkempi kuvaus omissa tiedostoissa.
 * @author joel
 */
public class Bishop extends Piece implements SearchDiagonal, CheckBlock {

    public Bishop(Team side) {
        super(side);
    }

    /**
     * Piece yliluokan abstraktin metodin toteutus, jonka tarkoitus on selvittää siirronlaillisuus
     * Taulukoihin yDeltaD ja xDeltaD on tallennettu suunnat, joihin lähetti voi liikkua. Kutsuu rajapinnan searchDiagonal
     * toteutusta, joka selvittää onko pisteiden välillä esteetön linja.
     */
    
    @Override
    public boolean checkLegality(Move from, Move to, Piece[][] board) {
        int yDeltaD[] = {-1, -1, 1, 1};
        int xDeltaD[] = {1, -1, 1, -1};
        for (int i = 0; i < yDeltaD.length; i++) {
            if (searchDiagonal(from, to, board, yDeltaD[i], xDeltaD[i])) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Rajapinnan SearchDiagonal toteutus. Muuttuja i on y-koordinaatti ja j on x-koordinaatti.  Niitä kasvatetaan kierroksen päätteeksi yDelta ja xDelta muuttujien arvoilla.
     * Luodaan uusi Move-olio. Jos to-olion ja uuden move-olion koordinaatit ovat samat, eikä possibleBlock palauta true niin siirto on laillinen.
     */
    @Override
    public boolean searchDiagonal(Move from, Move to, Piece[][] board, int yDelta, int xDelta) {
        for (int i = from.getY() + yDelta, j = from.getX() + xDelta; i >= 0 && i < board.length && j >= 0 && j < board.length; i += yDelta, j += xDelta) {
            Piece p = board[i][j];
            if (to.equals(new Move(i, j)) && !possibleBlock(p)) {
                return true;
            } else if (p != null) {
                return false;
            }
        }
        return false;
    }

    /**
     * CheckBlock rajapinnan toteutus. Tarkastaa ettei linjaa peitä mikään.
     */
    
    @Override
    public boolean possibleBlock(Piece block) {
        if (block == null) {
            return false;
        }
        if ((block.side != side && !(block instanceof King))) {
            return false;
        } else if (block.side == side || block instanceof King) {
            return true;
        }
        return false;
    }
}
