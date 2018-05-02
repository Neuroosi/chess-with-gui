package sweetchili.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import sweetchili.Board_Pieces.Piece;

/**
 *Luodaan nappulat pelitilanteen tallentamiseksi tai lataamiseksi. Tekstikenttä annetaan polku tiedostoon johon tallennetaan tai josta ladataan peli.
 * @author joel
 */
public class SaveLoadGameButtons extends JPanel {

    private Piece gamestate[][];
    private JFrame window;
    private boolean whitemove;
    private boolean save;
    private Game g;

    public SaveLoadGameButtons(Piece[][] gamestate, JFrame window, boolean whitemove, boolean save, Game g) {
        this.gamestate = gamestate;
        this.whitemove = whitemove;
        this.window = window;
        this.save = save;
        this.g = g;
        setLayout(null);
        createButtons();
    }

  
    /**
     * Luo nappulat
     */
    public void createButtons() {
        JLabel path = new JLabel("Path:");
        path.setLocation(0, 50);
        path.setSize(50, 100);
        path.setVisible(true);
        this.add(path);
        JLabel text = new JLabel(buttonText());
        text.setLocation(0, 0);
        text.setSize(150, 50);
        text.setVisible(true);
        add(text);
        JTextPane input = new JTextPane();
        input.setLocation(40, 90);
        input.setSize(300, 20);
        input.setVisible(true);
        add(input);
        JButton b = new JButton("...");
        b.setLocation(350, 90);
        b.setSize(50, 25);
        b.setVisible(true);
        add(b);
        SaveLoadGameHandle s = new SaveLoadGameHandle(input, b, gamestate, window, whitemove, save, g);
    }

    /**
     * Tarkastaa halutaanko tallentaa vai ladata peli. Palauttaa tekstin JLabel oliolle perustuen save totuusarvoon.
     */
    public String buttonText() {
        if (save) {
            return "Save game";
        }
        return "Load game";
    }
}
