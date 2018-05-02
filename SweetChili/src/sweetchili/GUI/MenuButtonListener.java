package sweetchili.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import sweetchili.Board_Pieces.Board;

/**
 *Menubuttonlistener luokka hallitsee valikon nappuloiden kuuntelemisen. 
 * @author joel
 */
public class MenuButtonListener implements ActionListener {

    private JButton r;
    private JButton s;
    private JButton l;
    private JFrame window;
    private Game game;

    public MenuButtonListener(JButton r, JButton s, JButton l, JFrame window, Game game) {
        this.r = r;
        this.s = s;
        this.l = l;
        this.window = window;
        this.game = game;
        this.r.addActionListener((ActionListener) this);
        this.s.addActionListener((ActionListener) this);
        this.l.addActionListener((ActionListener) this);
    }

    /**
     * Perustuen mitä nappulaa klikataan niin sen perustella jatketaan. Eli jos uusipeli niin kutsutaan game luokan resetGame metodia ja muutetaan piirto luokan
     * pelitilanne uuteen. Jos halutaan tallentaa niin suoritetaan SaveLoadGameWindow ja tallennus parametri on true. Jos halutaan tallentaa niin tallennus parametri on false.
     * Lopuksi suljetaan ikkuna ja piirretään pelitilanne.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == r) {
            Board b = new Board();
            b.setPieces();
            game.resetGame(b);
            game.getDrawer().setGameState(b.getBoard());
        } else if (ae.getSource() == s) { 
            SaveLoadGameWindow s = new SaveLoadGameWindow(game, true);
            s.run();
        } else if (ae.getSource() == l) {
            SaveLoadGameWindow l = new SaveLoadGameWindow(game, false);
            l.run();
        }
        window.dispose();
        game.drawState();
    }

}
