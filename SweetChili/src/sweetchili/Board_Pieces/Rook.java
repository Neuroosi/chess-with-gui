package sweetchili.Board_Pieces;

/**
 * Torni luokan toteutus. Perii luokan piece ja toteuttaa rajapinnat
 * SearchHorizonta ja CheckBlock.
 *
 * @author joel
 */
public class Rook extends Piece implements SearchHorizontal, CheckBlock {

    /**
     * Attribuutti kertoo onko tornia siirretty.
     */
    private boolean moved;

    public Rook(Team side) {
        super(side);
    }

    /**
     * Tornia siirretään niin moved arvo muuttuu todeksi.
     */
    public void firstMoveDone() {
        moved = true;
    }

    /**
     * Palauttaa tiedon siitä onko tornia siirretty.
     *
     * @return
     */
    public boolean rookMoved() {
        return moved;
    }

    /**
     * Kun peliä ladataan tiedostosta niin ac kuvaa onko tornia liikutettu
     * pelissä.
     *
     * @param ac
     */
    public void setActivity(boolean ac) {
        moved = ac;
    }

    /**
     * Piece yliluokan abstraktin metodin toteutus, jonka tarkoitus on selvittää
     * siirronlaillisuus Taulukoihin yDeltaH ja xDeltaH on tallennettu suunnat,
     * joihin torni voi liikkua. Kutsuu rajapinnan SearchHorizontal metodin
     * toteutusta, joka selvittää onko pisteiden välillä esteetön linja.
     */
    @Override
    public boolean checkLegality(Move from, Move to, Piece[][] board) {
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
