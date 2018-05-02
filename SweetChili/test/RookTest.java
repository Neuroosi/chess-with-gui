

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 *
 * @author joel
 */
public class RookTest {
    
    private Piece b[][];
    private Rook wR;
    private Rook bR;

    public RookTest() {
        b = new Piece[8][8];
        wR = new Rook(Team.White);
        bR = new Rook(Team.Black);
    }
    
    @Test
    public void testEnum(){
        assertEquals(Team.White, wR.getTeam());
        assertEquals(Team.Black, bR.getTeam());
    }
    
    @Test
    public void testMovingandCapturing(){
        b[4][4] = wR;
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(7, 4), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(4, 7), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(4, 0), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(0, 4), b));
        b[4][6] = bR;
        assertEquals(false, wR.checkLegality(new Move(4, 4), new Move(4, 7), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(4, 6), b));
        b[6][4] = bR;
        assertEquals(false, wR.checkLegality(new Move(4, 4), new Move(7, 4), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(6, 4), b));
        b[4][1] = bR;
        assertEquals(false, wR.checkLegality(new Move(4, 4), new Move(4, 0), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(4, 1), b));
        b[1][4] = bR;
        assertEquals(false, wR.checkLegality(new Move(4, 4), new Move(0, 4), b));
        assertEquals(true, wR.checkLegality(new Move(4, 4), new Move(1, 4), b));
    }
    
}
