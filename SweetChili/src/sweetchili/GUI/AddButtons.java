
package sweetchili.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;

/**
 * AddButtons luokka lisää nappulat ikkunaan, joista valitaan miksikä sotilas halutaan ylentää. Attribuutteina 
 * pelitilanne, sijainti sekä ikkuna olio.
 * @author joel
 */
public class AddButtons extends JPanel {

    private Piece gs[][];
    private Move lc;
    private JFrame f;

    public AddButtons(Piece[][] gs, Move lc, JFrame window) {
        this.gs = gs;
        this.lc = lc;
        f = window;
        createButtons();
    }

    
    /**
     * Piirtää nappulat joihin sotilas voidaan ylentää, joista käyttäjä voi valita haluamansa.
     */
    public void createButtons() {
        JLabel t = new JLabel("Choose a piece for promotion:");
        t.setBounds(0, 0,  50 , 50);
        t.setBorder(null);
        t.setVisible(true);
        this.add(t);
        JButton q = new JButton("Queen");
        q.setBounds(0, 20, 50, 50);
        q.setBorder(null);
        q.setVisible(true);
        this.add(q);
        JButton r = new JButton("Rook");
        r.setBounds(0, 30, 50, 50);
        r.setBorder(null);
        r.setVisible(true);
        this.add(r);
        JButton b = new JButton("Bishop");
        b.setBounds(0, 40, 50, 50);
        b.setBorder(null);
        b.setVisible(true);
        this.add(b);
        JButton n = new JButton("Knight");
        n.setBounds(0, 50, 50, 50);
        n.setBorder(null);
        n.setVisible(true);
        this.add(n);
        HandlePromotion h = new HandlePromotion(q, r, b, n, gs, lc, f);
    }

}
