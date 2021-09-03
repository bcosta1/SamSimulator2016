/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samsimulator2016;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Brandon
 */
public abstract class GameObject {
    
    protected float x, y;
    protected ID id;
    protected float volX, volY;
    
    public GameObject(float x, float y, ID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();
    
    public void setX(float x){
        this.x = x;
    }
    public void setY(float x){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setID(ID id){
        this.id = id;
    }
    public ID getID(){
        return id;
    }
    public void setVolX(float volX){
        this.volX = volX;
    }
    public void setVolY(float volY){
        this.volY = volY;
    }
    public float getVolX(){
        return volX;
    }
    public float getVolY(){
        return volY;
    }
}
