package com.mti.graphics3d_6;

import android.graphics.Canvas;
import android.widget.Toast;



public class GameLoopThread extends Thread {

	static final long FPS =20;
	private GameView view;
	private boolean running = false;
	

	public GameLoopThread(GameView view) {
		this.view = view;
	}

	public void setRunning(Boolean run) {
		running = run;
	}
	
	public void run() {
		long ticksPS = 1000 /FPS;
		long startTime;
		long sleepTime;
		
		long startTestTime = System.currentTimeMillis();
		long endTestTime;
		
		while(running) {
			Canvas c = null;
			startTime = System.currentTimeMillis();
			
			try {
				c = view.getHolder().lockCanvas();
				synchronized(view.getHolder()) {
					view.onDraw(c);
				}  // end synchronized
			} finally {
				if(c!=null) {
					view.getHolder().unlockCanvasAndPost(c);
				}  // end if
			}  // end finally
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			
			try {
				if(sleepTime > 0) 
					sleep(sleepTime);
				else
					sleep(10);
			} catch(Exception e) {
				
			}
			
			endTestTime = System.currentTimeMillis();
			//  Arbitrary time right now....  should stop in a minute or so. 
			if((endTestTime-startTestTime)>500000) {
				running = false;
			}  // end if
		}  // end while

		
		//  should be out of loop here.
		//  Toast will not work here this way.  Work on this tomorrow.
		//  Toast.makeText(getBaseContext(), "Loop has finished!", Toast.LENGTH_LONG).show();
		
	}	
}
