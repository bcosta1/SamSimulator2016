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
import static samsimulator2016.Assets.walk0;

/**
 *
 * @author Brandon
 */
public class HUD extends MouseAdapter{
    
    File pullUpClip = new File("res/sounds/CAMERA_VIDEO_LOA_60105303.wav");
    File pullDownClip = new File("res/sounds/put down.wav");
    File ambience0Clip = new File("res/sounds/ambience2.wav");
    File ambience1Clip = new File("res/sounds/ColdPresc B.wav");
    File ambience2Clip = new File("res/sounds/EerieAmbienceLargeSca_MV005.wav");
    File walk0Clip = new File("res/sounds/deep steps.wav");
    File walk1Clip = new File("res/sounds/Laugh_Giggle_Girl_8d.wav");
    File walk2Clip = new File("res/sounds/pirate song2.wav");
    File jumpscareSoundClip = new File("res/sounds/XSCREAM.wav");
    File errorClip = new File("res/sounds/error.wav");
    File runClip = new File("res/sounds/run.wav");
    
    Random r = new Random();
    
    public static boolean hitboxes = false;
    
    boolean calcUp = false;
    boolean learning = true;
    boolean inside = false;
    boolean jumpscare = false;
    boolean jumpscareHappening = false;
    boolean running = false;
    
    boolean[] walkPlayed = {false, false, false};
    
    int program = 0;
    int knowledge = 0;
    int suspicion = 0;
    int distance = 0;
    int time = 480*45;
    
    public void mouseReleased(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*1650/1920), (int)(SamSimulator2016.HEIGHT*200/1080), (int)(SamSimulator2016.WIDTH*85/1920), (int)(SamSimulator2016.HEIGHT*15/1080)) && !calcUp){
            if(learning){
                if(inside){
                    Assets.error.stop();
                    Assets.error.close();
                    try {
                        Assets.error.open(AudioSystem.getAudioInputStream(errorClip));
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    Assets.error.start();
                }else{
                    learning = false;
                }
            }else{
                learning = true;
            }
        }
        
        if(mouseOver(mx, my, (int)(SamSimulator2016.WIDTH*450/1920), (int)(SamSimulator2016.HEIGHT*960/1080), (int)(SamSimulator2016.WIDTH*960/1920), (int)(SamSimulator2016.HEIGHT*70/1080))){
            if(calcUp){
                if(jumpscare){
                    jumpscareHappening = true;
                    Assets.jumpscareSound.start();
                }
                Assets.pullUp.stop();
                Assets.pullUp.close();
                try {
                    Assets.pullUp.open(AudioSystem.getAudioInputStream(pullUpClip));
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Assets.putDown.start();
                calcUp = false;
            }else if (learning){
                Assets.putDown.stop();
                Assets.putDown.close();
                try {
                    Assets.putDown.open(AudioSystem.getAudioInputStream(pullDownClip));
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Assets.pullUp.start();
                calcUp = true;
                
                if(inside){
                    jumpscare = true;
                }
                
                if((double)(distance/300) >= 100.0){
                    if(learning){
                        inside = true;
                    }
                }else if((double)(distance/300) >= 75.0){
                    if(r.nextInt(3) == 0 && learning){
                        inside = true;
                    }
                }else if((double)(distance/300) >= 50.0){
                    if(r.nextInt(5) == 0 && learning){
                        inside = true;
                    }
                }else if((double)(distance/300) >= 25.0){
                    if(r.nextInt(10) == 0 && learning){
                        inside = true;
                    }
                }
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
    
    int scareTime = 10000;
    public void tick(){
        //determins whether game is lost
        if(Assets.jumpscareSound.getMicrosecondPosition() == Assets.jumpscareSound.getMicrosecondLength()){
            Assets.jumpscareSound.stop();
            Assets.jumpscareSound.close();
            
            SamSimulator2016.gameState = State.GameOver;
        }
        
        //determines whether you won
        if(program/50 == 100){
            Assets.cheer.start();
            SamSimulator2016.gameState = State.GameEnd;
        }
        
        if(time != -1){
            time--;
        }
        
        if(time < -1){
            time = 0;
        }
        
        if(time == 0){
            time = -1;
            Assets.ambience0.stop();
            Assets.ambience1.stop();
            Assets.ambience2.stop();
            Assets.ambience0.close();
            Assets.ambience1.close();
            Assets.ambience2.close();
            Assets.powerdown.start();
        }
        
        if(Assets.powerdown.getMicrosecondPosition() == Assets.powerdown.getMicrosecondLength()){
            Assets.powerdown.stop();
            Assets.powerdown.close();
            
            scareTime = r.nextInt((int)(Assets.musicbox.getMicrosecondLength()));
            Assets.musicbox.start();
        }
        
        if((int)(Assets.musicbox.getMicrosecondPosition()) >= scareTime && !jumpscareHappening){
            Assets.musicbox.stop();
            Assets.musicbox.close();
            
            jumpscareHappening = true;
            Assets.jumpscareSound.start();
        }
        
        if(calcUp && program/50 < knowledge/75 && program/50 < 100){
            program++;
            suspicion+=4;
        }
        
        if(!calcUp && learning && knowledge/75 < 100){
            knowledge++;
            suspicion+=2;
        }
        
        if(!learning && !calcUp){
            suspicion -= 5;
        }
        
        if(suspicion > 0){
            distance += r.nextInt((suspicion/500)+1);
        }
        
        //hendricks *foxy* AI
        if(suspicion == 5000 && !running && time != -1){
            running = true;
            Assets.run.start();
        }
        
        if(running){
            distance += 300;
        }
        
        if(Assets.run.getMicrosecondPosition() == Assets.run.getMicrosecondLength()){
            if(calcUp || learning){
                jumpscareHappening = true;
                Assets.jumpscareSound.start();
            }else{
                suspicion = 0;
                distance = 0;
                
                walkPlayed[0] = false;
                walkPlayed[1] = false;
                walkPlayed[2] = false;
                
                time -= 10*45*6;
                
                running = false;
            }
            
            Assets.run.stop();
            Assets.run.close();
            try {
                Assets.run.open(AudioSystem.getAudioInputStream(runClip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //keeps variable from going below 0
        suspicion = SamSimulator2016.clamp(suspicion, 0, 5000);
        distance = SamSimulator2016.clamp(distance, 0, 30000);
        
        //hendricks walking sounds
        if((double)(distance/300) >= 25.0 && !walkPlayed[0] && !running){
            hendricksWalkSound();
            walkPlayed[0] = true;
        }
        
        if((double)(distance/300) >= 50.0 && !walkPlayed[1] && !running){
            hendricksWalkSound();
            walkPlayed[1] = true;
        }
        
        if((double)(distance/300) >= 75.0 && !walkPlayed[2] && !running){
            hendricksWalkSound();
            walkPlayed[2] = true;
        }
        
        
        //manages background sound
        if(Assets.ambience0.getMicrosecondPosition() >= Assets.ambience0.getMicrosecondLength()){
            Assets.ambience0.stop();
            Assets.ambience0.close();
            try {
                Assets.ambience0.open(AudioSystem.getAudioInputStream(ambience0Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.ambience1.start();
        }
        
        if(Assets.ambience1.getMicrosecondPosition() >= Assets.ambience1.getMicrosecondLength()){
            Assets.ambience1.stop();
            Assets.ambience1.close();
            try {
                Assets.ambience1.open(AudioSystem.getAudioInputStream(ambience1Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.ambience2.start();
        }
        
        if(Assets.ambience2.getMicrosecondPosition() >= Assets.ambience2.getMicrosecondLength()){
            Assets.ambience2.stop();
            Assets.ambience2.close();
            try {
                Assets.ambience2.open(AudioSystem.getAudioInputStream(ambience2Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HUD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.ambience2.start();
        }
    }
    
    private void hendricksWalkSound(){
        int temp = r.nextInt(3);
        
        if(temp == 0){
            Assets.walk0.stop();
            Assets.walk0.close();
            try {
                Assets.walk0.open(AudioSystem.getAudioInputStream(walk0Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.walk0.start();
        }
        if(temp == 1){
            Assets.walk1.stop();
            Assets.walk1.close();
            try {
                Assets.walk1.open(AudioSystem.getAudioInputStream(walk1Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.walk1.start();
        }
        if(temp == 2){
            Assets.walk2.stop();
            Assets.walk2.close();
            try {
                Assets.walk2.open(AudioSystem.getAudioInputStream(walk2Clip));
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Assets.walk2.start();
        }
    }
    
    public void render(Graphics g){
        //show how far they are through the program/what screen is up on the laptop
        if (jumpscareHappening) {
            g.drawImage(Assets.jumpscare[1], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
        }else{
            if (calcUp) {
                if ((double) program / 5000 > (double) 7 / 7) {
                    g.drawImage(Assets.calculator[7], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 6 / 7) {
                    g.drawImage(Assets.calculator[6], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 5 / 7) {
                    g.drawImage(Assets.calculator[5], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 4 / 7) {
                    g.drawImage(Assets.calculator[4], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 3 / 7) {
                    g.drawImage(Assets.calculator[3], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 2 / 7) {
                    g.drawImage(Assets.calculator[2], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else if ((double) program / 5000 > (double) 1 / 7) {
                    g.drawImage(Assets.calculator[1], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else {
                    g.drawImage(Assets.calculator[0], 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                }
            } else {
                if (learning) {
                    g.drawImage(Assets.tutorial, 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                } else {
                    g.drawImage(Assets.work, 0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT, null);
                }
            }
        }
        
        if(time != -1){
            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.PLAIN, (int) (SamSimulator2016.WIDTH * 50 / 1920)));
            //displays time
            g.drawString((time / (45 * 6) + 1) + " Minutes", (int) (SamSimulator2016.WIDTH * 1650 / 1920), (int) (SamSimulator2016.HEIGHT * 50 / 1080));
            //displays distance
            g.drawString("Distance: ", (int) (SamSimulator2016.WIDTH * 50 / 1920), (int) (SamSimulator2016.HEIGHT * 930 / 1080));
            g.drawRect((int) (SamSimulator2016.WIDTH * 289 / 1920), (int) (SamSimulator2016.HEIGHT * 902 / 1080), (int) (SamSimulator2016.WIDTH * 101 / 1920), (int) (SamSimulator2016.HEIGHT * 26 / 1080));
            g.setColor(Color.green);
            g.fillRect((int) (SamSimulator2016.WIDTH * 290 / 1920), (int) (SamSimulator2016.HEIGHT * 903 / 1080), (int) (SamSimulator2016.WIDTH * (100 - (distance / 300)) / 1920), (int) (SamSimulator2016.HEIGHT * 25 / 1080));
            //displays knowledge and program
            g.setColor(Color.white);
            g.drawString("Knowledge: " + (knowledge / 75) + "%", (int) (SamSimulator2016.WIDTH * 50 / 1920), (int) (SamSimulator2016.HEIGHT * 980 / 1080));
            g.drawString("Program: " + (program / 50) + "%", (int) (SamSimulator2016.WIDTH * 50 / 1920), (int) (SamSimulator2016.HEIGHT * 1030 / 1080));
            //displays calcswitch
            if(learning){
                g.drawImage(Assets.calcswitch, (int) (SamSimulator2016.WIDTH * 450 / 1920), (int) (SamSimulator2016.HEIGHT * 950 / 1080), (int) (SamSimulator2016.WIDTH * 960 / 1920), (int) (SamSimulator2016.HEIGHT * 90 / 1080), null);
            }
        }else if(!jumpscareHappening){
            g.fillRect(0, 0, SamSimulator2016.WIDTH, SamSimulator2016.HEIGHT);
        }
        
        //hitboxes
        g.setColor(Color.red);
        if(hitboxes){
            g.drawRect((int)(SamSimulator2016.WIDTH*1650/1920), (int)(SamSimulator2016.HEIGHT*200/1080), (int)(SamSimulator2016.WIDTH*85/1920), (int)(SamSimulator2016.HEIGHT*15/1080));
            g.drawRect((int)(SamSimulator2016.WIDTH*450/1920), (int)(SamSimulator2016.HEIGHT*960/1080), (int)(SamSimulator2016.WIDTH*960/1920), (int)(SamSimulator2016.HEIGHT*70/1080));
        }
        
    }
}
