
package sweetchili.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *MenuWindow luokka luo menu ikkunan ruudulle.
 * @author joel
 */
public class MenuWindow {

    private Game g;

    public MenuWindow(Game g) {
        this.g = g;
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(100, 130));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawMenu(frame, g));
        frame.setVisible(true);
        frame.pack();
    }
}
