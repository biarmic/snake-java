package gamestuff;

import java.awt.Color;

public enum TileType {
	empty, snake, fruit, wall;
	public Color getColor() {
		if(this==snake)
			return Tile.SNAKE_COLOR;
		if(this==fruit)
			return Tile.FRUIT_COLOR;
		if(this==wall)
			return Tile.WALL_COLOR;
		return null;
	}
}
