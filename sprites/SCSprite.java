import java.awt.Image;
import java.math.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SCSprite implements DisplayableSprite, MovableSprite, CollidingSprite {
	
	private static Image image;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 40;
	private double height = 45;
	private final double VELOCITY = 200;
	private boolean dispose = false;
	private boolean openDoor = false;
	private int selectedSprite = 0;
	private static int leversPulled = 0;
	private static boolean noclip = false;
	private boolean finish = false;
	public SCSprite(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/sc/android.png"));
				
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}
			
		}

	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}
	public double getMaxX() {
		return centerX + (width / 2);
	}
	public double getMinY() {
		return centerY - (height / 2);
	}
	public double getMaxY() {
		return centerY + (height / 2);
	}
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		double velocityX = 0;
		double velocityY = 0;
			
		if (keyboard.keyDown(37)) {
			velocityX -= VELOCITY;
			try {
				image = ImageIO.read(new File("res/sc/androidfacingleft.png"));
				width = 25;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (keyboard.keyDown(38)) {
			velocityY -= VELOCITY;
			try {
				image = ImageIO.read(new File("res/sc/androidback.png"));
				width = 40;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
			try {
				image = ImageIO.read(new File("res/sc/androidfacingright.png"));
				width = 25;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;
			try {
				image = ImageIO.read(new File("res/sc/android.png"));
				width = 40;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;
		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);
		boolean collidingBarrierY = checkCollisionWithBarrier(universe.getSprites(), 0, deltaY);
		boolean collidingLeverX = checkCollisionWithLever(universe.getSprites(), deltaX, 0);
		boolean collidingLeverY = checkCollisionWithLever(universe.getSprites(), 0, deltaY);
		boolean collidingTeleportPadX = checkCollisionWithTeleportPad(universe.getSprites(), deltaX, 0);
		boolean collidingTeleportPadY = checkCollisionWithTeleportPad(universe.getSprites(), 0, deltaY);
		
		if (collidingBarrierY == false || noclip) {
			this.centerY += deltaY;
			}
		if (collidingBarrierX == false || noclip) {
			this.centerX += deltaX;
		}
		if (collidingLeverX == true && collidingLeverY == true) {
			if (keyboard.keyDownOnce(32) && leversPulled == 1) {
				LeverSprite.leverPulled();
				openDoor = true;
				leversPulled += 1;
			}
			else if (keyboard.keyDownOnce(32) && leversPulled == 0){
				OtherLeverSprite.leverPulled();
				openDoor = true;
				leversPulled += 1;
			}
		}
		if (collidingTeleportPadX == true && collidingTeleportPadY == true) {
			if (keyboard.keyDownOnce(32)) {
				MazeUniverse.complete();
				
			}
		}
	}
	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean currentlyColliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite || sprite instanceof DoorSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX() + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					currentlyColliding = true;
					break;					
				}
		}
	}
		return currentlyColliding;
	}
	private boolean checkCollisionWithLever(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean currentlyColliding = false;
//		int spriteNum = -1;
		for (DisplayableSprite sprite : sprites) {
//			spriteNum = spriteNum + 1;
			if (sprite instanceof OtherLeverSprite && leversPulled == 0) {
				if (CollisionDetection.inside(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX() + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					System.out.println(sprite);
//					selectedSprite = spriteNum;
					currentlyColliding = true;
					break;					
				}
		}
			else if (sprite instanceof LeverSprite && leversPulled == 1) {
				if (CollisionDetection.inside(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX() + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					System.out.println(sprite);
//					selectedSprite = spriteNum;
					currentlyColliding = true;
					break;					
				}
		}
	}	int counter = -1;
		for (DisplayableSprite sprite : sprites) {
			counter = counter + 1;
			if (sprite instanceof DoorSprite && openDoor) {
				MazeUniverse.e(counter);
				openDoor = false;
			}
		}
		return currentlyColliding;
	}
	private boolean checkCollisionWithTeleportPad(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {
		boolean currentlyColliding = false;
		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof TeleportPadSprite) {
				if (CollisionDetection.inside(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX() + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					currentlyColliding = true;
					break;					
				}
		}
	}
		return currentlyColliding;
	}
	@Override
	public void setCenterX(double centerX) {
		this.centerX = centerX;
		
	}
	@Override
	public void setCenterY(double centerY) {
		this.centerY = centerY;
		
	}
	public boolean centerOnPlayer() {
		return true;
	}
	public Image getImage() {
		return image;
	}
	@Override
	public boolean getVisible() {
		return true;
	}
	@Override
	public double getHeight() {
		return height;
	}
	@Override
	public double getWidth() {
		return width;
	}
	@Override
	public double getCenterX() {
		return centerX;
	}
	@Override
	public double getCenterY() {
		return centerY;
	}
	@Override
	public boolean getDispose() {
		return dispose;
	}
	public static void noClipActivator(boolean value) {
		noclip = value;
	}
	public static boolean getNoClip() {
		return noclip;
	}
	@Override
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}

	@Override
	public void setVelocityX(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVelocityY(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		
	}

	public long getScore() {
		return leversPulled;
	}
	
	public static int getLeversPulled() {
		return leversPulled;
	}
	@Override
	public String getProximityMessage() {
		String e = "HELP";
		return e;
	}

	@Override
	public boolean getIsAtExit() {
		return finish;
	}
}

