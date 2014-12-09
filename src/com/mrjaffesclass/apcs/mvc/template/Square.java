/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;

public class Square implements MessageHandler {

    private Messenger messenger;
    private int x, y;
    private JToggleButton button;
    private boolean selected;

    Square(int x, int y, Messenger messenger) {
        this.messenger = messenger;
        this.x = x;
        this.y = y;
        button = new JToggleButton();
        listener();
        messenger.subscribe("GameOver:Reset", this);
    }

    private void listener() {
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                if (!selected) {
                    messenger.notify("Square:position", new Dimension(x, y), true);
                }
                selected = true;
                button.setSelected(true);
            }
        });
    }

    public JToggleButton getButton() {
        return button;
    }

    private void reset(){
        selected=false;
        button.setSelected(false);
    }
    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        switch (messageName) {
            case "GameOver:Reset":
                reset();
                break;

        }
    }
}
