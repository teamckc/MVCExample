/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author
 */
public class View extends JFrame implements MessageHandler {

    private int numOfButtons = 8;
    private final int BUTTON_SIZE = 30;
    private final int BOARD_SIZE = BUTTON_SIZE * numOfButtons;
    private Messenger messenger;
    private Square[][] squares = new Square[numOfButtons][numOfButtons];
    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private int lives = 3;
    private int score = 0;
    private JLabel lifeLabel = new JLabel(lives + "");
    private JLabel scoreLabel = new JLabel(score + "");

    GridLayout layout1 = new GridLayout(0, 2);
    GridLayout layout2 = new GridLayout(numOfButtons, numOfButtons);

    public View(Messenger messenger) {
        super("MineSweeper");
        this.messenger = messenger;
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        panel.setLayout(layout1);
        panel.setLayout(layout2);
        panel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        addSquares();

        panel2.add(new Label("Score:"));
        panel2.add(scoreLabel);
        panel2.add(new Label("Lives:"));
        panel2.add(lifeLabel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);
        pack();

        setVisible(true);

    }

    public void init() {
        messenger.subscribe("Model:ScoreAPoint", this);
        messenger.subscribe("Model:LoseALife", this);
        messenger.subscribe("GameOver:Reset", this);
    }

    private void addSquares() {
        int counter = 0;
        for (int y = 0; y < numOfButtons; y++) {
            for (int x = 0; x < numOfButtons; x++) {
                squares[x][y] = new Square(x, y, messenger);
                panel.add(squares[x][y].getButton());

            }
        }
    }

    private void loseALife() {
        lives--;
        lifeLabel.setText(lives + "");
        if (lives <= 0) {
            messenger.notify("View:SendScore", score);
            messenger.notify("View:EndGame", false);
            
            setVisible(false);
        }
    }

    private void scoreAPoint() {
        score++;
        scoreLabel.setText(score + "");
        if (score >= 54) {
        messenger.notify("View:SendScore", score);
            messenger.notify("View:EndGame", true);
        
        }
    }

    private void reset() {
        score = 0;
        lives = 3;
        scoreLabel.setText(score + "");
        lifeLabel.setText(lives + "");
        setVisible(true);
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        switch (messageName) {
            case "Model:LoseALife":
                loseALife();
                break;
            case "Model:ScoreAPoint":
                scoreAPoint();
                break;
            case "GameOver:Reset":
                reset();
                break;
        }
    }
}
