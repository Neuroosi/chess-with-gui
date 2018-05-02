package sweetchili.Board_Pieces;

/**
 * Hakualgoritmi joka hakee pelilaudalta horisontaalisesti.
 */
public interface SearchHorizontal {

    /**
     * Tarkastaa onko from ja to välillä yhteys. Eli ei hyppää kuningattaren ja tornin tapauksessa nappulan yli.
     * Sekä liikkuu sallittuun suuntaan. Kuninkaan tapauksessa sovelletaan uhan etsimiseksi.
     * @param from
     * @param to
     * @param board
     * @param yDelta
     * @param xDelta
     * @return 
     */
    boolean searchHorizontal(Move from, Move to, Piece board[][], int yDelta, int xDelta);
    
}
