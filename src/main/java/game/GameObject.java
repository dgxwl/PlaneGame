package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class GameObject {

	protected static final int ALIVE = 0;
	protected static final int DEAD = 1;
	protected static final int RECYCLABLE = 2;

	protected static Random random = new Random();

	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int state = RECYCLABLE;

	public static Image loadImage(String fileName) {
		try {
			return ImageIO.read(new File("src/main/java/img/" + fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public GameObject(int width, int height) {
		this.width = width;
		this.height = height;
	}

	protected abstract Image getImage();

	public void paintObject(Graphics g) {
		g.drawImage(getImage(), x, y, null);
	}

	public abstract void step();

	public abstract boolean isOutOfBound();

	public boolean isAlive() {
		return state == ALIVE;
	}

	public boolean isDead() {
		return state == DEAD;
	}

	public boolean isRecyclable() {
		return state == RECYCLABLE;
	}

	public boolean isCollide(GameObject another) {
		int x1 = x - another.width;
		int x2 = x + width;
		int y1 = y - another.height;
		int y2 = y + height;
		int x = another.x;
		int y = another.y;
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	public void die() {
		state = DEAD;
	}
}
