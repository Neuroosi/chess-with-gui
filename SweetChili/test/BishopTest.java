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
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author neuroosi
 */
public class BishopTest {

    @Test
    public void TestEnum() {
        Bishop w = new Bishop(Team.White);
        Bishop bB = new Bishop(Team.Black);
        assertEquals(Team.White, w.getTeam());
        assertEquals(Team.Black, bB.getTeam());
    }

    @Test
    public void testMoving() {
        Piece b[][] = new Piece[8][8];
        Bishop w = new Bishop(Team.White);
        Bishop bB = new Bishop(Team.Black);
        b[0][0] = w;
        b[7][7] = bB;
        assertEquals(true, w.checkLegality(new Move(0, 0), new Move(7, 7), b));
        assertEquals(true, bB.checkLegality(new Move(7, 7), new Move(0, 0), b));
        assertEquals(false, bB.checkLegality(new Move(3, 3), new Move(7, 6), b));
        b[3][3] = w;
        assertEquals(false, bB.checkLegality(new Move(7, 7), new Move(0, 0), b));
        b[3][3] = bB;
        assertEquals(false, bB.checkLegality(new Move(7, 7), new Move(0, 0), b));
        assertEquals(false, bB.checkLegality(new Move(2, 0), new Move(0, 0), b));
    }
}
