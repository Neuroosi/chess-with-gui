package sweetchili.Board_Pieces;

/**
 * Kuningatar luokan toteutus. Perii luokan piece ja toteuttaa rajapinnat
 * SearchDiagonal, SearchHorizonta ja CheckBlock. Tarkemmat tiedot omissa
 * tiedostoissa.
 *
 * @author joel
 */
public class Queen extends Piece implements SearchDiagonal, SearchHorizontal, CheckBlock {

    public Queen(Team t) {
        super(t);
    }

    /**
     * Piece yliluokan abstraktin metodin toteutus, jonka tarkoitus on selvittää
     * siirronlaillisuus Taulukoihin yDeltaD,xDeltaD, yDeltaH ja xDeltaH on
     * tallennettu suunnat, joihin kuningatar voi liikkua. Kutsuu rajapinnan
     * searchDiagonal toteutusta ja SearchHorizontal metodin toteutusta, joka
     * selvittää onko pisteiden välillä esteetön linja. Ensiksi tarkastetaan
     * viistot linjat ja jos siirto löytyy niin palautetaan true ja toteutetaan
     * siirto. Muuten jatketaan eteepäin ja lopuksi uusi looppi, jossa
     * tarkastetaan horisontaaliset linjat samalla logiikalla.
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
        int yDeltaH[] = {1, -1};
        int xDeltaH[] = {-1, 1};
        for (int i = 0; i < yDeltaH.length; i++) {
            if (searchHorizontal(from, to, board, yDeltaH[i], xDeltaH[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Rajapinnan SearchDiagonal metodin toteutus, jonka tarkoitus on etsiä
     * mahdollista ruutua, johon pelaaja haluaa liikkua. Muuttujiin yDelta ja
     * xDelta on tallennettu suunnat, joihin kuningatar voi liikkua. Metodi
     * selvittää onko pisteiden välillä esteetön linja. Jos ei ole niin siirtoa
     * ei voida toteuttaa.
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
     * Rajapinnan SearchHorizontal metodin toteutus. Sama idea kun
     * SearchDiagonal toteutuksessa, mutta etsitään horisontaalisia linjoja
     * pitkin. Ensimmäinen for-loop tarkastaa mahdolliset ruudut joihin voi
     * liikkua y-akselilla. Jos ruutu löytyy niin voidaan palauttaa true, mutta
     * jos ei löydy ja ruutu ei ole tyhjä (eli jokin blockkaa linjan) niin ei
     * voida palauttaa false, koska x-akseli on tarkastamatta, joten poistutaan
     * loopista ja mennään viimeiseen for-looppiin, joka tarkastaa x-akselin.
     * Sama idea kuin ensimmäisessä for-loopissa paitsi voidaan poistua heti,
     * jos mahdollinen siirto löytyy, koska ei ole enää linjoja tarkastettavana
     * ja tämä for-loop etsii mahdollista siirtoa x-akselilta.
     *
     * @param from
     * @param to
     * @param board
     * @param yDelta
     * @param xDelta
     * @return
     */
    @Override
    public boolean searchHorizontal(Move from, Move to, Piece[][] board, int yDelta, int xDelta) {
        for (int y = from.getY() + yDelta; y >= 0 && y < board.length; y += yDelta) {
            Piece p = board[y][from.getX()];
            if (to.equals(new Move(y, from.getX())) && !possibleBlock(p)) {
                return true;
            } else if (p != null) {
                break;
            }
        }
        for (int x = from.getX() + xDelta; x >= 0 && x < board.length; x += xDelta) {
            Piece p = board[from.getY()][x];
            if (to.equals(new Move(from.getY(), x)) && !possibleBlock(p)) {
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
