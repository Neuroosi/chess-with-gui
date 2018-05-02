package sweetchili.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import sweetchili.Board_Pieces.Bishop;
import sweetchili.Board_Pieces.King;
import sweetchili.Board_Pieces.Knight;
import sweetchili.Board_Pieces.Piece;
import sweetchili.Board_Pieces.Queen;
import sweetchili.Board_Pieces.Rook;
import sweetchili.Board_Pieces.Team;

/**
 * Luokka vastaa pelitilanteen piirtämisestä näytölle. Attribuuttina pelitilanne.
 * @author joel
 */
public class DrawGameState extends JPanel {

    private Piece gameState[][];

    public DrawGameState(Piece[][] gameState) {
        this.gameState = gameState;
    }

    /**
     * Asetetaan uusipelitilanne perustuen parametriin.
     * @param g 
     */
    public void setGameState(Piece g[][]) {
        gameState = g;
    }

    /**
     * Metodi piirtää näytölle pelitilanteen kutsuen metodeita drawBoard ja drawGameState.
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        drawBoard(g);
        try {
            drawGameState(g);
        } catch (IOException ex) {
            Logger.getLogger(DrawGameState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodi piirtää shakkilaudan ruudulle.
     * @param g 
     */
    public void drawBoard(Graphics g) {
        boolean dark = false;
        for (int i = 0, y = 0; i < 8; i++, y += 80) {
            if (!dark) {
                drawRow(g, Color.WHITE, Color.GREEN, y, 0);
                dark = true;
            } else {
                drawRow(g, Color.GREEN, Color.WHITE, y, 0);
                dark = false;
            }
        }
    }

    /**
     * Metodi piirtää yhden rivin ruudulle.
     * @param g
     * @param st
     * @param nd
     * @param y
     * @param x 
     */
    public void drawRow(Graphics g, Color st, Color nd, int y, int x) {
        for (int i = 1; i <= 8; i++, x += 80) {
            if (i % 2 == 0) {
                g.setColor(nd);
                g.fillRect(x, y, 80, 80);
            } else {
                g.setColor(st);
                g.fillRect(x, y, 80, 80);
            }
        }
    }

    /**
     * Metodi piirtää nappulat ruudulle.
     * @param g
     * @throws IOException 
     */
    private void drawGameState(Graphics g) throws IOException {
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState.length; j++) {
                drawPiece(gameState[j][i], g, i, j);
            }
        }
    }
    
    /**
     * Hakee nappulan, joka halutaan piirtää ruudulle.
     * @param p
     * @param g
     * @param i
     * @param j
     * @throws IOException 
     */
    private void drawPiece(Piece p, Graphics g, int i, int j) throws IOException {
        if (p == null) {
            return;
        }
        if (p instanceof King) {
            g.drawImage(getKing(p.getTeam()), i * 80, j * 80, this);
        } else if (p instanceof Queen) {
            g.drawImage(getQueen(p.getTeam()), i * 80, j * 80, this);
        } else if (p instanceof Knight) {
            g.drawImage(getKnight(p.getTeam()), i * 80, j * 80, this);
        } else if (p instanceof Bishop) {
            g.drawImage(getBishop(p.getTeam()), i * 80, j * 80, this);
        } else if (p instanceof Rook) {
            g.drawImage(getRook(p.getTeam()), i * 80, j * 80, this);
        } else {
            g.drawImage(getPawn(p.getTeam()), i * 80, j * 80, this);
        }
    }

    /**
     * Alla olevat metodit palauttavat BufferedImage olion, jossa on tallennettu kuva, joka piirretään näytölle. Metodin nimestä näkee mikä nappula kyseessä ja värin
     * metodit saa parametrina.
     * @param t
     * @return
     * @throws IOException 
     */
    
    private BufferedImage getQueen(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackQueen.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhiteQueen.png/"));
    }

    private BufferedImage getKing(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackKing.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhiteKing.png/"));
    }

    private BufferedImage getKnight(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackKnight.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhiteKnight.png/"));
    }

    private BufferedImage getBishop(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackBishop.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhiteBishop.png/"));
    }

    private BufferedImage getRook(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackRook.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhiteRook.png/"));
    }

    private BufferedImage getPawn(Team t) throws IOException {
        if (t == Team.Black) {
            return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/BlackPawn.png/"));
        }
        return ImageIO.read(new File("src/sweetchili/GUI/Pieces/80/WhitePawn.png/"));
    }
}
