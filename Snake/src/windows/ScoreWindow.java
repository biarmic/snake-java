package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ScoreWindow extends Window {
	private static final Integer PAGE_LAYER = 1;
	private ScorePage[][] pages = new ScorePage[6][5];
	private JLabel text = new JLabel("Highscores", JLabel.CENTER);
	private Button maze = new Button(50, 125, 400, 75, "No Maze", "Box", "Tunnel", "Spiral", "Blockade", "Twisted");
	private Button speed = new Button(50, 237, 400, 75, "Speed: 1", "Speed: 2", "Speed: 3", "Speed: 4", "Speed: 5");
	private Button show = new Button("Show", 50, 350, 400, 100);
	private boolean isShowing = false;

	public ScoreWindow() {
		super(500, 500);
		ScorePage.scoreWindow = this;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				pages[i][j] = new ScorePage(
						i == 0 ? "No Maze"
								: i == 1 ? "Box"
										: i == 2 ? "Tunnel" : i == 3 ? "Spiral" : i == 4 ? "Blockade" : "Twisted",
						j + 1);
			}
		}
		text.setFont(new Font(GameWindow.FONT.getName(), Font.PLAIN, 50));
		text.setForeground(Color.white);
		text.setBounds(4, 4, 492, 100);
		add(text, JLayeredPane.DEFAULT_LAYER);
		add(maze, JLayeredPane.DEFAULT_LAYER);
		add(speed, JLayeredPane.DEFAULT_LAYER);
		add(show, JLayeredPane.DEFAULT_LAYER);
		show.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!show.isDisabled()) {
					isShowing = true;
					maze.setDisabled(true);
					speed.setDisabled(true);
					show.setDisabled(true);
					add(pages[maze.index()][speed.index()], PAGE_LAYER);
				}
			}
		});
		readScores();
	}

	public void readScores() {
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get("src/others/scores.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : lines) {
			StringTokenizer tokenizer = new StringTokenizer(line, ":");
			String page = tokenizer.nextToken();
			int speed = Integer.valueOf(tokenizer.nextToken());
			while (tokenizer.hasMoreTokens()) {
				String name = tokenizer.nextToken();
				int score = Integer.valueOf(tokenizer.nextToken());
				pages[page.equals("No Maze") ? 0
						: page.equals("Box") ? 1
								: page.equals("Tunnel") ? 2
										: page.equals("Spiral") ? 3 : page.equals("Blockade") ? 4 : 5][speed - 1]
												.addScore(name, score);
			}
		}
	}

	public void saveScores() {
		try {
			String str = "";
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 5; j++) {
					str += (i == 0 ? "No Maze" : i == 1 ? "Box" : i == 2 ? "Tunnel" : i == 3 ? "Spiral" : i == 4 ? "Blockade" : "Twisted") + ":" + (j+1) + ":" + pages[i][j] + "\n";
				}
			}
			FileWriter fw = new FileWriter(new File("src/others/scores.txt"));
			fw.write(str);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}

	public boolean isSlotAvailable(String level, int speed, int score) {
		return pages[level.equals("No Maze") ? 0
				: level.equals("Box") ? 1
						: level.equals("Tunnel") ? 2
								: level.equals("Spiral") ? 3 : level.equals("Blockade") ? 4 : 5][speed - 1]
										.isSlotAvailable(score);
	}

	public void saveScore(String level, int speed, int score) {
		pages[level.equals("No Maze") ? 0
				: level.equals("Box") ? 1
						: level.equals("Tunnel") ? 2
								: level.equals("Spiral") ? 3 : level.equals("Blockade") ? 4 : 5][speed - 1]
										.saveScore(score);
	}

	public void showMenu() {
		isShowing = false;
		maze.setDisabled(false);
		speed.setDisabled(false);
		show.setDisabled(false);
		if (getComponentsInLayer(1).length != 0)
			remove(getComponentsInLayer(1)[0]);
		Screen.refreshGlassPane();
	}

	public void showPage(ScorePage page) {
		isShowing = true;
		maze.setDisabled(true);
		speed.setDisabled(true);
		show.setDisabled(true);
		add(page, PAGE_LAYER);
	}

}
