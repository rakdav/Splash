package com.example.splash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
    private volatile boolean playing;
    private Thread gameThread=null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Player player;
    private ArrayList<Star> stars=new ArrayList<>();
    private Enemy[] enemies;
    private int enemyCount=3;


    public GameView(Context context,int screenX,int screenY) {
        super(context);
        player=new Player(context,screenX,screenY);
        surfaceHolder=getHolder();
        paint=new Paint();
        int starNums=100;
        for (int i=0;i<starNums;i++)
        {
            Star star=new Star(screenX,screenY);
            stars.add(star);
        }
        enemies=new Enemy[enemyCount];
        for (int i=0;i<enemyCount;i++)
        {
            enemies[i]=new Enemy(context,screenX,screenY);
        }
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
        player.update();
        for (Star s:stars)
        {
            s.Update(player.getSpeed());
        }
        for (int i=0;i<enemyCount;i++)
        {
            enemies[i].Update(player.getSpeed());
        }
    }
    private void draw()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            canvas=surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            for (Star s:stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(),s.getY(),paint);
            }
            canvas.drawBitmap(player.getBitmap(),player.getX(),player.getY(),paint);
            for (int i=0;i<enemyCount;i++)
            {
                canvas.drawBitmap(enemies[i].getBitmap(),enemies[i].getX(),enemies[i].getY(),paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private void control()
    {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume()
    {
        playing=true;
        gameThread=new Thread(this);
        gameThread.start();
    }
    public void pause()
    {
        playing=false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
