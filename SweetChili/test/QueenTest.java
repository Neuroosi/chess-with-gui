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
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author joel
 */
public class QueenTest {
    
    
    @Test
    public void queenSide() {
        Queen white = new Queen(Team.White);
        Queen black = new Queen(Team.Black);
        assertEquals(Team.White, white.getTeam());
        assertEquals(Team.Black, black.getTeam());
    }
    
    @Test
    public void queenMovesCorrectlyTest(){
        Queen white = new Queen(Team.White);
        Queen black = new Queen(Team.Black);
        King kW = new King(Team.White);
        Piece b[][] = new Piece[8][8];
        b[0][0] = white;
        assertEquals(true, white.checkLegality(new Move(0, 0), new Move(1, 0), b));
        b[1][0] = white;
        assertEquals(true, white.checkLegality(new Move(1, 0), new Move(2, 1), b));
        assertEquals(false, white.checkLegality(new Move(1, 0), new Move(0, 0), b));
        b[4][4] = black;
        assertEquals(true, white.checkLegality(new Move(0, 0), new Move(4, 4), b));
        b[1][1] = white;
        b[0][1] = white;
        assertEquals(false, white.checkLegality(new Move(0, 0), new Move(4, 4), b));
        assertEquals(false, white.checkLegality(new Move(0, 0), new Move(1, 1), b));
        assertEquals(false, white.checkLegality(new Move(0, 0), new Move(1, 0), b));
        assertEquals(false, white.checkLegality(new Move(0, 0), new Move(0, 1), b));
        b[7][7] = kW;
        assertEquals(false, white.checkLegality(new Move(0, 7), new Move(7, 7), b));
        assertEquals(false, black.checkLegality(new Move(7, 0), new Move(7, 7), b));
        assertEquals(true, white.checkLegality(new Move(7, 0), new Move(7, 6), b));
        assertEquals(false, white.checkLegality(new Move(5, 5), new Move(3, 3), b));
        b[7][4] = black;
        assertEquals(false, white.checkLegality(new Move(7, 0), new Move(7, 6), b));
        assertEquals(true, white.checkLegality(new Move(7, 0), new Move(7, 4), b));
    }
}
