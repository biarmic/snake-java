package windows;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import gamestuff.Direction;
import gamestuff.Tile;
import gamestuff.TileType;
import managers.AudioManager;

public class Ground extends JLayeredPane {
	public static final Color LIGHT_GROUND = new Color(57, 57, 57);
	public static final Color DARK_GROUND = new Color(51, 51, 51);
	public static final Integer PLAY_LAYER = new Integer(1);
	private Tile[][] grid = new Tile[13][23];
	private ArrayList<Tile> snake = new ArrayList<Tile>();
	private Direction currentDirection = Direction.east;
	private Direction nextDirection = Direction.east;

	public Ground(Block[][] preset) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 23; j++) {
				JLabel square = new JLabel();
				square.setBounds(4 + j * Tile.SIDE, 4 + i * Tile.SIDE, Tile.SIDE, Tile.SIDE);
				square.setBackground((i + j) % 2 == 0 ? LIGHT_GROUND : DARK_GROUND);
				square.setOpaque(true);
				add(square, JLayeredPane.DEFAULT_LAYER);
				grid[i][j] = new Tile(4 + j * Tile.SIDE, 4 + i * Tile.SIDE);
				if (preset != null)
					grid[i][j].setType(preset[i][j].getType(), -1);
				add(grid[i][j], PLAY_LAYER);
			}
		}
		for (int i = 0; i < 3; i++) {
			grid[6][10 + i].setType(TileType.snake, -1);
			snake.add(grid[6][10 + i]);
		}
		spawnFruit();
		setBounds(86, 86, 23 * Tile.SIDE + 8, 13 * Tile.SIDE + 8);
		setBorder(Window.BORDER);
	}

	public void spawnFruit() {
		while (true) {
			int row = (int) (Math.random() * 13);
			int col = (int) (Math.random() * 23);
			if (grid[row][col].getType() == TileType.empty) {
				grid[row][col].setType(TileType.fruit, -1);
				break;
			}
		}
	}

	public void moveSnake() {
		Tile head = snake.get(snake.size() - 1);
		int row = head.getRow();
		int col = head.getColumn();
		Tile next = grid[Math.floorMod(row + nextDirection.getRowValue(), 13)][Math
				.floorMod(col + nextDirection.getColumnValue(), 23)];
		if ((next.getType() == TileType.snake && next != snake.get(0)) || next.getType() == TileType.wall) {
			((GameWindow) getParent()).gameOver(false);
			return;
		} else if (next.getType() != TileType.fruit) {
			snake.remove(0).setType(TileType.empty, -1);
		} else {
			spawnFruit();
			((GameWindow) getParent()).addScore();
		}
		snake.add(next);
		next.setType(TileType.snake, -1);
		currentDirection = nextDirection;
	}

	public void changeDirection(Direction direction) {
		if (currentDirection.opposite() != direction)
			nextDirection = direction;
	}

	public void flash() {
		for (Tile tile : snake)
			tile.setType(tile.getType() == TileType.snake ? TileType.empty : TileType.snake, -1);
	}

}
