package sweetchili.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import sweetchili.LoadSavegame.Savegame;
import sweetchili.Board_Pieces.Piece;
import sweetchili.LoadSavegame.LoadGame;

/**
 * Kuuntelee SaveLoadGame nappuloita.
 * @author joel
 */
public class SaveLoadGameHandle implements ActionListener, KeyListener {

    private JTextPane input;
    private JButton p;
    private Piece gamestate[][];
    private String in = "";
    private JFrame window;
    private boolean whitemove;
    private boolean save;
    private Game g;

    public SaveLoadGameHandle(JTextPane input, JButton p, Piece[][] gamestate, JFrame window, boolean whitemove, boolean save, Game g) {
        this.input = input;
        this.p = p;
        this.gamestate = gamestate;
        this.window = window;
        this.whitemove = whitemove;
        this.save = save;
        this.g = g;
        this.p.addActionListener((ActionListener) this);
        this.input.addKeyListener((KeyListener) this);
    }

    /**
     * Jos nappulaa "..." painetaan niin tallennetaan peli jos save totta muuten ladataan peli muistista.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == p) {
            if (save) {
                savegame();
            } else {
                loadgame();
            }
        }
        window.dispose();
    }

    /**
     * Metodia kutsutaan jos halutaan tallentaa peli muistiin.
     */
    public void savegame() {
        try {
            Savegame save = new Savegame(in, gamestate, whitemove);
            save.writeFile();
        } catch (IOException ex) {
            Logger.getLogger(SaveLoadGameHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodia kutsutaan jos halutaan ladata peli muistista.
     */
    public void loadgame() {
        boolean failure = false;
        LoadGame l = null;
        try {
            l = new LoadGame(in);
            l.loadGame();
        } catch (IOException ex) {
            Logger.getLogger(SaveLoadGameHandle.class.getName()).log(Level.SEVERE, null, ex);
            failure = true;
        }
        if (!failure && l != null) {
            g.loadGame(l.getState(), l.whiteMove());
            g.getDrawer().setGameState(l.getState());
            g.drawState();
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * Ottaa käyttäjän syötteen ja tallentaa sen in muuttujaan.
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
            return;
        }
        in += ke.getKeyChar();
        System.out.println(in);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
