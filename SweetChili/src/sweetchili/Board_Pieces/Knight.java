package sweetchili.Board_Pieces;

/**
 * Ratsu luokan toteutus perii luokan Piece. Toteuttaa rajapinnan CheckBlock.
 *
 * @author joel
 */
public class Knight extends Piece implements CheckBlock {

    public Knight(Team side) {
        super(side);
    }

    /**
     * Piece yliluokan abstraktin metodin toteutus, jonka tarkoitus on selvittää
     * siirronlaillisuus Taulukoihin yDeltaD ja xDeltaD on tallennettu suunnat,
     * joihin ratsu voi liikkua. Kutsuu rajapinnan checkBlock toteutusta possibleBlock. Loopissa käydään läpi kaikki suunnat
     * johon ratsu voi liikkua. Mahdollinen sijainti määritellaan fromin koordinaattien ja mahdollisten suuntien kanssa joihin voidaan liikkua.
     * Jos to suunta on sama jonkun sijainnin kanssa niin siirto voidaan toteuttaa, eikä kohde ruudussa ole vastustajan kuningasta tai omaa nappulaa.
     */
    @Override
    public boolean checkLegality(Move from, Move to, Piece[][] board) {
        int yDelta[] = {-1, -1, 1, 1, -2, -2, 2, 2};
        int xDelta[] = {-2, 2, -2, 2, -1, 1, -1, 1};
        for (int i = 0; i < yDelta.length; i++) {
            int y = from.getY() + yDelta[i];
            int x = from.getX() + xDelta[i];
            if (to.equals(new Move(y, x)) && !possibleBlock(board[y][x])) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Tarkastaa ettei ruudussa ole vastustajan kuningasta tai omaa nappulaa, muuten siirto voidaan toteuttaa.
     */
    @Override
    public boolean possibleBlock(Piece block) {
        if (block == null) {
            return false;
        }
        if (block.side != side && !(block instanceof King)) {
            return false;
        } else if (block.side == side || block instanceof King) {
            return true;
        }
        return false;
    }

}
