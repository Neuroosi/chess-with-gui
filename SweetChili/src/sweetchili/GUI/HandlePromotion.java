
package sweetchili.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import sweetchili.Board_Pieces.Bishop;
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 *Handlepromotion luokka hoitaa nappuloiden kuuntelun, joista voidaan ylentää sotilas haluttuun vaihtoehtoon.
 * @author joel
 */
public class HandlePromotion implements ActionListener {

    private JButton q;
    private JButton r;
    private JButton b;
    private JButton n;
    private Piece gS[][];
    private Move lc;
    private JFrame f;

    public HandlePromotion(JButton q, JButton r, JButton b, JButton n, Piece[][] gS, Move lc, JFrame window) {
        this.q = q;
        this.r = r;
        this.b = b;
        this.n = n;
        this.gS = gS;
        this.lc = lc;
        this.f = window;
        this.q.addActionListener((ActionListener) this);
        this.r.addActionListener((ActionListener) this);
        this.b.addActionListener((ActionListener) this);
        this.n.addActionListener((ActionListener) this);
    }

    /**
     * Tarkastaa mitä nappulaa klikataan ja luo sen perusteella uuden olion laudalla sotilaan tilalle. Sulkee kyseisen ikkunan lopuksi.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == q) {
            Team c = gS[lc.getY()][lc.getX()].getTeam();
            gS[lc.getY()][lc.getX()] = new Queen(c);
        } else if (ae.getSource() == r) {
            Team c = gS[lc.getY()][lc.getX()].getTeam();
            gS[lc.getY()][lc.getX()] = new Rook(c);
        } else if (ae.getSource() == b) {
            Team c = gS[lc.getY()][lc.getX()].getTeam();
            gS[lc.getY()][lc.getX()] = new Bishop(c);
        } else if (ae.getSource() == n) {
            Team c = gS[lc.getY()][lc.getX()].getTeam();
            gS[lc.getY()][lc.getX()] = new Knight(c);
        }
        f.dispose();
    }

}
