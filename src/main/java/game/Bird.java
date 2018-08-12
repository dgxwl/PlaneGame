package game;

import java.awt.Image;

public class Bird extends GameObject implements Award {

	private static Image[] imgs;

	static {
		imgs = new Image[2];
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = GameObject.loadImage("bird" + i + ".png");
		}
	}

	protected int xSpeed;
	protected int ySpeed;
	protected int awardType;
	
	public Bird() {
		super(50, 50);
		xSpeed = 3 * (random.nextInt() > 0 ? 1 : -1);
		ySpeed = 2;
		awardType = random.nextInt(2);
	}

	public int getAwardType() {
		return awardType;
	}

	int deadColddown = 0;
	@Override
	protected Image getImage() {
		if (isAlive()) {
			return imgs[0];
		} else if (isDead()) {
			deadColddown++;
			if (deadColddown % 20 == 0) {
				state = RECYCLABLE;
			}
			return imgs[1];
		} else {
			return null;
		}
	}

	@Override
	public void step() {
		x += xSpeed;
		if (x <= 0 || x >=Window.WIDTH - width) {
			xSpeed *= -1;
		}
		y += ySpeed;
	}

	@Override
	public boolean isOutOfBound() {
		return y >= Window.HEIGHT;
	}

}
