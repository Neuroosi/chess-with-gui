package sweetchili.Board_Pieces;

/**
 * Board luokka huolehtii uuden pelilaudan luomisesta ja nappuloiden alkuasetelmista.
 * @author joel
 */

public class Board {

    private Piece[][] board;

    /**
     * Konstruktori, joka luo uuden pelilaudan.
     */
    public Board() {
        this.board = new Piece[8][8];
    }

    
    /**
     * Asettelee nappulat pelilaudalle.
     */
    public void setPieces() {
        board = new Piece[8][8];
        board[0][0] = new Rook(Team.Black);
        board[0][7] = new Rook(Team.Black);
        board[0][1] = new Knight(Team.Black);
        board[0][6] = new Knight(Team.Black);
        board[0][2] = new Bishop(Team.Black);
        board[0][5] = new Bishop(Team.Black);
        board[0][3] = new Queen(Team.Black);
        board[0][4] = new King(Team.Black);
        ((King) board[0][4]).addY(0);
        ((King) board[0][4]).addX(4);
        addPawns(board, Team.Black, 1);

        board[7][0] = new Rook(Team.White);
        board[7][7] = new Rook(Team.White);
        board[7][1] = new Knight(Team.White);
        board[7][6] = new Knight(Team.White);
        board[7][2] = new Bishop(Team.White);
        board[7][5] = new Bishop(Team.White);
        board[7][3] = new Queen(Team.White);
        board[7][4] = new King(Team.White);
        ((King) board[7][4]).addY(7);
        ((King) board[7][4]).addX(4);
        addPawns(board, Team.White, 6);
    }
    
    /**
     * Lisää sotilaat pelilaudalle värin ja rivin mukaan.
     * @param b Pelilauta
     * @param t Väri
     * @param row Rivi
     */

    public void addPawns(Piece b[][], Team t, int row) {
        for (int i = 0; i < 8; i++) {
            board[row][i] = new Pawn(t);
        }
    }
    
    /**
     * Palauttaa kaksiulotteisen taulukon, joka sisältää piece oliota.
     * @return Kaksiulotteinen taulukko
     */

    public Piece[][] getBoard() {
        return board;
    }

}
