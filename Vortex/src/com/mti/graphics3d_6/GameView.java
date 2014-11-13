package com.mti.graphics3d_6;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class GameView extends SurfaceView {
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private int xBH;
	private int yBH;
	private int xBHSpeed;
	private int yBHSpeed;
	private double xBall;
	private double yBall;
	private double xBallSpeed;
	private double yBallSpeed;
	
	private double xBallPos;
	private double yBallPos;
	
	private int boardHeight = 1000;
	private int boardWidth = 1000;
	
	Paint paint;
	
	
	public GameView(Context context) {
		super(context);

		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new Callback() {

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				Random rn = new Random();
				rn.setSeed(System.currentTimeMillis());
				
				xBH = rn.nextInt(1000);
				yBH = rn.nextInt(1000);
				xBHSpeed = rn.nextInt(7)+3;
				yBHSpeed = rn.nextInt(7)+3;
				
				xBall = rn.nextInt(1000);
				yBall = rn.nextInt(1000);		
				xBallSpeed = rn.nextInt(7)+3;
				yBallSpeed = rn.nextInt(7)+3;
				
				paint = new Paint();
						
				gameLoopThread.setRunning(true);
				gameLoopThread.start();	
				
			}  //  end SurfaceCreated

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
								
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				gameLoopThread.destroy();
				
			}			
									
		}
		
		);
		
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		paint.setColor(Color.BLUE);		

		//  BH = black hole  if it is about to be off screen, reverse direction
		//  These are separated into two if statement for x and y coordinates
		//  This was done to make it clearer to understand what is going on here.
		if(xBH > boardWidth) {
			xBHSpeed = -xBHSpeed;
		}
		if(xBH <= 0) {
			xBHSpeed = -xBHSpeed;
		}
		if(yBH > boardHeight) {
			yBHSpeed = -yBHSpeed;
		}
		if(yBH <= 0) {
			yBHSpeed = -yBHSpeed;
		}		

		
		//  We are doing the same thing here with the ball that we did with the black hole.
		if(xBall > boardWidth) {
			xBallSpeed = -xBallSpeed;
		}
		if(xBall <= 0) {
			xBallSpeed = -xBallSpeed;
		}
		if(yBall > boardHeight) {
			yBallSpeed = -yBallSpeed;
		}
		if(yBall <= 0) {
			yBallSpeed = -yBallSpeed;
		}			
		
		paint.setStrokeWidth(1);

		double xpos, ypos;
		double lastXpos, lastYpos;
		double gravity;
		double angle;
		
		
		lastXpos = 0;
		lastYpos = 0;
				

		
		// Graph will be created by cross-secting lines.  First one way
		for(int i = 0; i <= boardWidth; i += 20) {
			for(int j = 0; j <= boardHeight; j += 20) {
				
				gravity =  1000000/(getDistance(i,j, xBH, yBH) * getDistance(i,j, xBH, yBH));
				
				
				xpos = (i * getWidth()/boardWidth/2) + (j * getWidth()/boardHeight/2);
				
				ypos = getHeight()/2 + (i * getHeight()/boardHeight/2) - (j * getHeight()/boardWidth/2)+ gravity;

				//  If first point is not the start of a line
				if(j!=0) {
					canvas.drawLine((int) xpos, (int) ypos, (int) lastXpos, (int) lastYpos, paint);
				}				

				
				lastXpos = xpos;
				lastYpos = ypos;
				
			}
			
		}
		
		
		// Now draw the lines going in the perpendicular direction
		for(int j = 0; j <= boardWidth; j += 20) {
			for(int i = 0; i <= boardHeight; i += 20) {
				
				gravity =  1000000/(getDistance(i,j,xBH, yBH) * getDistance(i,j, xBH, yBH));
				
				
				xpos = (i * getWidth()/boardWidth/2) + (j * getWidth()/boardHeight/2);
				
				ypos = getHeight()/2 + (i * getHeight()/boardHeight/2) - (j * getHeight()/boardWidth/2)+ gravity;
				
				// Again if first point is not the start of a line
				if(i!=0) {
					canvas.drawLine((int) xpos, (int) ypos, (int) lastXpos, (int) lastYpos, paint);
				}

				lastXpos = xpos;
				lastYpos = ypos;
			}
			
		}
		
		gravity =  1000000/(getDistance(xBall,yBall,xBH, yBH) * getDistance(xBall,yBall, xBH, yBH));
		
		xBallPos = (xBall * getWidth()/boardWidth/2) + (yBall * getWidth()/boardHeight/2);		
		yBallPos = getHeight()/2 + (xBall * getHeight()/boardHeight/2) - (yBall * getHeight()/boardWidth/2)+ gravity;
		
		
		paint.setColor(Color.GREEN);
		canvas.drawCircle((int) xBallPos, (int) yBallPos,10,paint);
	

		// Update black hole and ball positions
		xBH += xBHSpeed;
		yBH += yBHSpeed;
		
		xBall += xBallSpeed;
		yBall += yBallSpeed;
		
		//  The following will write several variables to the screen.  Used for testing.
		paint.setTextSize(40);
		canvas.drawText("width = " + getWidth() + " height = " + getHeight(), 20, 50, paint);

		
		canvas.drawText("XSpeed : " + xBallSpeed + " YSpeed : " + yBallSpeed, 20, 80, paint);
		canvas.drawText("Gravity : " + gravity, 20, 120, paint);
		
		//  Only worry about gravity if it will have some meaningful impact on the ball movement.
		if(gravity>10) {
			
			//  Let's change the speed now
			//xBallSpeed = xBallSpeed + 10/((xBH - xBall)*(xBH - xBall));
			//yBallSpeed = yBallSpeed + 10/((yBH - yBall)*(yBH - yBall));

			//xBallSpeed = gravity * Math.sin(d);
			
			//  angle will be inverse tan of (x-x0)/(y-y0)
			//  then, x component will be sin(angle) * gravity
			// y component will be cos(angle) * gravity
			angle = Math.atan((xBH - xBall)/(yBH - yBall));

			xBallSpeed = xBallSpeed + .01 * (Math.sin(angle))*gravity;
			yBallSpeed = yBallSpeed + .01 * (Math.cos(angle))*gravity;
		}
		
	}  // end onDraw
	
	private double getDistance(double xPoint1, double yPoint1, double xPoint2, double yPoint2) {
		double distance;
	
		distance = Math.sqrt((xPoint1-xPoint2) * (xPoint1-xPoint2) + (yPoint1-yPoint2) *(yPoint1 - yPoint2));
		
		return distance;
	}		
	
}
