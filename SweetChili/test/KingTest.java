/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sweetchili.Board_Pieces.Bishop;
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Pawn;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author joel
 */
public class KingTest {

    private King wK;
    private King bK;
    private Queen wQ;
    private Queen bQ;
    private Bishop wB;
    private Bishop bB;
    private Knight wN;
    private Knight bN;
    private Rook wR;
    private Rook bR;
    private Pawn wP;
    private Pawn bP;
    private Piece b[][];

    public KingTest() {
        wK = new King(Team.White);
        bK = new King(Team.Black);
        wQ = new Queen(Team.White);
        bQ = new Queen(Team.Black);
        wB = new Bishop(Team.White);
        bB = new Bishop(Team.Black);
        wN = new Knight(Team.White);
        bN = new Knight(Team.Black);
        wR = new Rook(Team.White);
        bR = new Rook(Team.Black);
        wP = new Pawn(Team.White);
        bP = new Pawn(Team.Black);
        b = new Piece[8][8];
    }

    @Test
    public void testEnum() {
        King white = new King(Team.White);
        King black = new King(Team.Black);
        assertEquals(Team.White, white.getTeam());
        assertEquals(Team.Black, black.getTeam());
    }

    @Test
    public void testMoving() {
        b = new Piece[8][8];
        b[0][0] = bK;
        b[0][1] = wQ;
        b[1][0] = wQ;
        b[1][1] = bQ;
        assertEquals(false, bK.checkLegality(new Move(0, 0), new Move(1, 1), b));
        assertEquals(true, bK.checkLegality(new Move(0, 0), new Move(1, 0), b));
        assertEquals(true, bK.checkLegality(new Move(0, 0), new Move(0, 1), b));
        assertEquals(false, bK.checkLegality(new Move(0, 0), new Move(2, 2), b));
        b[1][1] = null;
        assertEquals(true, bK.checkLegality(new Move(0, 0), new Move(1, 1), b));
        assertEquals(false, bK.checkLegality(new Move(0, 0), new Move(7, 7), b));
        b[4][4] = bK;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                assertEquals(true, bK.checkLegality(new Move(4, 4), new Move(4 + i, 4 + j), b));
            }
        }
        assertEquals(false, bK.checkLegality(new Move(7, 7), new Move(8, 8), b));
        assertEquals(false, bK.checkLegality(new Move(7, 7), new Move(-1, -1), b));
        assertEquals(false, bK.checkLegality(new Move(7, 7), new Move(-1, 8), b));
    }

    @Test
    public void testDiagonalCheck() {
        b = new Piece[8][8];
        b[4][4] = wK;
        b[0][0] = bQ;
        wK.addX(4);
        wK.addY(4);
        assertEquals(false, wK.checkSafety(b));
        b[0][0] = null;
        b[1][7] = bB;
        assertEquals(false, wK.checkSafety(b));
        b[1][7] = null;
        b[7][7] = bB;
        assertEquals(false, wK.checkSafety(b));
        b[7][7] = null;
        b[7][1] = bQ;
        assertEquals(false, wK.checkSafety(b));
        b[6][2] = wP;
        assertEquals(true, wK.checkSafety(b));
        b[6][2] = bP;
        assertEquals(true, wK.checkSafety(b));
    }

    @Test
    public void testHorizontalCheck() {
        b = new Piece[8][8];
        b[4][4] = wK;
        b[4][0] = bQ;
        wK.addX(4);
        wK.addY(4);
        assertEquals(false, wK.checkSafety(b));
        b[4][3] = bB;
        assertEquals(true, wK.checkSafety(b));
        b[4][3] = wP;
        assertEquals(true, wK.checkSafety(b));
        b[7][4] = bR;
        assertEquals(false, wK.checkSafety(b));
        b[7][4] = bB;
        assertEquals(true, wK.checkSafety(b));
        b[0][4] = bR;
        assertEquals(false, wK.checkSafety(b));
        b[1][4] = wQ;
        assertEquals(true, wK.checkSafety(b));
        b[4][7] = bQ;
        assertEquals(false, wK.checkSafety(b));
        b[4][5] = bB;
        assertEquals(true, wK.checkSafety(b));
    }

    @Test
    public void testCheckByPawn() {
        b = new Piece[8][8];
        b[4][4] = bK;
        bK.addY(4);
        bK.addX(4);
        b[5][5] = wP;
        assertEquals(false, bK.checkSafety(b));
        b[5][5] = null;
        b[5][3] = wP;
        assertEquals(false, bK.checkSafety(b));
        b[5][3] = null;
        b[5][4] = wP;
        assertEquals(true, bK.checkSafety(b));
        b = new Piece[8][8];
        b[4][4] = wK;
        wK.addY(4);
        wK.addX(4);
        b[3][3] = bP;
        assertEquals(false, wK.checkSafety(b));
        b[3][3] = null;
        b[3][5] = bP;
        assertEquals(false, wK.checkSafety(b));
        b[3][5] = null;
        b[3][4] = bP;
        assertEquals(true, wK.checkSafety(b));
    }

    @Test
    public void testCheckByKnight() {
        b = new Piece[8][8];
        b[4][4] = wK;
        wK.addY(4);
        wK.addX(4);
        int yDelta[] = {-1, -1, 1, 1, -2, - 2, 2, 2};
        int xDelta[] = {-2, 2, -2, 2, -1, 1, - 1, 1};
        int y = 0;
        int x = 0;
        for (int i = 0; i < yDelta.length; i++) {
            b[y][x] = null;
            b[4 + yDelta[i]][4 + xDelta[i]] = bN;
            assertEquals(false, wK.checkSafety(b));
            y = 4 + yDelta[i];
            x = 4 + xDelta[i];
        }
        b = new Piece[8][8];
        b[4][4] = wK;
        b[5][5] = bN;
        assertEquals(true, wK.checkSafety(b));
    }

    @Test
    public void testCastling() {
        b = new Piece[8][8];
        b[7][4] = wK;
        wK.addY(7);
        wK.addX(4);
        b[7][7] = wR;
        assertEquals(true, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[0][0] = bB;
        assertEquals(true, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[1][0] = bQ;
        assertEquals(false, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[1][0] = null;
        b[2][0] = bB;
        assertEquals(false, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[2][0] = null;
        b[0][6] = bR;
        assertEquals(false, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[6][6] = wP;
        assertEquals(true, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b[0][7] = bR;
        assertEquals(true, wK.castling(b, new Move(7, 4), new Move(7, 6)));
        b = new Piece[8][8];
        b[7][0] = wR;
        b[7][4] = wK;
        wK.addY(7);
        wK.addX(4);
        b[0][3] = bQ;
        b[6][3] = wQ;
        assertEquals(true, wK.castling(b, new Move(7, 4), new Move(7, 2)));
    }

}
