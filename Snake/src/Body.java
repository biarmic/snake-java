import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Body extends JLabel{
	private int row;
	private int column;
	public Body(int row, int column) throws IOException {
		setIcon(new ImageIcon(ImageIO.read(getClass().getResource("snake_body.png"))));
		setBounds(column*40,row*40,40,40);
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public void slide(int row, int column) {
		this.row = row;
		this.column = column;
		setLocation(column*40,row*40);
	}
}
