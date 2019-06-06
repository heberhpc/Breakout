/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class CopyOfBreakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 600;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Pause Time - Based on NTSC*/
	private static final double PAUSE_TIME = 1000 / 80 ; 	

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
			
		setupGame();
		playGame();		
	}
	
	private void creatBall() {
		
		int x = (APPLICATION_WIDTH - BALL_RADIUS) / 2  ;
		int y = (APPLICATION_HEIGHT - BALL_RADIUS) / 2 ;
		
		ball = new GOval (x, y, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	
	}

/** Construct the environment where the game occurs */	
	private void setupGame() {
		
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		buildWallOfBricks ();
		createPaddle () ;
		addMouseListeners();
		setupPainel();
		creatBall();
		
		
		
	}

/** Create the paddle*/	
	private void createPaddle() {
	
		double xPaddle = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2 ;
		double yPaddle = APPLICATION_HEIGHT - PADDLE_Y_OFFSET ;
		
		paddle = new GRect(xPaddle, yPaddle, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add (paddle);
	
	}

/** for the paddle respond the mouse movement*/
	public void mouseMoved (MouseEvent e){
		
		double startRange = PADDLE_WIDTH / 2 ;
		double endRange   = APPLICATION_HEIGHT - (PADDLE_WIDTH/2);
		
		int x = e.getX() - (PADDLE_WIDTH/2);
		int y = APPLICATION_HEIGHT - PADDLE_Y_OFFSET;
				
		if ( ( e.getX() >= startRange ) && ( ( e.getX() <= endRange)) ){
			paddle.setLocation(x, y);
		}
	}
	
/** Create the wall of Bricks using a GRect form and store it in a GRect array.*/	
	private void buildWallOfBricks() {
		
		// Create a x y position to a frame. A frame is boundary that involves the Bricks
		double frameWidth =  (BRICK_WIDTH  * NBRICKS_PER_ROW) + ( BRICK_SEP * (NBRICKS_PER_ROW - 1) );
		double frameHeight = (BRICK_HEIGHT * NBRICK_ROWS)     + ( BRICK_SEP * (NBRICK_ROWS     - 1) );
		
		double xFrame = (APPLICATION_WIDTH  - frameWidth ) / 2 ;
		double yFrame =  APPLICATION_HEIGHT - HEIGHT + BRICK_Y_OFFSET ;
				
		GRect frame = new GRect (xFrame, yFrame, frameWidth, frameHeight);
		add(frame);
		
		double x = frame.getX();
		double y = frame.getY();
		
		// Setup Lines
		for (int i = 0 ; i < NBRICK_ROWS ; i++){
			// Setup Columns
			for (int j = 0 ; j < NBRICKS_PER_ROW ; j++){
				wallOfBricks [i][j] = new GRect (x, y, BRICK_WIDTH, BRICK_HEIGHT );
				add (wallOfBricks [i][j]);
				
				wallOfBricks [i][j].setFilled(true);
				wallOfBricks [i][j].setFillColor(wichColor(i));
				wallOfBricks [i][j].setColor(wichColor(i));
				
				x += (BRICK_WIDTH + BRICK_SEP);
				
								
			} // End of A Line Construct. Moving to the next Line
			
			x  = frame.getX();
			y += (BRICK_HEIGHT + BRICK_SEP);
			
		}// End of wall.
		remove (frame);
	}

/** Return the Color for the bricks.*/	
/** Return the Color to Construct the Bricks  */	
	private Color wichColor(int i) {
		Color cor = Color.RED;
		
		switch  (i) {
			case 0  : cor = Color.RED; break;
			case 1  : cor = Color.RED; break;
			case 2  : cor = Color.ORANGE; break;
			case 3  : cor = Color.ORANGE; break;
			case 4  : cor = Color.YELLOW; break;
			case 5  : cor = Color.YELLOW; break;
			case 6  : cor = Color.GREEN; break;
			case 7  : cor = Color.GREEN; break;
			case 8  : cor = Color.CYAN; break;
			case 9  : cor = Color.CYAN; break;
			default : cor = Color.BLACK; break;
		}
		
		return cor;
	
	}
	
	private void randomAngle () {
		
		velocityY = 3.0;
		velocityX  = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) velocityX = -velocityX; 
		
		
	}
	
	
	private void updateBall () {
		
		ball.move(velocityX, velocityY);
			
		
	}
	
	private void playGame() {
		
		randomAngle();
		
		while (true){ 
			updateBall();
			checkForColisionOnBricks();
			checkForColisionOnWall();
			updatePainel();
			pause (PAUSE_TIME);
			
		}
		
		
	}
	
	private void checkForColisionOnWall(){
		
		//hit the bottom
		if ( ball.getY() > (APPLICATION_HEIGHT - BALL_RADIUS)){
			velocityY = -velocityY;
		}
		
		//hit the top
		if ( ball.getY() < 0){
			velocityY = Math.abs(velocityY);
		}
		
		// hit the right
		if (ball.getX() > (APPLICATION_WIDTH - BALL_RADIUS) ){
			velocityX = -velocityX;
		}
		
		// hit the left
		if (ball.getX() < 0 ){
			velocityX = Math.abs(velocityX);
			
		}
	}
	
	private void checkForColisionOnBricks() {
		
		GPoint ballUpLeftCorner    = new GPoint (ball.getX(),ball.getY()); 
		GPoint ballUpRightCorner   = new GPoint (ball.getX()+ BALL_RADIUS, ball.getY()); 
		GPoint ballDownLeftCorner  = new GPoint (ball.getX(), ball.getY() + BALL_RADIUS);
		GPoint ballDownRightCorner = new GPoint (ball.getX()+BALL_RADIUS, ball.getY() + BALL_RADIUS);
		
		if ((getElementAt(ballUpLeftCorner) != null)){
			lastObj = getElementAt(ballUpLeftCorner);
			if (lastObj != null){
				velocityY = Math.abs(velocityY);
				
				if (lastObj != paddle) {
					remove(lastObj);
					remainingBricks--;
				}
				
			}
			
		}else if ((getElementAt(ballUpRightCorner) != null)){
			lastObj = getElementAt(ballUpRightCorner);
			if (lastObj != null){
				velocityX = -Math.abs(velocityX);
				
			if (lastObj != paddle) {
				remove(lastObj);
				remainingBricks--;
			}
			}
		}
		
		if ((getElementAt(ballDownLeftCorner) != null)){
			lastObj = getElementAt(ballDownLeftCorner);
			if (lastObj != null){
				velocityY = -Math.abs(velocityY);
					
			if (lastObj != paddle) {
				remove(lastObj);
				remainingBricks--;
			}
			
			}
		}else if ((getElementAt(ballDownRightCorner) != null)){
			lastObj = getElementAt(ballDownRightCorner);
			if (lastObj != null){
				velocityY = -Math.abs(velocityY);
			
			if (lastObj != paddle) {
				remove(lastObj);
				remainingBricks--;
			}
				
			}
		}
		

	}

	private void updatePainel(){
		String str = "The is still "+remainingBricks+ "bricks" ;
		painel.setLabel(str);
		
		
		
	
	}
	
	private void setupPainel(){
		String str = "The is still "+remainingBricks+ "bricks" ; 
		painel = new GLabel (str, 50, APPLICATION_HEIGHT  );
		add(painel);
		
	}
	
		
		
		
	
	
	
	


	// IVARS
	
/**	An Array thar holds the wall of Bricks*/
	private GRect [][] wallOfBricks = new GRect [NBRICK_ROWS][NBRICKS_PER_ROW];

/** A variable to track the paddle*/
	private GRect paddle;
	
/** Random Generator For random Angles*/
	private RandomGenerator rgen = RandomGenerator.getInstance();

/**	To keep Track the Ball*/
	private GOval ball;

/** Velocity of Ball*/
	private double velocityX, velocityY;

/** Last Object that touch the ball */
	private GObject lastObj;

/** For keep track the removed bricks*/
	private int remainingBricks = NBRICKS_PER_ROW * NBRICK_ROWS;

/** For the painel of bricks*/
	private GLabel painel;

	
}
