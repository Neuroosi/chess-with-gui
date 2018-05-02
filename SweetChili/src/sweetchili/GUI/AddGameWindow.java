package sweetchili.GUI;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import sweetchili.Board_Pieces.Board;

/**
 * AddGameWindow on luokka, joka tuo näytölle ikkunan johon pelitilanne piirretään.
 */
public class AddGameWindow implements Runnable {

    private JFrame frame;
    private Board b;

    public AddGameWindow(Board b) {
        this.b = b;
        b.setPieces();
    }

    /**
     * Alustetaan ikkuna
     */
    @Override
    public void run() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(650, 670));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawGameState d = new DrawGameState(b.getBoard());
        addComponent(d);
        Game g = new Game(b, d);
        frame.setVisible(true);
        frame.pack();
        d.requestFocusInWindow();
    }

    
    /**
     * Lisää kompinentit.
     * @param c 
     */
    public void addComponent(Container c) {
        frame.add(c);

    }
}
