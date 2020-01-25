package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import gamestuff.Tile;
import gamestuff.TileType;

public class PlayWindow extends Window {
	public Block[][] blocks = new Block[13][23];
	private String currentLevel = "No Maze";

	public PlayWindow() {
		super(1000, 560);
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 23; j++) {
				JLabel square = new JLabel();
				square.setBounds(50 + j * (int) (Tile.SIDE * 0.4), 50 + i * (int) (Tile.SIDE * 0.4),
						(int) (Tile.SIDE * 0.4), (int) (Tile.SIDE * 0.4));
				square.setBackground((i + j) % 2 == 0 ? Ground.LIGHT_GROUND : Ground.DARK_GROUND);
				square.setOpaque(true);
				add(square, JLayeredPane.DEFAULT_LAYER);
				blocks[i][j] = new Block(50 + j * (int) (Tile.SIDE * 0.4), 50 + i * (int) (Tile.SIDE * 0.4));
				add(blocks[i][j], Ground.PLAY_LAYER);
				blocks[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent event) {
						currentLevel = "Custom";
					}
				});
			}
		}
		for (int i = 0; i < 3; i++)
			blocks[6][10 + i].setType(TileType.snake);
		Button noMaze = new Button("No Maze", 700, 50, 250, 60);
		Button box = new Button("Box", 700, 130, 250, 60);
		Button tunnel = new Button("Tunnel", 700, 210, 250, 60);
		Button spiral = new Button("Spiral", 700, 290, 250, 60);
		Button blockade = new Button("Blockade", 700, 370, 250, 60);
		Button twisted = new Button("Twisted", 700, 450, 250, 60);
		Button speed = new Button(370, 410, 250, 100, "Speed: 1", "Speed: 2", "Speed: 3", "Speed: 4", "Speed: 5");
		Button play = new Button("Play", 70, 410, 250, 100);
		add(noMaze, JLayeredPane.DEFAULT_LAYER);
		add(box, JLayeredPane.DEFAULT_LAYER);
		add(tunnel, JLayeredPane.DEFAULT_LAYER);
		add(spiral, JLayeredPane.DEFAULT_LAYER);
		add(blockade, JLayeredPane.DEFAULT_LAYER);
		add(twisted, JLayeredPane.DEFAULT_LAYER);
		add(speed, JLayeredPane.DEFAULT_LAYER);
		add(play, JLayeredPane.DEFAULT_LAYER);
		noMaze.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				currentLevel = "No Maze";
			}
		});
		box.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				for (int i = 0; i < 13; i++) {
					blocks[i][0].setType(TileType.wall);
					blocks[i][22].setType(TileType.wall);
				}
				for (int j = 0; j < 23; j++) {
					blocks[0][j].setType(TileType.wall);
					blocks[12][j].setType(TileType.wall);
				}
				currentLevel = "Box";
			}
		});
		tunnel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				for (int i = 0; i < 2; i++) {
					blocks[0][i].setType(TileType.wall);
					blocks[0][22 - i].setType(TileType.wall);
					blocks[12][i].setType(TileType.wall);
					blocks[12][22 - i].setType(TileType.wall);
					blocks[i][0].setType(TileType.wall);
					blocks[12 - i][0].setType(TileType.wall);
					blocks[i][22].setType(TileType.wall);
					blocks[12 - i][22].setType(TileType.wall);
				}
				for (int i = 0; i < 7; i++) {
					blocks[4][8 + i].setType(TileType.wall);
					blocks[8][8 + i].setType(TileType.wall);
				}
				currentLevel = "Tunnel";
			}
		});
		spiral.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				for (int i = 0; i < 11; i++) {
					blocks[8][i].setType(TileType.wall);
					blocks[4][22 - i].setType(TileType.wall);
				}
				for (int i = 0; i < 6; i++) {
					blocks[i][9].setType(TileType.wall);
					blocks[12 - i][13].setType(TileType.wall);
				}
				currentLevel = "Spiral";
			}
		});
		blockade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				for (int i = 0; i < 5; i++) {
					blocks[i][0].setType(TileType.wall);
					blocks[12 - i][0].setType(TileType.wall);
					blocks[i][22].setType(TileType.wall);
					blocks[12 - i][22].setType(TileType.wall);
				}
				for (int j = 0; j < 23; j++) {
					blocks[0][j].setType(TileType.wall);
					blocks[12][j].setType(TileType.wall);
				}
				for (int i = 0; i < 7; i++) {
					blocks[3 + i][7].setType(TileType.wall);
					blocks[3 + i][15].setType(TileType.wall);
				}
				currentLevel = "Blockade";
			}
		});
		twisted.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				clearMaze();
				for (int i = 0; i < 23; i++) {
					blocks[3][i].setType(TileType.wall);
					blocks[7][i].setType(i > 7 && i < 12 ? TileType.empty : TileType.wall);
				}
				for (int i = 0; i < 3; i++) {
					blocks[i][7].setType(TileType.wall);
					blocks[8 + i][12].setType(TileType.wall);
					blocks[12][22 - i].setType(TileType.wall);
				}
				for (int i = 0; i < 12; i++) {
					blocks[12][4 + i].setType(TileType.wall);
				}
				blocks[11][12].setType(TileType.wall);
				blocks[11][22].setType(TileType.wall);
				currentLevel = "Twisted";
			}
		});
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				Screen.newGame(blocks, currentLevel, speed.index());
				Screen.removeFromGlassPane(PlayWindow.this);
				play.setBorder(null);
			}
		});
	}

	public void clearMaze() {
		for (int i = 0; i < 13; i++)
			for (int j = 0; j < 23; j++)
				blocks[i][j].setType(TileType.empty);
		for (int i = 0; i < 3; i++)
			blocks[6][10 + i].setType(TileType.snake);
	}

}
