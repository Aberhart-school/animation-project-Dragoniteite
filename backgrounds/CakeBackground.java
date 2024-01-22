import java.awt.Image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CakeBackground implements Background {
	public static int TILE_WIDTH = 50;
	public static int TILE_HEIGHT = 50;
	private Image rainbow;
    private Image blue;
    private Image apple;
    private Image lever;
    private int maxRows;
    private int maxCols;
    private int map[][] = new int[][] { 
		{1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,2,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,2,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,2,0,0,0,0,2,0,1},
		{1,2,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,2,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,2,0,0,2,0,0,0,0,2,1},
		{1,0,0,0,0,0,2,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,2,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1},

	};
    public CakeBackground() {
    	try {
    		this.rainbow = ImageIO.read(new File("res/sc/rainbowtile.jpg"));
    		this.blue = ImageIO.read(new File("res/sc/bluesky.jpg"));
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
			image = blue;
		}
		else if (map[row][col] == 1) {
			image = rainbow;
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
	public ArrayList<DisplayableSprite> getPlatforms() {
		ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 2) {
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 0.3) * TILE_WIDTH, (row + 0.3) * TILE_HEIGHT, true));
				}
			}
		}
		return barriers;
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