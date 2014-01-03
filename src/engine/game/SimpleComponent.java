/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.engine.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 *
 * @author dvano
 */
public final class SimpleComponent extends JComponent
{
    public static final int QUAD_SIZE = 40;

    public static final int BOARD_X = 100;
    public static final int BOARD_Y = 20;

    public static final int PLAYER_WHITE = 0;
    public static final int PLAYER_BLACK = 1;

    private final Checker[][] board = new Checker[8][8];

    private Checker enableChecker = null;

    private int strokePlayer = PLAYER_WHITE;

    private boolean destroy = false;
    private boolean running = false;

    private String status = "";

    public SimpleComponent()
    {
        this.setEnabled(true);
        try
        {
            Textures.textures.add(ImageIO.read(this.getClass().getResource("/resource/texture/checker_white.png")));
            Textures.textures.add(ImageIO.read(this.getClass().getResource("/resource/texture/checker_black.png")));
            Textures.textures.add(ImageIO.read(this.getClass().getResource("/resource/texture/checker_king_white.png")));
            Textures.textures.add(ImageIO.read(this.getClass().getResource("/resource/texture/checker_king_black.png")));
        }
        catch (IOException ex)
        {
            Logger.getLogger(SimpleComponent.class.getName()).log(Level.SEVERE, null, ex);
        }

        final MouseController mouse = new MouseController();

        this.addMouseListener(mouse);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        this.drawBoard(g2);
        this.drawLightEnableChacker(g2);
        this.drawCheckers(g2);

        if (this.running)
        {
            g2.drawString(this.status, BOARD_X + QUAD_SIZE * 8 + 40, BOARD_Y + QUAD_SIZE * 4);
        }
    }

    public void newGame()
    {
        this.clearBoard();
        this.strokePlayer = PLAYER_WHITE;
        this.addCheckers();
        this.updateStatus();
        this.running = true;
        this.repaint();
    }

    private void clearBoard()
    {
        for (int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[i].length; j++)
            {
                this.board[i][j] = null;
            }
        }
        this.running = false;
    }

    private void addCheckers()
    {
        for (int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[i].length; j++)
            {
                if (j < 3)
                {
                    if (j % 2 != 1)
                    {
                        if (i % 2 == 1)
                        {
                            this.board[i][j] = new Checker(1, 0, i, j);
                        }
                    }
                    if (j % 2 == 1)
                    {
                        if (i % 2 != 1)
                        {
                            this.board[i][j] = new Checker(1, 0, i, j);
                        }
                    }
                }
                if (j > 4)
                {
                    if (j % 2 != 1)
                    {
                        if (i % 2 == 1)
                        {
                            this.board[i][j] = new Checker(0, 0, i, j);
                        }
                    }
                    if (j % 2 == 1)
                    {
                        if (i % 2 != 1)
                        {
                            this.board[i][j] = new Checker(0, 0, i, j);
                        }
                    }
                }
            }
        }
    }

    private void drawCheckers(Graphics2D g2)
    {
        for (int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[i].length; j++)
            {
                if (this.board[i][j] != null)
                {
                    this.board[i][j].draw(g2);
                }
            }
        }
    }

    private void drawLightEnableChacker(Graphics2D g2)
    {
        if (this.enableChecker != null)
        {
            int i = this.enableChecker.getX();
            int j = this.enableChecker.getY();
            int n = Checker.SIZE;
            g2.setColor(Color.BLUE);
            g2.fillRect(i * n + BOARD_X, j * n + BOARD_Y, n, n);
            g2.setColor(Color.BLACK);
        }
    }

    private void checkTypeOfChecker()
    {
        if (this.enableChecker != null)
        {
            int player = this.enableChecker.getPlayer();
            int i = this.enableChecker.getX();
            int j = this.enableChecker.getY();
            if (this.board[i][j] != null)
            {
                if (player == PLAYER_WHITE && j == 0)
                {
                    this.board[i][j].setType();
                }
                else if (player == PLAYER_BLACK && j == 7)
                {
                    this.board[i][j].setType();
                }
            }
        }
    }

    private void drawBoard(Graphics2D g2)
    {
        g2.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[i].length; j++)
            {
                if (j % 2 != 1)
                {
                    if (i % 2 == 1)
                    {
                        g2.fillRect(i * QUAD_SIZE + BOARD_X, j * QUAD_SIZE + BOARD_Y, QUAD_SIZE, QUAD_SIZE);
                    }
                    else
                    {
                        g2.drawRect(i * QUAD_SIZE + BOARD_X, j * QUAD_SIZE + BOARD_Y, QUAD_SIZE, QUAD_SIZE);
                    }
                }
                if (j % 2 == 1)
                {
                    if (i % 2 != 1)
                    {
                        g2.fillRect(i * QUAD_SIZE + BOARD_X, j * QUAD_SIZE + BOARD_Y, QUAD_SIZE, QUAD_SIZE);
                    }
                    else
                    {
                        g2.drawRect(i * QUAD_SIZE + BOARD_X, j * QUAD_SIZE + BOARD_Y, QUAD_SIZE, QUAD_SIZE);
                    }
                }
            }
        }
        g2.setColor(Color.BLACK);

        g2.drawRect(BOARD_X, BOARD_Y, QUAD_SIZE * 8, QUAD_SIZE * 8);
    }

    private void updateStroke()
    {
        if (this.strokePlayer == PLAYER_WHITE)
        {
            this.strokePlayer = PLAYER_BLACK;
        }
        else if (this.strokePlayer == PLAYER_BLACK)
        {
            this.strokePlayer = PLAYER_WHITE;
        }
    }

    private void updateWhiteDestroy()
    {
        if (this.enableChecker != null)
        {
            int length = this.board.length;

            int i = this.enableChecker.getX();
            int j = this.enableChecker.getY();

            if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < length && j + 1 < length && this.board[i - 1][j - 1] != null && this.board[i - 1][j - 1].getPlayer() != this.strokePlayer && this.board[i + 1][j + 1] == null)
            {
                this.destroy = true;
            }
            else if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < length && j + 1 < length && this.board[i + 1][j + 1] != null && this.board[i + 1][j + 1].getPlayer() != this.strokePlayer && this.board[i - 1][j - 1] == null)
            {
                this.destroy = true;
            }
            else if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < length && j + 1 < length && this.board[i + 1][j - 1] != null && this.board[i + 1][j - 1].getPlayer() != this.strokePlayer && this.board[i - 1][j + 1] == null)
            {
                this.destroy = true;
            }
            else if (i - 1 >= 0 && j - 1 >= 0 && i + 1 < length && j + 1 < length && this.board[i - 1][j + 1] != null && this.board[i - 1][j + 1].getPlayer() != this.strokePlayer && this.board[i + 1][j - 1] == null)
            {
                this.destroy = true;
            }
        }
    }

    private void updateBlackDestroy()
    {
        if (this.enableChecker != null)
        {
            int length = this.board.length;

            int i = this.enableChecker.getX();
            int j = this.enableChecker.getY();

            if (i + 2 < length && j + 2 < length && this.board[i + 2][j + 2] == null && this.board[i + 1][j + 1] != null && this.board[i + 1][j + 1].getPlayer() != this.strokePlayer)
            {
                this.destroy = true;
            }
            else if (i - 2 >= 0 && j - 2 >= 0 && this.board[i - 2][j - 2] == null && this.board[i - 1][j - 1] != null && this.board[i - 1][j - 1].getPlayer() != this.strokePlayer)
            {
                this.destroy = true;
            }
            else if (i + 2 < length && j - 2 >= 0 && this.board[i + 2][j - 2] == null && this.board[i + 1][j - 1] != null && this.board[i + 1][j - 1].getPlayer() != this.strokePlayer)
            {
                this.destroy = true;
            }
            else if (i - 2 >= 0 && j + 2 < length && this.board[i - 2][j + 2] == null && this.board[i - 1][j + 1] != null && this.board[i - 1][j + 1].getPlayer() != this.strokePlayer)
            {
                this.destroy = true;
            }
        }
    }

    private void updateStatus()
    {
        if (this.strokePlayer == PLAYER_WHITE)
        {
            this.status = "белые ходят";
        }
        else if (this.strokePlayer == PLAYER_BLACK)
        {
            this.status = "черные ходят";
        }

        int indexWhite = 0;
        int indexBlack = 0;

        for (int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[i].length; j++)
            {
                if (this.board[i][j] != null)
                {
                    if (this.board[i][j].getPlayer() == PLAYER_WHITE)
                    {
                        indexWhite++;
                    }
                    else if (this.board[i][j].getPlayer() == PLAYER_BLACK)
                    {
                        indexBlack++;
                    }
                }
            }
        }
        if (indexWhite == 0)
        {
            this.status = "белые проиграли!";
        }
        else if (indexBlack == 0)
        {
            this.status = "черные проиграли!";
        }
    }

    private final class MouseController extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            int x = e.getX();
            int y = e.getY();

            for (int i = 0; i < SimpleComponent.this.board.length; i++)
            {
                for (int j = 0; j < SimpleComponent.this.board[i].length; j++)
                {
                    Rectangle2D rect1 = new Rectangle2D.Float(i * Checker.SIZE + BOARD_X, j * Checker.SIZE + BOARD_Y, Checker.SIZE, Checker.SIZE);
                    Rectangle2D rect2 = new Rectangle2D.Float(x, y, 1.0F, 1.0F);

                    if (rect1.intersects(rect2))
                    {
                        if (SimpleComponent.this.board[i][j] != null)
                        {
                            if (SimpleComponent.this.strokePlayer == SimpleComponent.this.board[i][j].getPlayer())
                            {
                                SimpleComponent.this.enableChecker = SimpleComponent.this.board[i][j];
                                SimpleComponent.this.updateBlackDestroy();
                            }
                        }
                        else if (SimpleComponent.this.board[i][j] == null)
                        {
                            if (SimpleComponent.this.enableChecker != null)
                            {
                                int ePlayer = SimpleComponent.this.enableChecker.getPlayer();
                                int eX = SimpleComponent.this.enableChecker.getX();
                                int eY = SimpleComponent.this.enableChecker.getY();

                                int dX = i;
                                int dY = j;

                                int lX = Math.abs(eX - dX);
                                int lY = Math.abs(eY - dY);

                                if (lX == 1 && lY == 1 && SimpleComponent.this.destroy == false)
                                {
                                    if (SimpleComponent.this.strokePlayer == SimpleComponent.PLAYER_WHITE)
                                    {
                                        int distance = eY - dY;
                                        if (SimpleComponent.this.board[eX][eY].getType() == Checker.CHECKER_KING)
                                        {
                                            SimpleComponent.this.board[eX][eY] = null;
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 1, dX, dY);

                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];

                                            SimpleComponent.this.updateWhiteDestroy();

                                            SimpleComponent.this.updateStroke();
                                        }
                                        else if (distance > 0 && SimpleComponent.this.board[eX][eY].getType() != Checker.CHECKER_KING)
                                        {
                                            SimpleComponent.this.board[eX][eY] = null;
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 0, dX, dY);

                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];

                                            SimpleComponent.this.updateWhiteDestroy();
                                            SimpleComponent.this.checkTypeOfChecker();

                                            SimpleComponent.this.updateStroke();
                                        }
                                    }
                                    else if (SimpleComponent.this.strokePlayer == SimpleComponent.PLAYER_BLACK)
                                    {
                                        int distance = eY - dY;
                                        if (SimpleComponent.this.board[eX][eY].getType() == Checker.CHECKER_KING)
                                        {
                                            SimpleComponent.this.board[eX][eY] = null;
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 1, dX, dY);

                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];

                                            SimpleComponent.this.updateWhiteDestroy();

                                            SimpleComponent.this.updateStroke();
                                        }
                                        else if (distance < 0 && SimpleComponent.this.board[eX][eY].getType() != Checker.CHECKER_KING)
                                        {
                                            SimpleComponent.this.board[eX][eY] = null;
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 0, dX, dY);

                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];

                                            SimpleComponent.this.updateWhiteDestroy();
                                            SimpleComponent.this.checkTypeOfChecker();

                                            SimpleComponent.this.updateStroke();
                                        }
                                    }
                                }
                                else if (lX == 2 && lY == 2 && SimpleComponent.this.destroy)
                                {
                                    int nX = (eX + dX) / 2;
                                    int nY = (eY + dY) / 2;

                                    if (SimpleComponent.this.board[nX][nY] != null && SimpleComponent.this.board[nX][nY].getPlayer() != ePlayer && SimpleComponent.this.board[dX][dY] == null)
                                    {
                                        SimpleComponent.this.board[eX][eY] = null;
                                        SimpleComponent.this.board[nX][nY] = null;

                                        if (SimpleComponent.this.enableChecker.getType() == Checker.CHECKER_DEFAULT)
                                        {
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 0, i, j);
                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];
                                            SimpleComponent.this.checkTypeOfChecker();
                                        }
                                        else if (SimpleComponent.this.enableChecker.getType() == Checker.CHECKER_KING)
                                        {
                                            SimpleComponent.this.board[dX][dY] = new Checker(ePlayer, 1, i, j);
                                            SimpleComponent.this.enableChecker = SimpleComponent.this.board[dX][dY];
                                        }

                                        SimpleComponent.this.destroy = false;

                                        SimpleComponent.this.updateBlackDestroy();

                                        if (SimpleComponent.this.destroy == false)
                                        {
                                            SimpleComponent.this.updateWhiteDestroy();
                                            SimpleComponent.this.updateStroke();
                                        }
                                    }
                                }
                            }

                            SimpleComponent.this.enableChecker = null;
                        }
                    }
                }
            }
            SimpleComponent.this.updateStatus();
            SimpleComponent.this.repaint();
        }
    }
}
