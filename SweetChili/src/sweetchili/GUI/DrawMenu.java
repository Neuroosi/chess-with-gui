
package sweetchili.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *DrawMenu luokka piirtää näytölle nappulat, joista voidaan aloittaa uusipeli tai tallentaa/ladata peli muistista. Kutsuen metodia addButtons()
 * @author joel
 */
public class DrawMenu extends JPanel {

    private JFrame window;
    private Game g;

    public DrawMenu(JFrame window, Game g) {
        this.window = window;
        this.g = g;
        addButtons();
    }

    public void addButtons() {
        JLabel t = new JLabel("Menu: ");
        t.setBounds(0, 0, 50, 50);
        t.setBorder(null);
        t.setVisible(true);
        this.add(t);
        JButton r = new JButton("Reset game");
        r.setBounds(0, 60, 50, 50);
        r.setBorder(null);
        r.setVisible(true);
        this.add(r);
        JButton s = new JButton("Save game");
        s.setBounds(0, 110, 50, 50);
        s.setBorder(null);
        s.setVisible(true);
        this.add(s);
        JButton l = new JButton("Load game");
        l.setBounds(0, 160, 50, 50);
        l.setBorder(null);
        l.setVisible(true);
        this.add(l);
        MenuButtonListener m = new MenuButtonListener(r, s, l, window, g);
    }
}
