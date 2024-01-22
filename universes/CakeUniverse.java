import java.util.ArrayList;

public class CakeUniverse implements Universe {


	private static boolean complete = false;	
	private Background background = null;
	private ArrayList<Background> backgrounds = null;
	private DisplayableSprite player1 = null;
	private static ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private double xCenter = 0;
	private double yCenter = 0;
	private static int deleteSprite = 0;
	public CakeUniverse () {

		background = new CakeBackground();
		ArrayList<DisplayableSprite> barriers = ((CakeBackground)background).getBarriers();
		ArrayList<DisplayableSprite> platforms = ((CakeBackground)background).getPlatforms();
		backgrounds = new ArrayList<Background>();
		backgrounds.add(background);
		player1 = new FinalRoomSprite(SCBackground.TILE_HEIGHT * 1.6, SCBackground.TILE_WIDTH * 1.6);
		sprites.add(player1);
		sprites.addAll(barriers);
		sprites.addAll(platforms);
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
		return false;
	}		
	
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, keyboard, actual_delta_time);
    	} 
		}

	public String toString() {
		return "The Cake is not a Lie?";
	}	
	public static void e (int num){
		deleteSprite = num;
	}

}
