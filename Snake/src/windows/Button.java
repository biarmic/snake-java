package windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import managers.AudioManager;

public class Button extends JLabel {
	public static Color BACKGROUND_COLOR = new Color(9, 122, 54);
	private int index;
	private boolean disabled = false;

	public Button(String text, int x, int y, int width, int height) {
		super(text, JLabel.CENTER);
		setBounds(x, y, width, height);
		setOpaque(true);
		setBackground(BACKGROUND_COLOR);
		setForeground(Color.white);
		setFont(new Font(GameWindow.FONT.getName(), Font.PLAIN, height / 2));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent event) {
				Button.this.setBorder(Window.BORDER);
				Screen.refreshGlassPane();
			}

			@Override
			public void mouseExited(MouseEvent event) {
				Button.this.setBorder(null);
				Screen.refreshGlassPane();
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				if (!disabled) {
					AudioManager.playSound("click");
					Screen.refreshGlassPane();
				}
			}
		});
	}

	public Button(int x, int y, int width, int height, String... array) {
		this(x, y, width, height, 0, array);
	}

	public Button(int x, int y, int width, int height, int index, String... array) {
		this(array[index], x, y, width, height);
		this.index = index;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (!disabled) {
					Button.this.index = (Button.this.index + 1) % array.length;
					Button.this.setText(array[Button.this.index]);
				}
			}
		});
	}

	public int index() {
		return index;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}
}
