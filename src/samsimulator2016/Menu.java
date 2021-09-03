/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsimulator2016;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Brandon
 */
public class Menu extends MouseAdapter{
    
    private Handler handler;
    private HUD hud;
    private Random r;
    
    File darknessMusicClip = new File("res/sounds/darkness music.wav");
    
    public Menu(Handler handler, HUD hud){
        this.handler = handler;
        this.hud = hud;
        r = new Random();
    }
    
    public void mousePressed(MouseEvent e){
        
    }
    
    public void mouseReleased(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        //new game
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*600/1080), (int)(SamSimulator2016.WIDTH*500/1920), (int)(SamSimulator2016.HEIGHT*100/1080))){
            if(SamSimulator2016.gameState == State.Menu){
                Assets.darknessMusic.stop();
                Assets.darknessMusic.close();
                Assets.help.stop();
                Assets.help.close();
                Assets.ambience0.start();
                SamSimulator2016.gameState = State.Game;
            }
        }
        
        //help
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*700/1080), (int)(SamSimulator2016.WIDTH*200/1920), (int)(SamSimulator2016.HEIGHT*100/1080))){
            if(SamSimulator2016.gameState == State.Menu){
                Assets.help.start();
            }
        }
        
        //exit
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*800/1080), (int)(SamSimulator2016.WIDTH*200/1920), (int)(SamSimulator2016.HEIGHT*100/1080))){
            if(SamSimulator2016.gameState == State.Menu){
                System.exit(0);
            }
        }
        
        //quit
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*627/1920), (int)(SamSimulator2016.HEIGHT*970/1080), (int)(SamSimulator2016.WIDTH*665/1920), (int)(SamSimulator2016.HEIGHT*50/1080))){
            if(SamSimulator2016.gameState == State.GameEnd || SamSimulator2016.gameState == State.GameOver){
                System.exit(0);
            }
        }
        
    }
    
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }
        }
        return false;
    }
    
    public void tick(){
        if(Assets.darknessMusic.getMicrosecondPosition() == Assets.darknessMusic.getMicrosecondLength()){
            Assets.darknessMusic.stop();
            Assets.darknessMusic.close();
            try {
                Assets.darknessMusic.open(AudioSystem.getAudioInputStream(darknessMusicClip));
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.darknessMusic.start();
        }
    }
    
    public void render(Graphics g){
        g.setFont(new Font("arial", Font.PLAIN, (int) (SamSimulator2016.WIDTH * 100 / 1920)));
        g.setColor(Color.white);
        
        g.drawImage(Assets.home, 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
        
        if(SamSimulator2016.gameState == State.Menu){
            g.drawString("Sam", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*200/1080));
            g.drawString("Simulator", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*300/1080));
            g.drawString("2016", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*400/1080));
            
            g.drawString("New Game", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*700/1080));
            g.drawString("Help", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*800/1080));
            g.drawString("Quit", (int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*900/1080));
        }else if(SamSimulator2016.gameState == State.GameOver){
            g.drawImage(Assets.gameover, 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
        }else if(SamSimulator2016.gameState == State.GameEnd){
            g.drawImage(Assets.gameend, 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
        }
        
        g.setColor(Color.red);
        if(HUD.hitboxes){
            g.drawRect((int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*600/1080), (int)(SamSimulator2016.WIDTH*500/1920), (int)(SamSimulator2016.HEIGHT*100/1080));
            g.drawRect((int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*700/1080), (int)(SamSimulator2016.WIDTH*200/1920), (int)(SamSimulator2016.HEIGHT*100/1080));
            g.drawRect((int)(SamSimulator2016.WIDTH*100/1920), (int)(SamSimulator2016.HEIGHT*800/1080), (int)(SamSimulator2016.WIDTH*200/1920), (int)(SamSimulator2016.HEIGHT*100/1080));
            g.drawRect((int)(SamSimulator2016.WIDTH*627/1920), (int)(SamSimulator2016.HEIGHT*970/1080), (int)(SamSimulator2016.WIDTH*665/1920), (int)(SamSimulator2016.HEIGHT*50/1080));
        }
    }
    
}
