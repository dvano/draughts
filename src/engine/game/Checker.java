/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.engine.game;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author dvano
 */
public final class Checker
{
    public static final int SIZE = 40;

    public static final int CHECKER_DEFAULT = 0;
    public static final int CHECKER_KING = 1;

    private final int player;

    private int type = CHECKER_DEFAULT;

    private final int x;
    private final int y;

    public Checker(int player, int type, int x, int y)
    {
        this.player = player;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void setType()
    {
        this.type = CHECKER_KING;
    }

    public void draw(Graphics2D g2)
    {
        if (this.player == SimpleComponent.PLAYER_WHITE && this.type == CHECKER_DEFAULT)
        {
            g2.drawImage(Textures.textures.get(0), this.x * SIZE + SimpleComponent.BOARD_X, this.y * SIZE + SimpleComponent.BOARD_Y, SIZE, SIZE, null);
        }
        else if (this.player == SimpleComponent.PLAYER_BLACK && this.type == CHECKER_DEFAULT)
        {
            g2.drawImage(Textures.textures.get(1), this.x * SIZE + SimpleComponent.BOARD_X, this.y * SIZE + SimpleComponent.BOARD_Y, SIZE, SIZE, null);
        }
        else if (this.player == SimpleComponent.PLAYER_WHITE && this.type == CHECKER_KING)
        {
            g2.drawImage(Textures.textures.get(2), this.x * SIZE + SimpleComponent.BOARD_X, this.y * SIZE + SimpleComponent.BOARD_Y, SIZE, SIZE, null);
        }
        else if (this.player == SimpleComponent.PLAYER_BLACK && this.type == CHECKER_KING)
        {
            g2.drawImage(Textures.textures.get(3), this.x * SIZE + SimpleComponent.BOARD_X, this.y * SIZE + SimpleComponent.BOARD_Y, SIZE, SIZE, null);
        }
    }

    public int getPlayer()
    {
        return this.player;
    }

    public int getType()
    {
        return this.type;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }
}
