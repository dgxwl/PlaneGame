package game;

import java.util.Arrays;
import java.util.List;

public class EnemyFactory {
	
	private static GameObject[] enemies = new GameObject[20];
	
	static {
		for (int i = 0; i < 13; i++) {
			enemies[i] = new SmallPlane();
		}
		for (int i = 13; i < 19; i++) {
			enemies[i] = new BigPlane();
		}
		enemies[19] = new Bird();
	}
	
	public static List<GameObject> getEnemies() {
		return Arrays.asList(enemies);
	}
	
	public static GameObject appear() {
		while (true) {
			int index = GameObject.random.nextInt(20);
			if (enemies[index].isAlive()) {
				continue;
			}
			if (enemies[index].isRecyclable()) {
				enemies[index].x = GameObject.random.nextInt(Window.WIDTH - enemies[index].width);
				enemies[index].y = -enemies[index].height;
				enemies[index].state = GameObject.ALIVE;
				if (enemies[index] instanceof SmallPlane) {
					((SmallPlane) enemies[index]).setLife(1);
				}
				if (enemies[index] instanceof BigPlane) {
					((BigPlane) enemies[index]).setLife(3);
				}
				if (enemies[index] instanceof Bird) {
					Bird b = (Bird) enemies[index];
					b.xSpeed = 3 * (GameObject.random.nextInt() > 0 ? 1 : -1);
					b.awardType = GameObject.random.nextInt(2);
					return b;
				}
				return enemies[index];
			}
		}
	}
	
	public static void refreshAll() {
		for (GameObject g : enemies) {
			g.state = GameObject.RECYCLABLE;
		}
	}
}
