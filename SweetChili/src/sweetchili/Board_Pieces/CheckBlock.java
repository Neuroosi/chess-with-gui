package sweetchili.Board_Pieces;

/**
 *Rajapinta, joka huolehtii olion tarkastamisesta, että onko se este.
 * @author joel
 */
public interface CheckBlock {

    
    /**
     * Saa parametrina Piece olion ja tarkastaa onko se este.
     * @param block
     * @return onko este
     */
    boolean possibleBlock(Piece block);
}
