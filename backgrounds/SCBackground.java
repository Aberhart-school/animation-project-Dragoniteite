import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SCBackground implements Background {
	public static int TILE_WIDTH = 50;
	public static int TILE_HEIGHT = 50;
	private Image moss;
    private Image cobble;
    private Image apple;
    private Image lever;
    private int maxRows;
    private int maxCols;
    private int map[][] = new int[][] { 
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1},
		{1,0,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1},
		{1,0,1,0,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,1,0,1},
		{1,0,1,0,1,0,1,1,1,0,1,0,1,1,1,1,1,1,1,0,1,1,0,1},
		{1,0,1,0,1,0,1,1,1,0,1,0,0,0,0,0,0,1,1,0,1,1,0,1},
		{1,0,1,3,1,0,1,1,1,0,1,0,1,1,1,1,0,0,0,0,1,1,0,1},
		{1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,1,1,0,1,1,1,1,0,1},
		{1,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,1,1,1,0,0,1},
		{1,0,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,1,1},
		{1,0,1,1,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,2,1},
		{1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1},
		{1,0,1,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
		{1,0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
		{1,0,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,0,1},
		{1,0,1,1,1,1,1,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1},
		{1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,1},
		{1,3,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
    public SCBackground() {
    	try {
    		this.moss = ImageIO.read(new File("res/sc/moss.png"));
    		this.cobble = ImageIO.read(new File("res/sc/cobble.jpg"));
    		this.apple = ImageIO.read(new File("res/sc/apple.jpg"));
    		this.lever = ImageIO.read(new File("res/sc/lever.jpg"));
    	}
    	catch (IOException exception) {
    		System.out.println("Doesn't work");
    	}
    	maxRows = map.length - 1;
    	maxCols = map[0].length - 1;
    }
	
	public Tile getTile(int col, int row) {
			
		Image image = null;
			
		if (row < 0 || row > maxRows || col < 0 || col > maxCols) {
			image = null;
		}
		else if (map[row][col] == 0 || map[row][col] == 2 || map[row][col] == 3 || map[row][col] == 4) {
			image = cobble;
		}
		else if (map[row][col] == 1) {
			image = moss;
		}
		int x = (col * TILE_WIDTH);
		int y = (row * TILE_HEIGHT);
			
		Tile newTile = new Tile(image, x, y, TILE_WIDTH, TILE_HEIGHT, false);
			
		return newTile;
	}
			
	public int getCol(double x) {
		int col = 0;
		if (TILE_WIDTH != 0) {
			col = (int) (x / TILE_WIDTH);
			if (x < 0) {
				return col - 1;
			}
		}
		return col;
	}
	
	public int getRow(double y) {
		int row = 0;
		if (TILE_HEIGHT != 0) {
			row = (int) (y / TILE_HEIGHT);
			if (y < 0) {
				return row - 1;
			}
		}
		return row;
	}
	public ArrayList<DisplayableSprite> getBarriers() {
		ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 1) {
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
				}
			}
		}
		return barriers;
	}
	public ArrayList<DisplayableSprite> getDoors() {
		ArrayList<DisplayableSprite> doors = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 4) {
					doors.add(new DoorSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, true));
				}
			}
		}
		return doors;
	}
	public ArrayList<DisplayableSprite> getLevers() {
		ArrayList<DisplayableSprite> levers = new ArrayList<DisplayableSprite>();
		boolean firstLever = false;
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 3 && firstLever == false) {
					levers.add(new LeverSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH , (row + 1) * TILE_HEIGHT, true));
					firstLever = true;
				}
				else if (map[row][col] == 3 && firstLever) {
					levers.add(new OtherLeverSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH , (row + 1) * TILE_HEIGHT, true));
				}
			}
		}
		return levers;
	}
	public ArrayList<DisplayableSprite> getTeleportPad() {
		ArrayList<DisplayableSprite> tpPad = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 2) {
					tpPad.add(new TeleportPadSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, true));
				}
			}
		}
		return tpPad;
	}
	@Override
	public double getShiftX() {
		return 0;
	}

	@Override
	public double getShiftY() {
		return 0;
	}


	@Override
	public void setShiftX(double shiftX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShiftY(double shiftY) {
		// TODO Auto-generated method stub
		
	}

}