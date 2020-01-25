package windows;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import gamestuff.Direction;
import managers.AudioManager;

public class Screen extends JFrame {
	private static Screen screen;
	private static AudioManager audioManager = new AudioManager();
	private static JLayeredPane currentPane;
	private static GameWindow gameWindow = new GameWindow();
	private static PlayWindow playWindow = new PlayWindow();
	private static ScoreWindow scoreWindow = new ScoreWindow();
	private static GridBagConstraints gbc = new GridBagConstraints();

	public Screen() {
		screen = this;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentPane = gameWindow;
		setLayeredPane(currentPane);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();
				if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
					gameWindow.changeDirection(Direction.north);
				} else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
					gameWindow.changeDirection(Direction.south);
				} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
					gameWindow.changeDirection(Direction.west);
				} else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
					gameWindow.changeDirection(Direction.east);
				} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (isInGlassPane(playWindow))
						removeFromGlassPane(playWindow);
					else if (isInGlassPane(scoreWindow)) {
						if (scoreWindow.isShowing())
							scoreWindow.showMenu();
						else
							removeFromGlassPane(scoreWindow);
					}
				}else {
					if(((keyCode >= 65 && keyCode <= 90) || keyCode == 10) && isInGlassPane(scoreWindow) && scoreWindow.isShowing()) {
						((ScorePage) scoreWindow.getComponentsInLayer(1)[0]).receiveInput(keyCode, Character.toUpperCase(event.getKeyChar()));
					}
				}
			}
		});
		getGlassPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getSource() instanceof JLabel && ((JLabel) event.getSource()).getParent() != getGlassPane())
					event.consume();
			}
		});
		((JPanel) getGlassPane()).setLayout(new GridBagLayout());
		setVisible(true);
		gameWindow.start(0);
	}
	
	public static void refreshGlassPane() {
		((JPanel) screen.getGlassPane()).repaint();
	}

	public static void addToGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents()) {
			glass.remove(comp);
			currentPane.add(comp, new Integer(currentPane.highestLayer() + 1));
		}
		glass.add(pane, gbc);
		glass.setVisible(true);
		currentPane.repaint();
		glass.repaint();
	}

	public static void removeFromGlassPane(JLayeredPane pane) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		glass.remove(pane);
		for (Component comp : currentPane.getComponentsInLayer(currentPane.highestLayer())) {
			if (comp instanceof Window) {
				currentPane.remove(comp);
				glass.add(comp, gbc);
				break;
			}
		}
		currentPane.repaint();
		glass.repaint();
		glass.setVisible(glass.getComponents().length != 0);
	}

	public static boolean isInGlassPane(Component search) {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents())
			if (search == comp)
				return true;
		return false;
	}

	public static void showPlayWindow() {
		addToGlassPane(playWindow);
	}

	public static void showScoreWindow() {
		addToGlassPane(scoreWindow);
	}

	public static void clearGlassPane() {
		JPanel glass = ((JPanel) screen.getGlassPane());
		for (Component comp : glass.getComponents())
			glass.remove(comp);
		currentPane.repaint();
		glass.repaint();
		glass.setVisible(false);
	}

	public static void setClickable(boolean clickable) {
		((JPanel) screen.getGlassPane()).setVisible(!clickable);
	}

	public static void newGame(Block[][] preset, String level, int speed) {
		gameWindow.newGame(preset, level, speed);
	}

	public static boolean isSlotAvailable(String level, int speed, int score) {
		return scoreWindow.isSlotAvailable(level, speed, score);
	}

	public static void saveScore(String level, int speed, int score) {
		scoreWindow.saveScore(level, speed, score);
	}

	public static void main(String[] args) {
		new Screen();
	}
}
