/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsimulator2016;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Brandon
 */
public class Assets {
    
    private static final int width = 16, height = 16;
    
    //gamescreen stuff
    public static BufferedImage tutorial, work, calcswitch, home, gameover, gameend;
    
    //calculator
    public static BufferedImage[] calculator = new BufferedImage[8];
    
    //jumpscare
    public static BufferedImage[] jumpscare = new BufferedImage[3];
    
    //calculator
    public static Clip pullUp, putDown;
    
    //background noise
    public static Clip ambience0, ambience1, ambience2;
    
    //hendricks walking
    public static Clip walk0, walk1, walk2;
    
    public static Clip jumpscareSound, error, run, powerdown, musicbox, darknessMusic, cheer, help;
    
    public static void init(){
        SpriteSheet tutorialPic = new SpriteSheet(ImageLoader.loadImage("/textures/tutorial.jpg"));
        SpriteSheet workPic = new SpriteSheet(ImageLoader.loadImage("/textures/work.jpg"));
        SpriteSheet calcswitchPic = new SpriteSheet(ImageLoader.loadImage("/textures/calcswitch.png"));
        SpriteSheet homePic = new SpriteSheet(ImageLoader.loadImage("/textures/home.jpg"));
        SpriteSheet gameoverPic = new SpriteSheet(ImageLoader.loadImage("/textures/gameover.png"));
        SpriteSheet gameendPic = new SpriteSheet(ImageLoader.loadImage("/textures/gameend.png"));
        
        //jumpscare
        SpriteSheet[] jumpscarePic = new SpriteSheet[3];
        jumpscarePic[0] = new SpriteSheet(ImageLoader.loadImage("/textures/jumpscare0.jpg"));
        jumpscarePic[1] = new SpriteSheet(ImageLoader.loadImage("/textures/jumpscare1.jpg"));
        jumpscarePic[2] = new SpriteSheet(ImageLoader.loadImage("/textures/jumpscare2.jpg"));
        
        //calculator
        SpriteSheet[] calculatorPic = new SpriteSheet[8];
        calculatorPic[0] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator0.png"));
        calculatorPic[1] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator1.png"));
        calculatorPic[2] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator2.png"));
        calculatorPic[3] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator3.png"));
        calculatorPic[4] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator4.png"));
        calculatorPic[5] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator5.png"));
        calculatorPic[6] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator6.png"));
        calculatorPic[7] = new SpriteSheet(ImageLoader.loadImage("/textures/calculator7.png"));
        
        //background
        tutorial = tutorialPic.crop(0, 0, 3264, 2448);
        work = workPic.crop(0, 0, 3264, 2448);
        calcswitch = calcswitchPic.crop(0, 0, 600, 60);
        home = homePic.crop(0, 0, 1920, 1080);
        gameover = gameoverPic.crop(0, 0, 1920, 1080);
        gameend = gameendPic.crop(0, 0, 1920, 1080);
        
        //jumpscare
        for(int i = 0; i < 3; i++){
            jumpscare[i] = jumpscarePic[i].crop(0, 0, 3264, 2448);
        }
        
        //calculator
        for(int i = 0; i < 8; i++){
            calculator[i] = calculatorPic[i].crop(0, 0, 1920, 1364);
        }
        
        sounds();
    }
    
    private static void sounds(){
        File pullUpClip = new File("res/sounds/CAMERA_VIDEO_LOA_60105303.wav");
        File putDownClip = new File("res/sounds/put down.wav");
        File ambience0Clip = new File("res/sounds/ambience2.wav");
        File ambience1Clip = new File("res/sounds/ColdPresc B.wav");
        File ambience2Clip = new File("res/sounds/EerieAmbienceLargeSca_MV005.wav");
        File walk0Clip = new File("res/sounds/deep steps.wav");
        File walk1Clip = new File("res/sounds/Laugh_Giggle_Girl_8d.wav");
        File walk2Clip = new File("res/sounds/pirate song2.wav");
        File jumpscareSoundClip = new File("res/sounds/XSCREAM.wav");
        File errorClip = new File("res/sounds/error.wav");
        File runClip = new File("res/sounds/run.wav");
        File powerdownClip = new File("res/sounds/powerdown.wav");
        File musicboxClip = new File("res/sounds/music box.wav");
        File darknessMusicClip = new File("res/sounds/darkness music.wav");
        File cheerClip = new File("res/sounds/CROWD_SMALL_CHIL_EC049202.wav");
        File helpClip = new File("res/sounds/help.wav");
        
        try {
            pullUp = AudioSystem.getClip();
            pullUp.open(AudioSystem.getAudioInputStream(pullUpClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            putDown = AudioSystem.getClip();
            putDown.open(AudioSystem.getAudioInputStream(putDownClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ambience0 = AudioSystem.getClip();
            ambience0.open(AudioSystem.getAudioInputStream(ambience0Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ambience1 = AudioSystem.getClip();
            ambience1.open(AudioSystem.getAudioInputStream(ambience1Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ambience2 = AudioSystem.getClip();
            ambience2.open(AudioSystem.getAudioInputStream(ambience2Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            walk0 = AudioSystem.getClip();
            walk0.open(AudioSystem.getAudioInputStream(walk0Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            walk1 = AudioSystem.getClip();
            walk1.open(AudioSystem.getAudioInputStream(walk1Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            walk2 = AudioSystem.getClip();
            walk2.open(AudioSystem.getAudioInputStream(walk2Clip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            jumpscareSound = AudioSystem.getClip();
            jumpscareSound.open(AudioSystem.getAudioInputStream(jumpscareSoundClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            error = AudioSystem.getClip();
            error.open(AudioSystem.getAudioInputStream(errorClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            run = AudioSystem.getClip();
            run.open(AudioSystem.getAudioInputStream(runClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            powerdown = AudioSystem.getClip();
            powerdown.open(AudioSystem.getAudioInputStream(powerdownClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            musicbox = AudioSystem.getClip();
            musicbox.open(AudioSystem.getAudioInputStream(musicboxClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            darknessMusic = AudioSystem.getClip();
            darknessMusic.open(AudioSystem.getAudioInputStream(darknessMusicClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            cheer = AudioSystem.getClip();
            cheer.open(AudioSystem.getAudioInputStream(cheerClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            help = AudioSystem.getClip();
            help.open(AudioSystem.getAudioInputStream(helpClip));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
