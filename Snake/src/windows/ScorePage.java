package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ScorePage extends JLayeredPane {
	public static ScoreWindow scoreWindow;
	private JLabel[] names = new JLabel[5];
	private JLabel[] scores = new JLabel[5];
	private boolean saving = false;
	private int change = -1;
	private Timer flash;

	public ScorePage(String maze, int speed) {
		Font font = new Font(GameWindow.FONT.getName(), Font.PLAIN, 35);
		JLabel title = new JLabel(maze + " - " + speed, JLabel.CENTER);
		title.setFont(new Font(GameWindow.FONT.getName(), Font.PLAIN, 50));
		title.setForeground(Color.white);
		title.setBounds(4, 4, 492, 100);
		add(title, JLayeredPane.DEFAULT_LAYER);
		for (int i = 0; i < 5; i++) {
			names[i] = new JLabel();
			names[i].setFont(font);
			names[i].setForeground(Color.white);
			names[i].setBounds(50, 90 + 60 * i, 200, 60);
			add(names[i], JLayeredPane.DEFAULT_LAYER);
			scores[i] = new JLabel("", JLabel.RIGHT);
			scores[i].setFont(font);
			scores[i].setForeground(Color.white);
			scores[i].setBounds(250, 90 + 60 * i, 200, 60);
			add(scores[i], JLayeredPane.DEFAULT_LAYER);
		}
		Button back = new Button("Back", 100, 400, 300, 80);
		add(back, JLayeredPane.DEFAULT_LAYER);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!saving)
					scoreWindow.showMenu();
			}
		});
		setBorder(Window.BORDER);
		setOpaque(true);
		setBounds(0, 0, 500, 500);
	}

	public void adjustScores(int score) {
		change = -1;
		for (int i = 0; i < 5; i++) {
			if (scores[i].getText().length() == 0 || score > Integer.valueOf(scores[i].getText())) {
				change = i;
				break;
			}
		}
		for (int i = 4; i > change; i--) {
			names[i].setText(names[i - 1].getText());
			scores[i].setText(scores[i - 1].getText());
		}
	}

	public void addScore(String name, int score) {
		adjustScores(score);
		names[change].setText(name);
		scores[change].setText(String.valueOf(score));
	}

	public boolean isSlotAvailable(int score) {
		for (int i = 0; i < 5; i++)
			if (scores[i].getText().length() == 0 || score > Integer.valueOf(scores[i].getText()))
				return true;
		return false;
	}

	public void saveScore(int score) {
		adjustScores(score);
		scores[change].setText(String.valueOf(score));
		names[change].setText("YOUR NAME");
		saving = true;
		Screen.showScoreWindow();
		scoreWindow.showPage(this);
		startFlashing();
	}

	public void receiveInput(int keyCode, char key) {
		if (saving) {
			String current = names[change].getText();
			if (keyCode == KeyEvent.VK_BACK_SPACE && current.length() != 0 && !current.equals("YOUR NAME")) {
				names[change].setText(current.substring(0, current.length() - 1));
				if (current.length() == 1) {
					names[change].setText("YOUR NAME");
				}
			} else if (keyCode == KeyEvent.VK_ENTER && current.length() > 0 && !current.equals("YOUR NAME")) {
				((ScoreWindow) getParent()).saveScores();
				stopFlashing();
				saving = false;
				change = -1;
			} else {
				if ((int) key >= 65 && (int) key <= 90 && (current.equals("YOUR NAME") || current.length() < 6)) {
					names[change].setText(current.equals("YOUR NAME") ? String.valueOf(key) : current + key);
				}
			}
		}
	}

	public void startFlashing() {
		flash = new Timer();
		flash.schedule(new TimerTask() {
			@Override
			public void run() {
				names[change].setVisible(!names[change].isVisible());
				scores[change].setVisible(!scores[change].isVisible());
			}
		}, 400, 400);
	}

	public void stopFlashing() {
		flash.cancel();
		names[change].setVisible(true);
		scores[change].setVisible(true);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < 5; i++) {
			str += names[i].getText() + ":" + scores[i].getText() + ":";
		}
		return str;
	}
}
