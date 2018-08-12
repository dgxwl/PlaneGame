package game;

import java.awt.Image;

public class Hero extends GameObject {

	private static Image imgs[];

	static {
		imgs = new Image[3];
		for (int i = 0; i < imgs.length; i++) {
			imgs[i] = GameObject.loadImage("hero" + i + ".png");
		}
	}

	private int num;
	protected int firepower;

	public Hero() {
		super(98, 124);
		x = 140;
		y = 400;
		num = 3;
		firepower = 0;
		state = ALIVE;
	}

	int index = 0;

	@Override
	protected Image getImage() {
		if (isAlive()) {
			index++;
			index = index % 2;
			return imgs[index];
		} else if (isDead()) {
			return imgs[2];
		} else {
			return null;
		}
	}

	@Override
	public void step() {
	}

	public void moveTo(int x, int y) {
		this.x = x - width / 2;
		this.y = y - height / 2;
	}
	
	@Override
	public boolean isOutOfBound() {
		return false;
	}

	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	public void addLife() {
		num++;
	}

	public void subLife() {
		num--;
		if (num <= 0) {
			state = DEAD;
		}
	}

	public void addFirepower() {
		firepower += 50;
	}

	public void clearFirepower() {
		firepower = 0;
	}
}
