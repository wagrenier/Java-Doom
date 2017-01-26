/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drgnst.Game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import org.drgnst.Game.gfx.Screen;

/**
 *
 * @author William
 */
public class Game extends Canvas implements Runnable{
    public static final long serialVersionUID = 1L;
    
    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH * 3 / 4;
    public static final int SCALE = 1;
    public static final String TITLE = "Perspective";
    public static final double FRAME_LIMIT = 60.0;
    public static final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private boolean isRunning = false;
    public static final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    private Screen screen;
    
    public Game(){
        screen = new Screen(WIDTH, HEIGHT);
    }
    
    
    public void start() {
        if(isRunning)
            return;
        
        isRunning = true;
        
        new Thread(this).start();
    }
    @Override
    public void run() {
        
        final double nsPerUpdate = 1000000000.0 / FRAME_LIMIT;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        int frames = 0;
        int updates = 0;
        
        long frameCounter = System.currentTimeMillis();
        
        while(isRunning){
            long currentTime = System.nanoTime();
            long passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime += passedTime;
            
            if(unprocessedTime >= nsPerUpdate){
                unprocessedTime = 0;
                update();
                updates++;
            }
            
            
            render();
            frames++;
            
            if(System.currentTimeMillis() - frameCounter >= 1000){
                System.out.println("Frames : " + frames + ", Updates : " + updates);
                frames = 0;
                updates = 0;
                frameCounter += 1000;
            }
        }
        
        dispose();
    }
    
    public void dispose() {
        System.exit(0);
    }
    
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        for(int i =0; i < pixels.length; i++){
            pixels[i] = screen.pixels[i];
        }
        
        screen.render();
        
        g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        
        g.dispose();
        
        bs.show();
    }
    
    public void update(){
        screen.update();
    }
    
    public void stop(){
        if(isRunning)
            return;
        
        isRunning = false;
    }
    
    public static void main (String[] args){
        JFrame frame = new JFrame();
        frame.setTitle(TITLE);
        frame.setResizable(false);
        Game game = new Game();
        Dimension d = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
        game.setMinimumSize(d);
        game.setMaximumSize(d);
        game.setPreferredSize(d);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        
        game.start();
        
    } 
}
