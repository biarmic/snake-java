package gamestuff;

import java.awt.Color;

import javax.swing.JLabel;

public class Tile extends JLabel {
	public static final Color SNAKE_COLOR = new Color(51, 181, 29);
	public static final Color FRUIT_COLOR = new Color(179, 16, 11);
	public static final Color WALL_COLOR = new Color(163, 163, 163);
	public static final int SIDE = 65;
	private TileType type = TileType.empty;
	private int x;
	private int y;
	private int portalID = -1;

	public Tile(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		setBounds(x, y, SIDE, SIDE);
	}

	public int getRow() {
		return y / SIDE;
	}

	public int getColumn() {
		return x / SIDE;
	}

	public TileType getType() {
		return type;
	}
	
	public int getPortalID() {
		return portalID;
	}

	public void setType(TileType type, int id) {
		this.type = type;
		if (type == TileType.snake) {
			setBounds(x, y, SIDE, SIDE);
		} else if (type == TileType.fruit) {
			setBounds(x + SIDE / 4, y + SIDE / 4, SIDE / 2, SIDE / 2);
		} else if (type == TileType.wall) {
			setBounds(x, y, SIDE, SIDE);
		} else {
			setBounds(x, y, SIDE, SIDE);
		}
		setOpaque(type != TileType.empty);
		setBackground(type.getColor());
	}

}
