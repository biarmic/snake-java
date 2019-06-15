import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
	
public class Grid extends JLayeredPane {
	private Snake snake;
	public Grid() throws IOException {
		JLabel boardBG = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("board.png"))));
		boardBG.setBounds(0,0,1200,600);
		add(boardBG,JLayeredPane.DEFAULT_LAYER);
		snake = new Snake(this);
		setFocusable(true);
	}
	public Snake getSnake() {
		return snake;
	}
	public void setDirection(Direction direction) {
		snake.setDirection(direction);
	}
	public void start() throws InterruptedException, IOException {
		while(snake.isSliding()) {
			Thread.sleep(300);
			snake.move();
		}
		JOptionPane.showMessageDialog(null,"Game is over!");
	}
}
