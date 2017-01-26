/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drgnst.Game.gfx;

import java.util.Random;


/**
 *
 * @author William
 */
public class Screen extends Bitmap{
    
    public Random r = new Random();
    public Bitmap test;
    public Bitmap3D perspectiveVision;
    
    public Screen(int widht, int height) {
        super(widht, height);
        
        test = new Bitmap(50, 50);
        
        for(int i = 0; i < test.pixels.length; i++){
            test.pixels[i] = r.nextInt();
        }
        
        perspectiveVision = new Bitmap3D(widht, height);
    }
    
    int t;
    
    public void render(){
        
        t++;
        
        int ox = (int) (Math.sin(t / 1000.0) * width / 2);
        int oy = (int) (Math.cos(t / 1000.0) * height / 2);
        clear();
        perspectiveVision.render();
        render(perspectiveVision, 0 , 0);
        //render(test, (width - 50) / 2 + ox, (height - 50) / 2 + oy);
        
    }
    
    public void update(){
        
    }
    
}
