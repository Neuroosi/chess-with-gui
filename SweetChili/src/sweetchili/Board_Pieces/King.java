package sweetchili.Board_Pieces;

/**
 * Kuningas luokan toteutus, perii abstraktin luokan Piece. Toteuttaa rajapinnat
 * SearchDiagonal ja SearchHorizontal. Luokalla on attribuutit kY ja kX, jotka kertoo kuninkaan sijainnin laudalla.
 * Boolean moved linnoittautumista varten.
 *
 * @author joel
 */
public class King extends Piece implements SearchDiagonal, SearchHorizontal {

    private int kY;
    private int kX;
    private boolean moved;

    public King(Team t) {
        super(t);
    }

    /** addY()
     * Vaihtaa attribuutin kY arvoksi parametrin y.
     *
     * @param y
     */
    public void addY(int y) {
        this.kY = y;
    }

    /** addX()
     * Vaihtaa attribuutin kX arvoksi parametrin x.
     * @param x
     */
    public void addX(int x) {
        this.kX = x;
    }

    /** firstMoveDone()
     * Kun kunigasta siirretään ensimmäisen kerran niin moved atribuuttiin asetetaan true.
     */
    
    public void firstMoveDone() {
        moved = true;
    }
    
    /** kingMoved()
     * Palauttaa tiedon siitä onko kuningasta siirretty.
     * @return moved
     */

    public boolean kingMoved() {
        return moved;
    }
    
    /** setActivity()
     * Kun peli ladataan muistista niin ac arvoon on tallennettu tieto siitä oliko kunigasta siirrety pelissä.
     * @param ac 
     */

    public void setActivity(boolean ac) {
        moved = ac;
    }

    /**
     * Piece luokan abstraktin metodin toteutus. Tarkastaa voiko kuningas liikkua fromista pisteeseen to.
     * Kuningas voi liikkua yhden ruudun mihin tahansa suuntaan. Jos pisteiden erotuksesta tulee suurempi kuin
     * yksi niin koitetaan hypätä liian monta ruutua ja metodi palauttaa false. Jos näin ei ole sitten täytyy tarkistaa, että
     * ruutua ei miehitä vastustajan kuningas tai oma nappula. Jos näin on niin siirtoa ei voi toteuttaa. Lopuksi
     * päivitetään kuninkaan sijainti. Metodi ei tarkasta tuleeko to ruutun uhkaa jostain muusta ruudusta. Se tarkastetaan aina siirron jälkeen 
     * kun Game luokassa vaihdetaan vuoroa. Siirto perutaan, jos to ruudussa on uhka.
     * @param from
     * @param to
     * @param board
     * @return 
     */
    @Override
    public boolean checkLegality(Move from, Move to, Piece board[][]) {
        if ((to.getY() < 0 || to.getY() > 7) || (to.getX() < 0 || to.getX() > 7)) {
            return false;
        }
        if (Math.abs(from.getY() - to.getY()) > 1 || Math.abs(from.getX() - to.getX()) > 1) {
            return false;
        }
        if (board[to.getY()][to.getX()] != null) {
            if (board[to.getY()][to.getX()].side == this.side || board[to.getY()][to.getX()] instanceof King) {
                return false;
            }
        }
        addY(to.getY());
        addX(to.getX());
        return true;
    }

    
    /**
     * Rajapinnan SearchDiagonal metodin toteutus, jonka tarkoitus on selvittää uhkaako kuningatar tai lähetti ruutua, jossa kuningas on.
     * Muuttujiin yDelta ja xDelta on tallennettu suunnat, joista uhka voi tulla. Metodi selvittää onko pisteiden välillä esteetön linja.
     * Mikä tahansa muu nappula, joka on linjan välissä torjuu uhan.
     */
    @Override
    public boolean searchDiagonal(Move from, Move to, Piece[][] board, int yDelta, int xDelta) {
        for (int i = from.getY() + yDelta, j = from.getX() + xDelta; i >= 0 && i < board.length && j >= 0 && j < board.length; i += yDelta, j += xDelta) {
            if (checkThreat(board[i][j], true, new Move(i, j))) {
                return true;
            } else if (board[i][j] != null) {
                return false;
            }
        }
        return false;
    }

    /**
     * Rajapinnan SearchHorizontal metodin toteutus. Sama idea kun SearchDiagonal toteutuksessa, mutta etsitään horisontaalisia linjoja pitkin.
     * Ensimmäinen for-loop tarkastaa tuleeko uhkaa y-akselilta. Jos uhka tulee niin voidaan palauttaa true, mutta jos ei tule ja ruutu ei ole tyhjä (eli jokin torjuu uhan)
     * niin ei voida palauttaa false, koska x-akseli on tarkastamatta, joten poistutaan loopista ja mennään for-looppiin, joka tarkastaa x-akselin. Sama idea kuin ensimmäisessä for-loopissa paitsi voidaan poistua heti, jos
     * jokin torjuu uhan, koska ei ole enää linjoja tarkastettavana ja tämä for-loop etsii uhkaa x-akselilta. 
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
            if (checkThreat(board[y][from.getX()], false, new Move(y, from.getX()))) {
                return true;
            } else if (board[y][from.getX()] != null) {
                break;
            }
        }
        for (int x = from.getX() + xDelta; x >= 0 && x < board.length; x += xDelta) {
            if (checkThreat(board[from.getY()][x], false, new Move(from.getY(), x))) {
                return true;
            } else if (board[from.getY()][x] != null) {
                return false;
            }
        }
        return false;
    }

    /**
     * checkSafety tarkastaa onko kuningas turvassa eli ei ole uhattuna. Ensimmäiseksi tarkastetaan tuleeko uhka ratsulta. yDeltaD ja xDeltaD taulukoihin on tallennettu suunnat, joista uhka voi tulla ja ne käydään läpi.
     * Sen jälkeen etsitään mahdolliset uhat viistosta eli kutsutaan rajapinnan SearchDiagonal metodin toteutusta searchDiagonal. Jos searchDiagonal palauttaa true
     * niin viistosta tulee uhka. Jos uhkaa ei tule niin sen jälkeen tarkastetaan uhkaako vastapuolen kuningas mahdollisesti ruutua viistosta.
     * Viimeisenä tarkastetaan horisontaaliset linjat. yDeltaH ja xDeltaH taulukoihin on tallennettu suunnat joista uhka voi tulla. Ne käydään läpi kutsuen rajapinnan SearchHorizontal metodin toteutusta. Jos se palauttaa true niin uhka tulee.
     * Muuten katsotaan uhkaako kuningas joltain horisontaaliselta linjalta.
     */
    public boolean checkSafety(Piece[][] b) {
        if (knightThreat(kY - 1, kX - 2, b) || knightThreat(kY - 1, kX + 2, b) || knightThreat(kY + 1, kX - 2, b) || knightThreat(kY + 1, kX + 2, b) || knightThreat(kY - 2, kX - 1, b) || knightThreat(kY - 2, kX + 1, b) || knightThreat(kY + 2, kX - 1, b) || knightThreat(kY + 2, kX + 1, b)) {
            return false;
        }
        Move kingLc = new Move(kY, kX);
        int yDeltaD[] = {-1, -1, 1, 1};
        int xDeltaD[] = {1, -1, 1, -1};
        for (int i = 0; i < yDeltaD.length; i++) {
            if (searchDiagonal(kingLc, null, b, yDeltaD[i], xDeltaD[i])) {
                return false;
            } else if (kingThreat(kingLc.getY() + yDeltaD[i], kingLc.getX() + xDeltaD[i], b)) {
                return false;
            }
        }
        int yDeltaH[] = {1, -1};
        int xDeltaH[] = {-1, 1};
        for (int i = 0; i < yDeltaH.length; i++) {
            if (searchHorizontal(kingLc, null, b, yDeltaH[i], xDeltaH[i])) {
                return false;
            } else if (kingThreat(kingLc.getY() + yDeltaH[i], kingLc.getX(), b) || kingThreat(kingLc.getY(), kingLc.getX() + xDeltaH[i], b)) {
                return false;
            }
        }
        return true;
    }

     /** checkThreat()
      * Tarkastaa tuleeko ruudusta uhkaa. Diagonal totuusarvo on lähettiä ja sotilasta varten, koska ne uhkaavat viistosta.
      * Lisäksi sitä voidaan hyödyntää tornin uhassa, jos diagonal on false(Torni voi uhata kuningasta horisontaalisilta linjoilta). Move kertoo uhan sijainnin. Sitä tarvitaan sotilasta varten, koska se uhkaa vain yhden
      * ylös ja vasemmalle/oikealle. Jos ruutu on tyhjä tai nappula on samalla puolella kuin kuningas niin palautetaan false eli uhkaa ei tule. Muuten tarkastetaan threat olion tyyppi ja hyödynnetään saatuja parametreja uhan arvionnissa.
      * Kuningattareen sitä ei tarvita, koska se uhkaa mistä tahansa suunnasta. Tornin kohdalla tarvitsee tarkastaa ettei uhka tule viistosta, koska torni voi vain uhata horisontaalisia linjoja pitkin. 
      * Lähetin kohdalla tarkastetaan, että sen linja on viistosta, koska se ei voi vuorostaan uhata horisontaalisesti. 
      * Sotilaan kohdalla täytynee tarkastaa, että onko kuninkaan ruutu sotilaan hyökkäämään suuntaan eli yksi eteen ja yksi vasemmalle tai oikealle. Lisäksi uhan täytyy tulla viistosta. 
      */
    private boolean checkThreat(Piece threat, boolean diagonal, Move c) {
        if (threat == null || threat.side == side) {
            return false;
        }
        if (threat instanceof Queen) {
            return true;
        }
        if (threat instanceof Rook && !diagonal) {
            return true;
        }
        if (threat instanceof Bishop && diagonal) {
            return true;
        }
        if (threat.side == Team.Black) {
            return threat instanceof Pawn && diagonal && Math.abs(kY - c.getY()) == 1 && kY > c.getY();
        } else {
            return threat instanceof Pawn && diagonal && Math.abs(kY - c.getY()) == 1 && kY < c.getY();
        }
    }

    /**
     * Tarkastaa uhkaako vastapuolen kuningas ruudusta [y][x]. Jos ruutu on tyhjä palautetaan false.
     * @param y
     * @param x
     * @param b
     * @return uhkaako kuningas
     */
    private boolean kingThreat(int y, int x, Piece b[][]) {
        if (y < 0 || x < 0 || y > b.length - 1 || x > b.length - 1) {
            return false;
        }
        if (b[y][x] == null) {
            return false;
        }
        return b[y][x] instanceof King && b[y][x].side != side;
    }

    /**
     * Tarkastaa uhkaako vastapuolen ratsu [y][×] ruutua.
     */
    
    private boolean knightThreat(int y, int x, Piece b[][]) {
        if (y < 0 || x < 0 || y > b.length - 1 || x > b.length - 1) {
            return false;
        }
        if (b[y][x] == null) {
            return false;
        }
        return b[y][x] instanceof Knight && b[y][x].side != side;
    }
    
    /**
     * Tarkastaa onko linnoittaumuminen mahdollista. Tarkastaa kummalle puolelle halutaan linnoittautua. 
     * @param from
     * @param to
     * @param board
     * @return 
     */

    public boolean isCastlingPossible(Move from, Move to, Piece board[][]) {
        if (Team.White == side && from.equals(new Move(7, 4)) && (to.equals(new Move(7, 6)) || to.equals(new Move(7, 2)))) {
            return castling(board, from, to);
        } else if (Team.Black == side && from.equals(new Move(0, 4)) && (to.equals(new Move(0, 6)) || to.equals(new Move(0, 2)))) {
            return castling(board, from, to);
        }
        return false;
    }
    
    /**
     * Tarkastaa ettei ruutuihin tule uhkaa, jotka kuningas ylittää tai päätyy linnoittautumisessa. Alussa tarkastetaan ollaanko kuningasta siirretty tai puolen tornia jolle linnoittaudutaan, jos on niin linnoittautumista ei voida tehdä.
     * Jos linnoittaudutaan kuninkaan puolelle niin delta on 1, koska kuninkaan tulee liikkua kaksi ruutua eteenpäin ja ruudut täytyy tarkastaa. Muuten delta on -1 eli linnoittaudutaan kuningattaren puolelle. Eli mennään taaksepäin.
     * Loopissa kuninkaan sijaintia päivitetään addX() metodin avulla. Lopussa se resetataan. Ehto tarkastaa ettei ruutun tule uhkaa tai ettei siinä ole linjaa blockkaavaa nappulaa. 
     * Jotta linnoittautuminen onnistuu niin tornin ja kuninkaan välillä tulee olla tyhjä linja.
     * @param b
     * @param from
     * @param to
     * @return 
     */
    public boolean castling(Piece b[][], Move from, Move to) {
        if (moved || !checkRook(b, to)) {
            return false;
        }
        int delta;
        if (to.getX() == 6) {
            delta = 1;
        } else {
            delta = -1;
        }
        for (int i = from.getX() + delta; i >= 2 && i <= 6; i += delta) {
            addX(i);
            if (!checkSafety(b) || b[from.getY()][i] != null) {
                return false;
            }
        }
        addX(from.getX());
        if (delta == -1) {
            return b[from.getY()][1] == null;
        }
        return true;
    }

    
    /**
     * Tarkastaa onko tornia liikutettu puolelta, johon linnoittaudutaan.
     */
    public boolean checkRook(Piece b[][], Move to) {
        if (Team.White == side) {
            if (to.getX() == 6 && !((Rook) b[7][7]).rookMoved()) {
                return true;
            } else if (to.getX() == 2 && !((Rook) b[7][0]).rookMoved()) {
                return true;
            }
        } else if (Team.Black == side) {
            if (to.getX() == 6 && !((Rook) b[0][7]).rookMoved()) {
                return true;
            } else if (to.getX() == 2 && !((Rook) b[0][0]).rookMoved()) {
                return true;
            }
        }
        return false;
    }
}
