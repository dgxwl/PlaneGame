package game;

import java.util.Arrays;
import java.util.List;

public class BulletFactory {

	private static Bullet[] bullets = new Bullet[12];
	
	static {
		for (int i = 0; i < bullets.length; i++) {
			bullets[i] = new Bullet(0, 0);
		}
	}
	
	public static List<Bullet> getBullets() {
		return Arrays.asList(bullets);
	}
	
	public static void shoot(Hero hero) {
		if (hero.firepower > 0) {
			generateBullet(hero, hero.x + hero.width/4, hero.y);
			generateBullet(hero, hero.x + hero.width/4*3, hero.y);
			hero.firepower -= 2;
		} else {
			generateBullet(hero, hero.x + hero.width/4, hero.y);
		}
	}
	
	private static void generateBullet(Hero hero, int x, int y) {
		while (true) {
			int index = GameObject.random.nextInt(12);
			if (bullets[index].isAlive()) {
				continue;
			}
			if (bullets[index].isRecyclable()) {
				bullets[index].x = x;
				bullets[index].y = y;
				bullets[index].state = GameObject.ALIVE;
				return ;
			}
		}
	}
	
	public static void refreshAll() {
		for (Bullet b : bullets) {
			b.state = GameObject.RECYCLABLE;
		}
	}
}
