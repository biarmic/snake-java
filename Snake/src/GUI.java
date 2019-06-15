import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class GUI extends JFrame {
	public GUI() throws IOException, InterruptedException {
		super("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(1206,629);
		setResizable(false);
		setLocation((int)(screenSize.width-1216)/2,(int)(screenSize.height-639)/2);
		Grid grid = new Grid();
		add(grid);
		setVisible(true);
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "pressUp");//UP ARROW
		getRootPane().getActionMap().put("pressUp", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				grid.setDirection(Direction.UP);
			}
		});
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "pressDown");//DOWN ARROW
		getRootPane().getActionMap().put("pressDown", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				grid.setDirection(Direction.DOWN);
			}
		});
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "pressLeft");//LEFT ARROW
		getRootPane().getActionMap().put("pressLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				grid.setDirection(Direction.LEFT);
			}
		});
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "pressRight");//RIGHT ARROW
		getRootPane().getActionMap().put("pressRight", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				grid.setDirection(Direction.RIGHT);
			}
		});
		grid.start();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		while(true) {
			GUI gui = new GUI();
			gui.setVisible(false);
			gui.dispose();
		}
	}
}
