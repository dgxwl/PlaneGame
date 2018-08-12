package game;

import java.awt.Image;

public class SmallPlane extends GameObject implements Enemy {

	private static Image[] imgs;
	
	static {
		imgs = new Image[2];
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = GameObject.loadImage("smallplane" + i + ".png");
		}
	}
	
	private int speed;
	private int life;
	
	public SmallPlane() {
		super(50, 35);
		speed = 3;
		life = 1;
	}

	@Override
	public int getScore() {
		return 1;
	}
	
	@Override
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	@Override
	public void subLife() {
		life--;
		if (life <= 0) {
			state = DEAD;
		}
	}

	int deadColddown = 0;
	@Override
	protected Image getImage() {
		if (isAlive()) {
			return imgs[0];
		} else if (isDead()) {
			deadColddown++;
			if (deadColddown % 10 == 0) {
				state = RECYCLABLE;
			}
			return imgs[1];
		} else {
			return null;
		}
	}

	@Override
	public void step() {
		y += speed;
	}
	
	@Override
	public boolean isOutOfBound() {
		return y >= Window.HEIGHT;
	}

}
