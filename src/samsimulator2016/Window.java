/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsimulator2016;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Brandon
 */
public class Window extends Canvas{
    public Window(int width, int height, String title, SamSimulator2016 game){
        //the frame of the game window
        JFrame frame = new JFrame(title);
        
        //sets screen size
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setUndecorated(true);
        
        //X button works properly
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //can't resize window
        frame.setResizable(false);
        //wondow opens in middle of screen
        frame.setLocationRelativeTo(null);
        //adds game class to frame
        frame.add(game);
        //sets the icon
        //MUST CREATE IMAGE AND CLASSES FIRST frame.setIconImage(ImageLoader.loadImage("/textures/icon.png"));
        //makes the canvas fit to the frame
        frame.pack();
        //makes it so u can see game
        frame.setVisible(true);
        //runs start method in Wave
        game.start();
    }
}
