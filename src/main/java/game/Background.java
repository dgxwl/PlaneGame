package game;

import java.awt.Graphics;
import java.awt.Image;

public class Background extends GameObject {

	private static Image img;

	static {
		img = GameObject.loadImage("sky.png");
	}

	private int y1;
	private int speed;

	public Background() {
		super(Window.WIDTH, Window.HEIGHT);
		x = 0;
		y = 0;
		y1 = -height;
		speed = 2;
	}

	@Override
	protected Image getImage() {
		return img;
	}

	@Override
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
		g.drawImage(getImage(), x, y1, null);
	}

	@Override
	public void step() {
		y += speed;
		y1 += speed;
		if (y >= Window.HEIGHT) {
			y = -Window.HEIGHT;
		}
		if (y1 >= Window.HEIGHT) {
			y1 = -Window.HEIGHT;
		}
	}

	@Override
	public boolean isOutOfBound() {
		return false;
	}
}
