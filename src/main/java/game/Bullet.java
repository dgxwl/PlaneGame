package game;

import java.awt.Image;

public class Bullet extends GameObject {

	private static Image img;

	static {
		img = GameObject.loadImage("bullet.png");
	}

	private int speed;

	public Bullet(int x, int y) {
		super(8, 14);
		this.x = x;
		this.y = y;
		speed = 5;
		state = RECYCLABLE;
	}

	int deadColddown = 0;
	@Override
	protected Image getImage() {
		if (isAlive()) {
			return img;
		} else if (isDead()) {
			deadColddown++;
			if (deadColddown % 10 == 0) {
				state = RECYCLABLE;
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public void step() {
		if (isAlive()) {
			y -= speed;
		}
	}

	@Override
	public boolean isOutOfBound() {
		return (y + height) <= 0;
	}

}
