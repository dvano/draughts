/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.engine.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import src.engine.game.SimpleComponent;

/**
 *
 * @author dvano
 */
public final class SimpleFrame extends JFrame
{
    protected SimpleFrame()
    {
        this.setTitle("Draughts");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        final SimpleComponent component = new SimpleComponent();
        this.add(component);

        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("игра");
        JMenuItem newGame = new JMenuItem("начать новую игру");
        newGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                component.newGame();
            }
        });
        game.add(newGame);

        menuBar.add(game);

        this.setJMenuBar(menuBar);
    }
}
