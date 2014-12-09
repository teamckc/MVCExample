/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author 
 */
public class GameOver extends JFrame implements MessageHandler {

    JLabel winorlose = new JLabel("test");
    JButton button = new JButton("Restart");
    int score;
    private Messenger m;

    public GameOver(Messenger m) {
        this.m = m;
        setResizable(false);
        this.setLocationRelativeTo(null);
        setPreferredSize(new Dimension(250, 100));
        add(winorlose);
        listener();
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(false);
    }

    public void init(){
       m.subscribe("View:EndGame", this); 
       m.subscribe("View:SendScore", this);
    }
    private void listener() {

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m.notify("GameOver:Reset");
                setVisible(false);
            }
        });
    }

    private void appear(boolean state){
        if(state)
            winorlose.setText("You win! Your score is: "+score);
        else
            winorlose.setText("Nice Try! Your score is: "+score);
        setVisible(true);
    }
    private void setScore(int s){
        score = s;
    }
    @Override
    public void messageHandler(String messageName, Object messagePayload) {
            switch(messageName){
                case "View:EndGame": appear((boolean)messagePayload);break;
                case "View:SendScore" : setScore((int)messagePayload);break;
            }
    }

}
