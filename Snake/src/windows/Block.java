package windows;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import gamestuff.Tile;
import gamestuff.TileType;

public class Block extends JLabel {
	private TileType type = TileType.empty;

	public Block(int x, int y) {
		setBounds(x, y, (int) (Tile.SIDE * 0.4), (int) (Tile.SIDE * 0.4));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				changeType();
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				setBorder(Window.BORDER);
			}

			@Override
			public void mouseExited(MouseEvent event) {
				setBorder(null);
			}
		});
	}

	public TileType getType() {
		return type;
	}

	public void setType(TileType type) {
		this.type = type;
		setOpaque(type != TileType.empty);
		setBackground(type.getColor());
	}

	private void changeType() {
		if (type == TileType.empty) {
			type = TileType.wall;
			setOpaque(true);
			setBackground(Tile.WALL_COLOR);
		} else if (type == TileType.wall) {
			type = TileType.empty;
			setBackground(null);
			setOpaque(false);
		}
	}

}
