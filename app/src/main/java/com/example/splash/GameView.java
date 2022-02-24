package com.example.splash;

import android.content.Context;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private volatile boolean playing;
    private Thread gameThread=null;

    public GameView(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while (playing)
        {
            update();
            draw();
            control();
        }
    }
    private void update()
    {

    }
    private void draw()
    {

    }
    private void control()
    {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void resume()
    {
        playing=true;
        gameThread=new Thread(this);
        gameThread.start();
    }
    private void pause()
    {
        playing=false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
