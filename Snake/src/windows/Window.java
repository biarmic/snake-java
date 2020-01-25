package windows;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import java.awt.Color;

public abstract class Window extends JLayeredPane {
	public static final Color BACKGROUND_COLOR = new Color(4, 64, 28);
	public static final LineBorder BORDER = new LineBorder(Color.white, 4);

	public Window(int width, int height) {
		this();
		setBounds((1920 - width) / 2, (1080 - height) / 2, width, height);
		setPreferredSize(getSize());
	}

	private Window() {
		setBackground(BACKGROUND_COLOR);
		setBorder(BORDER);
		setOpaque(true);
	}
}
