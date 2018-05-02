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
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Pawn;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author neuroosi
 */
public class PawnTest {
   
    private Pawn wP;
    private Pawn bP;
    private Piece b[][];
    
    public PawnTest() {
        b = new Piece[8][8];
        wP = new Pawn(Team.White);
        bP = new Pawn(Team.Black);
    }
    
    @Test
    public void testSide(){
        assertEquals(Team.White, wP.getTeam());
        assertEquals(Team.Black, bP.getTeam());
    }
    
    @Test
    public void testMovingandCapturing(){
        b[4][4] = wP;
        assertEquals(false, wP.checkLegality(new Move(4, 4), new Move(5, 4), b));
        assertEquals(true, wP.checkLegality(new Move(4, 4), new Move(3, 4), b));
        assertEquals(false, wP.checkLegality(new Move(4, 4), new Move(3, 3), b));
        b[3][3] = bP;
        assertEquals(true, wP.checkLegality(new Move(4, 4), new Move(3, 3), b));
        assertEquals(true, bP.checkLegality(new Move(3, 3), new Move(4, 4), b));
        assertEquals(true, bP.checkLegality(new Move(3, 3), new Move(5, 3), b));
        assertEquals(true, wP.checkLegality(new Move(4, 4), new Move(2, 4), b));
    }
   
}
