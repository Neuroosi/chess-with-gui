package sweetchili.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Luo ikkunan pelin lataamiselle tai tallentamiselle.
 * @author joel
 */
public class SaveLoadGameWindow {

    private Game g;
    private boolean save;

    public SaveLoadGameWindow(Game g, boolean save) {
        this.g = g;
        this.save = save;
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(500, 200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SaveLoadGameButtons s = new SaveLoadGameButtons(g.getState(), frame, g.turn(), save, g);
        //s.setFocusable(true);
        frame.add(s);
        frame.setVisible(true);
        frame.pack();
    }
}
