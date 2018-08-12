package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAMEOVER = 3;
	protected static int status = START;

	private Background background = new Background();

	private List<GameObject> enemies = EnemyFactory.getEnemies();
	private Hero hero = new Hero();
	private List<Bullet> bullets = BulletFactory.getBullets();
	
	public void checkGameover() {
		if (hero.getNum() <= 0) {
			status = GAMEOVER;
		}
	}
	
	public void heroCollideAction() {
		for (GameObject g : enemies) {
			if (g.isAlive() && hero.isAlive() && g.isCollide(hero)) {
				g.die();
				hero.subLife();
				hero.clearFirepower();
				break;
			}
		}
	}
	
	int score = 0;
	public void bulletCollideAction() {
		for (Bullet b : bullets) {
			for (GameObject g : enemies) {
				if (b.isAlive() && g.isAlive() && b.isCollide(g)) {
					b.die();
					if (g instanceof Enemy) {
						((Enemy) g).subLife();
						score += ((Enemy) g).getScore();
					}
					if (g instanceof Award) {
						g.die();
						switch (((Award) g).getAwardType()) {
						case Award.LIFE:
							hero.addLife();
							break;
						case Award.FIREPOWER:
							hero.addFirepower();
							break;
						}
					}
					break;
				}
			}
		}
	}
	
	public void outOfBoundAction() {
		for (GameObject g : enemies) {
			if (g.isOutOfBound()) {
				g.state = GameObject.RECYCLABLE;
			}
		}
		for (Bullet b : bullets) {
			if (b.isOutOfBound()) {
				b.state = GameObject.RECYCLABLE;
			}
		}
	}
	
	public void stepAction() {
		background.step();
		for (GameObject g : enemies) {
			g.step();
		}
		for (Bullet b : bullets) {
			b.step();
		}
	}
	
	int shootIndex = 0;
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 50 == 0) {
			BulletFactory.shoot(hero);
		}
	}

	int enterIndex = 0;
	public void enterAction() {
		enterIndex++;
		if (enterIndex % 50 == 0) {
			EnemyFactory.appear();
		}
	}

	public void action() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (isRunning()) {
					heroCollideAction();
					enterAction();
					shootAction();
					stepAction();
					bulletCollideAction();
					outOfBoundAction();
					checkGameover();
				}
				repaint();
			}
		}, 0, 10);
		
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isStart()) {
					status = RUNNING;
				}
				if (isGameover()) {
					EnemyFactory.refreshAll();
					BulletFactory.refreshAll();
					hero.firepower = 0;
					hero.setNum(3);
					hero.x = 140;
					hero.y = 400;
					hero.state = GameObject.ALIVE;
					score = 0;
					status = START;
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (isRunning()) {
					hero.moveTo(e.getX(), e.getY());
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (isRunning()) {
					status = PAUSE;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isPause()) {
					status = RUNNING;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	@Override
	public void paint(Graphics g) {
		background.paintObject(g);
		hero.paintObject(g);
		for (GameObject e : enemies) {
			e.paintObject(g);
		}
		for (Bullet b : bullets) {
			b.paintObject(g);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 15));
		g.setColor(new Color(0, 0, 0));
		g.drawString("plane × "+hero.getNum(), 20, 40);
		g.drawString("score: "+score, 20, 80);
		
		if (isStart()) {
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.setColor(new Color(255, 255, 255));
			g.drawString("click to start", 120, 300);
		} else if (isPause()) {
			g.setFont(new Font("Arial", Font.PLAIN, 45));
			g.setColor(new Color(255, 255, 255));
			g.drawString("PAUSE", 120, 300);
		} else if (isGameover()) {
			g.setFont(new Font("Arial", Font.PLAIN, 45));
			g.setColor(new Color(255, 255, 255));
			g.drawString("GAMEOVER!", 60, 300);
		}
		
	}

	public static boolean isStart() {
		return status == START;
	}

	public static boolean isRunning() {
		return status == RUNNING;
	}

	public static boolean isPause() {
		return status == PAUSE;
	}

	public static boolean isGameover() {
		return status == GAMEOVER;
	}

	public static void main(String[] args) {
		Window win = new Window();

		JFrame frame = new JFrame("plane!");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(win);
		frame.setVisible(true);

		win.action();
	}
}
