
public class ShellAnimation implements Animation {

	private static int universeCount = 0;
	
	public static int getUniverseCount() {
		return universeCount;
	}

	public static void setUniverseCount(int count) {
		ShellAnimation.universeCount = count;
	}

	public Universe getNextUniverse() {

		universeCount++;
		
		if (universeCount == 1) {
			return new MazeUniverse();
		}
		else if (universeCount == 2) {
			return new CakeUniverse();
		}
		else {
			return null;
		}

	}
	
}
