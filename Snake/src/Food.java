import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Food extends JLabel{
	private int row;
	private int column;
	public Food() throws IOException {
		setIcon(new ImageIcon(ImageIO.read(getClass().getResource("food.png"))));
		row = (int) (Math.random()*15);
		column = (int) (Math.random()*30);
		setBounds(column*40,row*40,40,40);
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}
