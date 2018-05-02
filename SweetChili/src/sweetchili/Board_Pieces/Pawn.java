package sweetchili.Board_Pieces;

/**
 * Sotilas luokan toteutus. Perii luokan piece.
 *
 * @author joel
 */
public class Pawn extends Piece {

    /**
     * Attribuutti active kertoo onko sotilasta jo liikutettu. Enpassant kertoo
     * voidaanko ohestalyönti suorittaa tähän sotilaaseen.
     */
    private boolean active = false;
    private boolean enPassant = false;

    public Pawn(Team side) {
        super(side);
    }

    /**
     * Hyppää yhden ruudun yli. Nyt voidaan suorittaan ohestalyönti.
     */
    public void enPassantTrue() {
        enPassant = true;
    }

    /**
     * Asettaa sotilaan tilan aktiiviseksi eli sitä on jo liikutettu.
     */
    public void stateActive() {
        active = true;
    }

    /**
     * Kertoo voidaan ohestalyönti tehdä tähän sotilaaseen.
     *
     * @return enPassant
     */
    public boolean enPassantMove() {
        return enPassant;
    }

    /**
     * Onko sotilas jo aktiivinen.
     *
     * @return
     */
    public boolean active() {
        return active;
    }

    /**
     * Jos ohestalyöntiä ei suoriteta heti seuraavalla siirrolla niin sitä ei
     * voida tehdä enää. Joten asetaan arvoksi false
     */
    public void enPassantFalse() {
        enPassant = false;
    }

    /**
     * Kun ladataan pelitiedostosta niin ac kuvaa voidaanko tähän sotilaaseen
     * tehdä ohestalyönti.
     *
     * @param ac
     */
    public void setEnpassant(boolean ac) {
        enPassant = ac;
    }

    /**
     * Kun ladataan pelitiedostosta niin ac kuvaa onko nappulaa jo liikutettu.
     *
     * @param ac
     */
    public void setActivity(boolean ac) {
        active = ac;
    }

    /**
     * Piece yliluokan abstraktin metodin toteutus, jonka tarkoitus on selvittää
     * siirronlaillisuus. Muuttujiin stepY ja stepX askelten määrä, jotka
     * sotilas ottaa. Kutsutaan capture metodia, jos se onnistuu niin
     * palautetaan true eli siirto on laillinen. Muuten selvitetään sotilaan
     * puoli. Jos sotilas on hyppäämässä yhden ruudun yli niin tarkastetaan
     * ettei se ole jo aktiivisena. Jos on niin silloin hyppäystä ei voida
     * suorittaa. Lisäksi tulee tarkastaa, että ruutu on tyhjä, joka on kohde
     * ruutuna ja sotilaan tulee myös liikkua oikeaan suuntaan. Seuraavaksi
     * tarkastetaan jos sotilas liikkuu pelkästään yhden ruudun eteenpäin.
     * Sotilas asetetaan aktiiviseksi kun vuoroa vaihdetaan. Silloin sotilas ei
     * pysty hyppäämään ruudun yli jos se on jo aktiivisena.
     */
    @Override
    public boolean checkLegality(Move from, Move to, Piece[][] board) {
        int stepY = Math.abs(from.getY() - to.getY());
        int stepX = Math.abs(from.getX() - to.getX());
        if (capture(stepY, stepX, board, from, to)) {
            return true;
        }
        if (this.side == Team.Black) {
            if (!active && stepY == 2 && stepX == 0 && board[to.getY()][to.getX()] == null && to.getY() > from.getY()) {
                enPassantTrue();
                return true;
            }
            return stepY == 1 && stepX == 0 && board[to.getY()][to.getX()] == null && to.getY() > from.getY();
        }
        if (!active && stepY == 2 && stepX == 0 && board[to.getY()][to.getX()] == null && to.getY() < from.getY()) {
            enPassantTrue();
            return true;
        }
        return stepY == 1 && stepX == 0 && board[to.getY()][to.getX()] == null && to.getY() < from.getY();
    }

    /**
     * Tarkastetaan voidaanko ruudussa oleva nappula kaapata. Eli jos ruutu on
     * tyhjä niin luonnillisesti palautetaan false. Muuten selvitetään puoli ja
     * tarkastetaan, että sotilas liikkuu yhden eteen ja sivulle. Pitää myös
     * tarkastaa, että kaapattava nappula ei ole kuningas tai samalla puolella.
     * Lisäksi tarkastetaan, että sotilas liikkuu oikeaan suuntaan.
     *
     * @param stepY
     * @param stepX
     * @param board
     * @param from
     * @param to
     * @return
     */
    public boolean capture(int stepY, int stepX, Piece board[][], Move from, Move to) {
        if (board[to.getY()][to.getX()] == null) {
            return false;
        }
        if (this.side == Team.Black) {
            return (stepY == 1 && stepX == 1) && board[to.getY()][to.getX()].side != side && !(board[to.getY()][to.getX()] instanceof King) && to.getY() > from.getY();
        }
        return (stepY == 1 && stepX == 1) && board[to.getY()][to.getX()].side != side && !(board[to.getY()][to.getX()] instanceof King) && to.getY() < from.getY();
    }

    /**
     * Tarkastetaan voidaanko ohestalyönti tehdä. Ensiksi täytyy tarkastaa, että ruudussa on sotilas nappula ja sen täytyy olla eri puolella.
     * Sen jälkeen katsotaan, että haluttuun sotilaaseen voidaan tehdä ohestalyönti.
     */
    public boolean enPassantLegality(Move from, Move to, Piece[][] board) {
        if (((board[to.getY() + 1][to.getX()] == null || !(board[to.getY() + 1][to.getX()] instanceof Pawn)) && Team.White == board[from.getY()][from.getX()].getTeam()) || ((board[to.getY() - 1][to.getX()] == null || !(board[to.getY() - 1][to.getX()] instanceof Pawn)) && Team.Black == board[from.getY()][from.getX()].getTeam())) {
            return false;
        }
        if (Team.White == board[from.getY()][from.getX()].getTeam()) {
            return board[to.getY()][to.getX()] == null && ((Pawn) board[to.getY() + 1][to.getX()]).enPassantMove();
        }
        return board[to.getY()][to.getX()] == null && ((Pawn) board[to.getY() - 1][to.getX()]).enPassantMove();
    }

}
