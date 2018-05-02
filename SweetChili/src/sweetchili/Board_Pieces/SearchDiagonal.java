package sweetchili.Board_Pieces;

/**
 * Viisto hakualgoritmi.
 */
public interface SearchDiagonal {

    /**
     *Hakee viistosti tarkastaa onko from ja to välillä tyhjää kaistaa. 
     */
    boolean searchDiagonal(Move from, Move to, Piece board[][], int yDelta, int xDelta);
}
