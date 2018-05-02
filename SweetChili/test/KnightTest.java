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
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author joel
 */
public class KnightTest {

    private Knight wN;
    private Knight bN;
    private Piece b[][];

    public KnightTest() {
        wN = new Knight(Team.White);
        bN = new Knight(Team.Black);
        b = new Piece[8][8];
    }

    @Test
    public void testEnum() {
        assertEquals(Team.White, wN.getTeam());
        assertEquals(Team.Black, bN.getTeam());
    }

    @Test
    public void testMovingANDCapturing() {
        b[4][4] = wN;
        int yDelta[] = {-1, -1, 1, 1, -2, - 2, 2, 2};
        int xDelta[] = {-2, 2, -2, 2, -1, 1, - 1, 1};
        for (int i = 0; i < yDelta.length; i++) {
            assertEquals(true, wN.checkLegality(new Move(4, 4), new Move(4 + yDelta[i], 4 + xDelta[i]), b));
        }
        b[3][2] = bN;
        assertEquals(true, wN.checkLegality(new Move(4, 4), new Move(3, 2), b));
        assertEquals(false, wN.checkLegality(new Move(4, 4), new Move(3, 22), b));
        b[3][2] = wN;
        assertEquals(false, wN.checkLegality(new Move(4, 4), new Move(3, 2), b));
    }
}
