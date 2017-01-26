/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drgnst.Game.gfx;

/**
 *
 * @author William
 */
public class Bitmap3D extends Bitmap{
    
    private double fov = height;
    
    public Bitmap3D(int widht, int height) {
        super(widht, height);
    }
    
    int t;
    
    double xCam = 0;
    double yCam = 0;
    double zCam = 4;
    double rot = 0;
    
    public void render(){
        t++;
        
        xCam = t / 10.0;
        yCam = t / 10.0;
        zCam = Math.sin(t / 30.0);
        
        rot = t / 100.0;
        
        double rSin = Math.sin(rot);
        double rCos = Math.cos(rot);
        
        for(int y = 0; y < height; y++){
            double yd = (y - (height / 2)) / fov;
            double zd = (6 + zCam) / yd;
            if(yd < 0)
                zd = (6 - zCam) / -yd;
            
            
            for(int x = 0; x < width; x++){
                double xd = (x - (width / 2)) / fov;
                xd *= zd;
                
                int xPix = (int)(xd * rCos - zd * rSin + xCam);
                int yPix = (int)(xd * rSin + zd * rCos + yCam);
                
                pixels[x + y * width] = ((yPix & 15) * 16) << 8 | ((xPix & 15) * 16);
            }
        }
    }
    
}
