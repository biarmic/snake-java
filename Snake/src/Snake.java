import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

public class Snake {
	private boolean sliding = true;
	private JLayeredPane panel;
	private ArrayList<Body> bodies = new ArrayList<Body>();
	private Direction direction;
	private Food food;
	public Snake(JLayeredPane panel) throws IOException {
		this.panel = panel;
		direction = Direction.RIGHT;
		for(int i = 0; i < 3; i++) {
			Body a = new Body(7,15-i);
			bodies.add(a);
			this.panel.add(a,JLayeredPane.MODAL_LAYER);
		}
		spawnFood();
	}
	public boolean isSliding() {
		return sliding;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		if(direction.notOpposite(this.direction)) {
			this.direction = direction;
		}
	}
	public void move() throws IOException {
		Body a = bodies.get(0);
		int prevRow = a.getRow();
		int prevColumn = a.getColumn();
		if(direction==Direction.LEFT) {
			doesCollide(prevRow,prevColumn-1);
			a.slide(prevRow,prevColumn-1);
		}else if(direction==Direction.RIGHT) {
			doesCollide(prevRow,prevColumn+1);
			a.slide(prevRow,prevColumn+1);
		}else if(direction==Direction.UP) {
			doesCollide(prevRow-1,prevColumn);
			a.slide(prevRow-1,prevColumn);
		}else if(direction==Direction.DOWN) {
			doesCollide(prevRow+1,prevColumn);
			a.slide(prevRow+1,prevColumn);
		}
		boolean grow = false;
		if(a.getRow()==food.getRow() && a.getColumn()==food.getColumn())
			grow = true;
		for(int i = 1; i < bodies.size(); i++) {
			a = bodies.get(i);
			int row = a.getRow();
			int column = a.getColumn();
			a.slide(prevRow,prevColumn);
			prevRow = row;
			prevColumn = column;
		}
		if(grow) {
			eatFood();
			Body b = new Body(prevRow,prevColumn);
			bodies.add(b);
			panel.add(b,JLayeredPane.MODAL_LAYER);
			panel.repaint();
			spawnFood();
		}
	}
	public void spawnFood() throws IOException {
		food = new Food();
		if(isThereBody(food.getRow(),food.getColumn()))
			spawnFood();
		else
			panel.add(food,JLayeredPane.PALETTE_LAYER);
	}
	public void eatFood() throws IOException {
		panel.remove(food);
	}
	public boolean isThereBody(int row, int column) {
		for(Body a : bodies) {
			if(a.getRow()==row && a.getColumn()==column)
				return true;
		}
		return false;
	}
	public void doesCollide(int row, int column) {
		if(row<0 || row>14 || column<0 || column>29)
			sliding = false;
		for(int i = 3; i < bodies.size(); i++) {
			Body a = bodies.get(i);
			if(a.getRow()==row && a.getColumn()==column)
				sliding = false;
		}
	}
}
