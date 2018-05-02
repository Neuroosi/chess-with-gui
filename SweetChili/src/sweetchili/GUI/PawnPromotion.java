/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sweetchili.GUI;

import java.awt.Dimension;
import javax.swing.JFrame;
import sweetchili.Board_Pieces.Move;
import sweetchili.Board_Pieces.Piece;

/**
 *Kun ylennetään sotilas niin luodaan ikkuna ylennys vaihtoehdoille.
 * @author joel
 */
public class PawnPromotion {

    private Piece gameState[][];
    private Move lc;

    public PawnPromotion(Piece g[][], Move lc) {
        gameState = g;
        this.lc = lc;
    }

    //@Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(500, 60));
        AddButtons a = new AddButtons(gameState, lc, frame);
        frame.add(a);
        frame.setVisible(true);
        frame.pack();
    }

}
