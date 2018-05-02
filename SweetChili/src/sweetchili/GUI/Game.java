package sweetchili.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import sweetchili.Board_Pieces.Board;
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Pawn;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 * Game luokka, joka huolehtii vuoron hoitamisesta esim. jos valkoisen vuoro
 * niin odottaa, että valkoinen siirtää laillisen siirron ja sen jälkeen vaihtaa
 * vuoron mustalle. Toteuttaa rajapinnat keylistener ja mouselistener.
 * Attribuutteina gamestate, jossa on on pelintilanne. Draw, josta on pääsy
 * DrawGameState luokan metodeihin. Luokka vastaa piirtämisestä. B, joka on
 * board luokka. Vastaa uuden pelin luomisesta. Totuusarvo whiteMove, joka
 * kertoo onko valkoisen vai mustan vuoro. P muuttujaan tallennetaan nappula
 * ruudusta, josta se siirretään pois. PressedY vastaa y-koordinaattia ja
 * pressedX x-koordinaattia.
 *
 * @author joel
 */
public class Game implements MouseListener, KeyListener {

    private Piece gamestate[][];
    private DrawGameState draw;
    private Board b;
    private boolean whiteMove = true;
    private Piece p;
    private int pressedY;
    private int pressedX;

    public Game(Board b, DrawGameState g) {
        this.b = b;
        this.gamestate = b.getBoard();
        g.addMouseListener((MouseListener) this);
        g.addKeyListener((KeyListener) this);
        this.draw = g;
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    /**
     * MousePressed ottaa pelaajan klikkauksen ruudusta ja muuntaa hiiren
     * koordinaatit x- ja y-koordinaateiksi. P muuttujalle annetaan arvo
     * ruudusta, josta halutaan siirtyä pois.
     */
    @Override
    public void mousePressed(MouseEvent me) {
        pressedY = me.getY() / 80;
        pressedX = me.getX() / 80;
        p = gamestate[pressedY][pressedX];
    }

    /**
     * MouseReleased kertoo missä ruudussa pelaaja vapauttaa hiiren eli siis
     * mihin ruutuun haluaa siirtää valitsemaansa nappulaa. Jos kuitenkin ruutu
     * oli tyhjä, josta lähdettiin liikkelle niin poistutaan metodista. Tai
     * pelaaja koittaa siirtää väärää nappulaa. Sen jälkeen tarkastetaan onko
     * siirtolaillinen, jos on niin vaihdetaan vuoroa ja tarvittaessa ylennetään
     * sotilas, jos se on aiheellista. Jos siirto ei ole laillinen niin
     * katsotaan onko siirto linnoittautuminen tai ohestalyönti. Lopuksi
     * asetetaan ohestalyönnit falseksi, koska ohestalyönti tulee suorittaa HETI
     * seuraavalla siirrolla. Sen jälkeen piirretään pelitilanne.
     */
    @Override
    public void mouseReleased(MouseEvent me) {
        if (p == null) {
            return;
        } else if ((whiteMove && p.getTeam() != Team.White) || (!whiteMove && p.getTeam() == Team.White)) {
            return;
        }
        if (p.checkLegality(new Move(pressedY, pressedX), new Move(me.getY() / 80, me.getX() / 80), gamestate)) {
            Piece pieceFromSquare = makeMove(me);
            changeTurn(me, pieceFromSquare);
            promotePawn(me);
        } else {
            checkCastling(me);
            enPassantMove(me);
        }
        if (!whiteMove) {
            enPassantsFalse(Team.Black);
        } else {
            enPassantsFalse(Team.White);
        }
        draw.repaint();
    }

    /**
     * ChangeTurn vaihtaa vuoron tarkastaen onko kuningas turvassa, jos ei ole
     * niin vuoroa ei voida vaihtaa, koska siirto ei ole laillinen. Lisäksi
     * asetetaan kuninkaan tai tornin ensimmäinsen siirto todeksi, jotta voidaan
     * estää laittomat linnoittautumiset.
     */
    public void changeTurn(MouseEvent me, Piece pieceFromSquare) {
        if (whiteMove && checkIsKingSafe(me, pieceFromSquare, Team.White)) {
            whiteMove = false;
            setKingOrRookOrRookFirstMove();
        } else if (!whiteMove && checkIsKingSafe(me, pieceFromSquare, Team.Black)) {
            whiteMove = true;
            setKingOrRookOrRookFirstMove();
        }
    }

    /**
     * checkIsKingSafe tarkastaa onko kuningas turvass, jos ei niin peruutetaan
     * aikaisempi siirto ja palautetaan false. Muuten true
     *
     * @param me
     * @param pieceFromSquare
     * @param t
     * @return
     */
    public boolean checkIsKingSafe(MouseEvent me, Piece pieceFromSquare, Team t) {
        if (!getKingSafety(getKingLocation(t))) {
            takeBack(me, pieceFromSquare);
            return false;
        }
        return true;
    }

    /**
     * getKingSafety tarkastaa onko kuningas uhattuna hyödyntäen kuningas olion
     * metodeita.
     *
     * @param kingLC
     * @return
     */
    public boolean getKingSafety(Move kingLC) {
        return ((King) gamestate[kingLC.getY()][kingLC.getX()]).checkSafety(gamestate);
    }

    /**
     * MakeMove hoitaa tavallisen siirron. Otetaan talteen nappula ruudusta
     * johon halutaan siirtyä, jos siirto ei ole laillinen. Jos kyseessä
     * kuningas niin täytyy muuttaa sen sijaintia. Lopuksi suoritetaan siirto ja
     * palautetaan nappula, joka mahdollisesti syötiin ruudusta johon
     * siirrettiin nappulaa.
     *
     * @param me
     * @return
     */
    public Piece makeMove(MouseEvent me) {
        Piece from = gamestate[me.getY() / 80][me.getX() / 80];
        if (p instanceof King) {
            ((King) p).addY(me.getY() / 80);
            ((King) p).addX(me.getX() / 80);
        }
        gamestate[pressedY][pressedX] = null;
        gamestate[me.getY() / 80][me.getX() / 80] = p;
        return from;
    }

    /**
     * TakeBack saa parametrina makeMovin palauttaman nappulan, joka oli
     * ruudussa johon haluttiin siirtyä. Kuitenkin siirto on epäonnistunut ja
     * täytynee ottaa askel taaksepäin. Lisäksi tarkastetaan jos kyseessä
     * kuningas niin asetetaan äskeinen sijainti.
     */
    public void takeBack(MouseEvent me, Piece echo) {
        if (p instanceof King) {
            ((King) p).addY(pressedY);
            ((King) p).addX(pressedX);
        }
        gamestate[pressedY][pressedX] = p;
        gamestate[me.getY() / 80][me.getX() / 80] = echo;
    }

    /**
     * setKingOrRookFirstMove asettaa kuninkaan tai tornin tai sotilaan ensimmäisen siirron
     * kyseiselle oliolle jota siirretään.
     */
    public void setKingOrRookOrRookFirstMove() {
        if (p instanceof King && !((King) p).kingMoved()) {
            ((King) p).firstMoveDone();
        } else if (p instanceof Rook && !((Rook) p).rookMoved()) {
            ((Rook) p).firstMoveDone();
        } else if (p instanceof Pawn && !((Pawn) p).active()) {
            ((Pawn) p).stateActive();
        }
    }

    /**
     * CheckCastling tarkastaa voidaanko linnoittautua hyödyntäen kuningas
     * oliolle toteutettua metodia isCastlingPossible, jos metodi palauttaa true
     * niin linnoittautuminen suoritetaan ja vuoroa vaihdetaan. Muuten ei tehdä
     * mitään.
     *
     * @param me
     */
    public void checkCastling(MouseEvent me) {
        if (!(p instanceof King)) {
            return;
        }
        if (((King) p).isCastlingPossible(new Move(pressedY, pressedX), new Move(me.getY() / 80, me.getX() / 80), gamestate)) {
            castle(p.getTeam(), new Move(me.getY() / 80, me.getX() / 80), me);
            changeTurn(me, p);
        }
    }

    /**
     * Castle metodissa katsotaan kummalle puolelle halutaan linnoittautua ja
     * toteutetaan se hyödyntäen metodia doCastleMove.
     *
     * @param side
     * @param to
     * @param me
     */
    public void castle(Team side, Move to, MouseEvent me) {
        if (Team.White == side) {
            if (to.getX() == 6) {
                doCastleMove(new Move(pressedY, pressedX), new Move(7, 7), to, new Move(7, 5));
            } else if (to.getX() == 2) {
                doCastleMove(new Move(pressedY, pressedX), new Move(7, 0), to, new Move(7, 3));
            }
        } else if (Team.Black == side) {
            if (to.getX() == 6) {
                doCastleMove(new Move(pressedY, pressedX), new Move(0, 7), to, new Move(0, 5));
            } else if (to.getX() == 2) {
                doCastleMove(new Move(pressedY, pressedX), new Move(0, 0), to, new Move(0, 3));
            }
        }
    }

    /**
     * doCastleMove suorittaa linnoittautumis siirron.
     *
     * @param kingFROM
     * @param rookFROM
     * @param kingTO
     * @param rookTO
     */
    public void doCastleMove(Move kingFROM, Move rookFROM, Move kingTO, Move rookTO) {
        Piece k = gamestate[kingFROM.getY()][kingFROM.getX()];
        Piece r = gamestate[rookFROM.getY()][rookFROM.getX()];
        gamestate[kingFROM.getY()][kingFROM.getX()] = null;
        gamestate[rookFROM.getY()][rookFROM.getX()] = null;
        gamestate[kingTO.getY()][kingTO.getX()] = k;
        gamestate[rookTO.getY()][rookTO.getX()] = r;
    }

    /**
     * EnpassantMoven tarkoitus on suorittaan ohestalyönti, jos se on
     * mahdollista. Jos nappula ei ole sotilas niin poistutaan. Tarkastetaan
     * voidaanko ohestalyönti suorittaa haluttuun sotilaaseen. Hyödynnetään
     * sotilas luokan enPassantLegality metodia. Jos voidaan niin tehdään
     * siirto. Lisäksi tarkastetaan kuninkaan turvallisuus, jos ei turvassa
     * poistutaan. Muuten poistetaan nappula johon voitiin ohestalyönti
     * suorittaa ja vaihdetaan vuoroa.
     *
     * @param me
     */
    public void enPassantMove(MouseEvent me) {
        if (!(gamestate[pressedY][pressedX] instanceof Pawn)) {
            return;
        }
        if (((Pawn) gamestate[pressedY][pressedX]).enPassantLegality(new Move(pressedY, pressedX), new Move(me.getY() / 80, me.getX() / 80), gamestate)) {
            makeMove(me);
            if (!getKingSafety(getKingLocation(gamestate[me.getY() / 80][me.getX() / 80].getTeam()))) {
                gamestate[pressedY][pressedX] = gamestate[me.getY() / 80][me.getX() / 80];
                gamestate[me.getY() / 80][me.getX() / 80] = null;
                return;
            }
            if (gamestate[me.getY() / 80][me.getX() / 80].getTeam() == Team.White) {
                gamestate[me.getY() / 80 + 1][me.getX() / 80] = null;
                whiteMove = false;
            } else {
                gamestate[me.getY() / 80 - 1][me.getX() / 80] = null;
                whiteMove = true;
            }
        }
    }

    /**
     * enPassantFalse asettaa sotilas luokan enPassant totuusarvot falseksi,
     * jotta ohestalyönti ei voida suorittaa kyseiseen nappulaan.
     *
     * @param t
     */
    public void enPassantsFalse(Team t) {
        for (int i = 0; i < gamestate.length; i++) {
            for (int j = 0; j < gamestate.length; j++) {
                if (gamestate[i][j] == null) {
                    continue;
                }
                Piece current = gamestate[i][j];
                if (!(current instanceof Pawn)) {
                    continue;
                }
                if (((Pawn) current).getTeam() == t && ((Pawn) current).enPassantMove()) {
                    ((Pawn) current).enPassantFalse();
                }
            }
        }
    }

    /**
     * PromotePawn ylentää sotilaan, jos se on tarpeellista. Muutetaan hiiren
     * koordinaatit pelilaudan ruuduksi, johon siirrytään. Jos kyseessä ei ole
     * sotilas niin poistutaan. Muuten avataan ikkuna, jossa sotilas ylennetään
     * pelaajan haluamaan nappulaan.
     *
     * @param me
     */
    public void promotePawn(MouseEvent me) {
        int y = me.getY() / 80;
        int x = me.getX() / 80;
        if (!(gamestate[y][x] instanceof Pawn)) {
            return;
        }
        if (gamestate[y][x].getTeam() == Team.White && y == 0) {
            PawnPromotion a = new PawnPromotion(gamestate, new Move(y, x));
            a.run();
        } else if (gamestate[y][x].getTeam() == Team.Black && y == 7) {
            PawnPromotion b = new PawnPromotion(gamestate, new Move(y, x));
            b.run();
        }
    }

    /**
     * Jos halutaan aloittaa uusipeli niin resetGame alustaa uuden laudan ja
     * vaihtaa valkoisen vuoron todeksi.
     *
     * @param b
     */
    public void resetGame(Board b) {
        this.b = b;
        gamestate = b.getBoard();
        whiteMove = true;
    }

    /**
     * LoadGame: Kun peli ladataan muistista niin parametrina saatu board[][] on
     * pelitilanne johon jäätiin ja totuusarvo turn kertoo kenen vuoro on
     * siirtää.
     *
     * @param board
     * @param turn
     */
    public void loadGame(Piece board[][], boolean turn) {
        gamestate = board;
        whiteMove = turn;
    }

    /**
     * getDrawet paluttaa piirto luokan olion.
     */
    public DrawGameState getDrawer() {
        return draw;
    }

    /**
     * Palauttaa pelitilanteen.
     *
     * @return
     */
    public Piece[][] getState() {
        return gamestate;
    }

    /**
     * Palauttaa kenen vuoro on seuraavaksi.
     *
     * @return
     */
    public boolean turn() {
        return whiteMove;
    }

    /**
     * drawState piirtää pelitilanteen näytölle.
     */
    public void drawState() {
        draw.repaint();
    }

    /**
     * Etsii kuninkaan sijainnin laudalta ja palauttaa sen.
     *
     * @param t
     * @return
     */
    public Move getKingLocation(Team t) {
        for (int i = 0; i < gamestate.length; i++) {
            for (int j = 0; j < gamestate.length; j++) {
                if (gamestate[i][j] == null) {
                    continue;
                }
                if (gamestate[i][j].getTeam() == t && gamestate[i][j] instanceof King) {
                    return new Move(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * keyPressed ottaa käyttäjän näppäimen jota painetaan, jos kyseessä
     * välilyönti niin avataan valikko, josta vuodaan tallentaa/lataa/resettaa
     * peli.
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            MenuWindow m = new MenuWindow(this);
            m.run();
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
