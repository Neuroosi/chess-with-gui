package sweetchili.Board_Pieces;

/**
 * Abstrakti luokka, joka on antaa yleiset metodit joita kaikki nappulat tarvitsevat.
*/
abstract public class Piece {

    protected Team side;
    /**
     * 
     * @param side Väri johon nappula kuuluu. Näkyvyys aliluokille. 
     */
    public Piece(Team side) {
        this.side = side;
    }

    /**
     * Metodi, joka antaa nappulan värin
     * @return side Palauttaa nappulan värin 
     */
    
    public Team getTeam() {
        return side;
    }

    /**
     * Tarkastaa siirronlaillusuuden. Tarkka kuvaus toteuttavassa luokassa, koska nappulat liikkuvat eri tavalla.
     * @param from  Mistä ruudusta lähdetään liikkeelle.
     * @param to    Mihin ruutuun halutaan liikkua.
     * @param board Kaksiulotteinen taulukko, johon pelitilanne on tallennettu.
     * @return      Palauttaa totuusarvon siitä onko siirtolaillinen eli voidaanko se tehdä.
     */
    public abstract boolean checkLegality(Move from, Move to, Piece board[][]);

}
