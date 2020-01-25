package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import gamestuff.Direction;

public class GameWindow extends JLayeredPane {
	public static Font FONT;
	private Timer timer;
	private Ground ground = new Ground(null);
	private JLabel scoreLabel = new JLabel("Score: 0");
	private JLabel levelLabel = new JLabel("No Maze", JLabel.CENTER);
	private JLabel speedLabel = new JLabel("Speed: 1", JLabel.CENTER);

	public GameWindow() {
		setBackground(Window.BACKGROUND_COLOR);
		setOpaque(true);
		setBounds(0, 0, 1920, 1080);
		add(ground, JLayeredPane.DEFAULT_LAYER);
		try {
			FONT = Font.createFont(Font.TRUETYPE_FONT, new File("src/others/font.ttf")).deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(FONT);
			scoreLabel.setFont(new Font(FONT.getName(), Font.PLAIN, 50));
			levelLabel.setFont(new Font(FONT.getName(), Font.PLAIN, 50));
			speedLabel.setFont(new Font(FONT.getName(), Font.PLAIN, 50));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		scoreLabel.setBounds(130, 970, 400, 80);
		levelLabel.setBounds(630, 970, 400, 80);
		speedLabel.setBounds(1130, 970, 400, 80);
		scoreLabel.setForeground(Color.white);
		levelLabel.setForeground(Color.white);
		speedLabel.setForeground(Color.white);
		add(scoreLabel, JLayeredPane.DEFAULT_LAYER);
		add(levelLabel, JLayeredPane.DEFAULT_LAYER);
		add(speedLabel, JLayeredPane.DEFAULT_LAYER);
		Button newGame = new Button("New Game", 1650, 220, 210, 75);
		Button highscores = new Button("Highscores", 1650, 345, 210, 75);
		add(newGame, JLayeredPane.DEFAULT_LAYER);
		add(highscores, JLayeredPane.DEFAULT_LAYER);
		newGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showPlayWindow();
			}
		});
		highscores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.showScoreWindow();
			}
		});
	}

	public void start(int speed) {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				ground.moveSnake();
			}

		}, 500 - speed * 50, 500 - speed * 50);
	}

	public void stop() {
		timer.cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				ground.flash();
			}

		}, 0, 400);
		String level = levelLabel.getText();
		if(!level.equals("Custom")) {
			int speed = Integer.valueOf(speedLabel.getText().substring(7));
			int score = Integer.valueOf(scoreLabel.getText().substring(7));
			if (Screen.isSlotAvailable(level, speed, score)) {
				Screen.saveScore(level, speed, score);
			}
		}
	}

	public void newGame(Block[][] preset, String level, int speed) {
		timer.cancel();
		remove(ground);
		ground = new Ground(preset);
		add(ground, JLayeredPane.DEFAULT_LAYER);
		scoreLabel.setText("Score: 0");
		levelLabel.setText(level);
		speedLabel.setText("Speed: " + (speed + 1));
		start(speed);
	}

	public void changeDirection(Direction direction) {
		ground.changeDirection(direction);
	}

	public void addScore() {
		scoreLabel.setText("Score: " + (Integer.parseInt(scoreLabel.getText().substring(7)) + 1));
	}

	public void gameOver(boolean isWin) {
		stop();
	}
}
