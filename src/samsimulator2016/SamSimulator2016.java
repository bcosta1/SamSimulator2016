/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsimulator2016;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Brandon
 */
public class SamSimulator2016 extends Canvas implements Runnable{
    
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int WIDTH = (int)screenSize.getWidth();
    public static final int HEIGHT = (int)screenSize.getHeight();
    
    private Thread thread;
    private boolean running = false;
    
    private Handler handler;
    private HUD hud;
    private Menu menu;
    
    public static State gameState = State.Menu;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SamSimulator2016();
    }
    
    public SamSimulator2016(){
        handler = new Handler();
        hud = new HUD();
        menu = new Menu(handler, hud);
        
        new Window(WIDTH, HEIGHT, "SamSimulator2016", this);
        
        this.addMouseListener(hud);
        this.addMouseListener(menu);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        Assets.init();
        Assets.darknessMusic.start();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void tick(){
        handler.tick();
        if(gameState == State.Game){
            hud.tick();
        }else if(gameState == State.Menu || gameState == State.GameEnd || gameState == State.GameOver){
            menu.tick();
        }
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        //DRAW HERE
        
        
        handler.render(g);
        
        if(gameState == State.Game){
            hud.render(g);
        }else if(gameState == State.Menu || gameState == State.GameEnd || gameState == State.GameOver){
            menu.render(g);
        }
        //DRAW HERE
        
        g.dispose();
        bs.show();
    }
    
    public static int clamp(int var, int min, int max){
        if(var >= max){
            return var = max;
        }else if(var <= min){
            return var = min;
        }else{
            return var;
        }
    }
    
}
