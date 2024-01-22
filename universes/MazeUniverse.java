import java.util.ArrayList;

public class MazeUniverse implements Universe {


	private static boolean complete = false;	
	private Background background = null;
	private ArrayList<Background> backgrounds = null;
	private DisplayableSprite player1 = null;
	private DisplayableSprite warpPad = null;
	private DisplayableSprite pulledLever = null;
	private static ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private double xCenter = 0;
	private double yCenter = 0;
	private static boolean deleteTheDamnDoor = false;
	private static boolean changeSprite = false;
	private static int deleteSprite = 0;
//	private static int spriteNum = 0;
	public MazeUniverse () {

		background = new SCBackground();
		ArrayList<DisplayableSprite> barriers = ((SCBackground)background).getBarriers();
		ArrayList<DisplayableSprite> levers = ((SCBackground)background).getLevers();
		ArrayList<DisplayableSprite> doors = ((SCBackground)background).getDoors();
		ArrayList<DisplayableSprite> warpPad = ((SCBackground)background).getTeleportPad();
		backgrounds = new ArrayList<Background>();
		backgrounds.add(background);

		player1 = new SCSprite(SCBackground.TILE_HEIGHT * 1.6, SCBackground.TILE_WIDTH * 1.6);
		sprites.addAll(barriers);
		sprites.addAll(levers);
		sprites.addAll(doors);
		sprites.addAll(warpPad);
		sprites.add(player1);
	}

	public double getScale() {
		return 2.2;
	}	
	
	public double getXCenter() {
		return this.player1.getCenterX();
	}

	public double getYCenter() {
		return this.player1.getCenterY();
	}
	
	public void setXCenter(double xCenter) {
		this.xCenter = player1.getCenterX();
	}

	public void setYCenter(double yCenter) {
		this.yCenter = player1.getCenterY();
	}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	public static void complete() {
		complete = true;
	}

	@Override
	public ArrayList<Background> getBackgrounds() {
		return backgrounds;
	}	
	public DisplayableSprite getPlayer1() {
		return player1;
	}

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}
		
	public boolean centerOnPlayer() {
		return true;
	}		
	
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, keyboard, actual_delta_time);
    	} 
		if (deleteTheDamnDoor == true){
			sprites.remove(deleteSprite);
			deleteTheDamnDoor = false;
		}
//		if (changeSprite = true) {
//			changeSprite = false;
//		}
	}

	public String toString() {
		return "The Maze";
	}	
	public static void e (int num){
		deleteTheDamnDoor = true;
		deleteSprite = num;
	}
//	public static void changeLever(int num) {
//		spriteNum = num;
//		changeSprite = true;
//	}

}
